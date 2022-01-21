package fr.pederobien.minecraft.chat.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.minecraft.chat.event.ChatNameChangePostEvent;
import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.minecraft.game.impl.PlayerQuitOrJoinEventHandler;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.utils.event.EventManager;

public class Chat implements IChat, ICodeSender {
	private String name;
	private EColor color;
	private Lock lock;
	private IPlayerList players;
	private List<Player> quitPlayers;

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param name The chat name.
	 */
	public Chat(String name) {
		this.name = name;
		this.color = EColor.RESET;

		lock = new ReentrantLock(true);
		players = new PlayerList(name);
		quitPlayers = new ArrayList<Player>();

		PlayerQuitOrJoinEventHandler.instance().registerQuitEventHandler(this, event -> onPlayerQuitEvent(event));
		PlayerQuitOrJoinEventHandler.instance().registerJoinEventHandler(this, event -> onPlayerJoinEvent(event));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (getName().equals(name))
			return;

		String oldName = getName();
		this.name = name;
		EventManager.callEvent(new ChatNameChangePostEvent(this, oldName));
	}

	@Override
	public String getColoredName() {
		return getColor().getInColor(getName());
	}

	@Override
	public String getColoredName(EColor next) {
		return getColor().getInColor(getName(), next);
	}

	@Override
	public EColor getColor() {
		return color;
	}

	@Override
	public void setColor(EColor color) {
		this.color = color;
	}

	@Override
	public IPlayerList getPlayers() {
		return players;
	}

	@Override
	public void sendMessage(CommandSender sender, boolean isOperator, String message) {
		checkPlayer(sender, isOperator);
		for (Player player : getPlayers().toList())
			MessageManager.sendMessage(player, getPrefix(sender, player, isOperator) + message);
	}

	@Override
	public void sendMessage(CommandSender sender, boolean isOperator, IMinecraftCode code, Object... args) {
		checkPlayer(sender, isOperator);
		for (Player player : getPlayers())
			MessageManager.sendMessage(player, getPrefix(sender, player, isOperator) + getMessage(player, code, args));
	}

	@Override
	public String toString() {
		StringJoiner players = new StringJoiner(" ", "[", "]");
		for (Player player : getPlayers())
			players.add(player.getName());
		return getColor().getInColor(getName() + " " + players.toString());
	}

	private void checkPlayer(CommandSender sender, boolean isOperator) {
		if (sender instanceof Player) {
			if (isOperator && !((Player) sender).isOp())
				throw new PlayerNotRegisteredInChatException(this, (Player) sender);

			if (!isOperator && !getPlayers().toList().contains(sender))
				throw new PlayerNotRegisteredInChatException(this, (Player) sender);
		}
	}

	private String getPrefix(CommandSender sender, CommandSender player, boolean isOperator) {
		String senderName = null;
		if (isOperator)
			senderName = getMessage(sender, EChatCode.CHAT__OPERATOR);
		else
			senderName = player.equals(sender) ? getMessage(sender, EChatCode.CHAT__ME) : sender.getName();
		return color.getInColor(String.format("[%s -> %s] ", senderName, getName()));
	}

	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		lock.lock();
		try {
			Iterator<Player> iterator = getPlayers().iterator();
			while (iterator.hasNext()) {
				Player player = iterator.next();
				if (player.getName().equals(event.getPlayer().getName())) {
					quitPlayers.add(player);
					iterator.remove();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		Iterator<Player> iterator = quitPlayers.iterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.getName().equals(event.getPlayer().getName())) {
				iterator.remove();
				getPlayers().add(event.getPlayer());
			}
		}
	}
}
