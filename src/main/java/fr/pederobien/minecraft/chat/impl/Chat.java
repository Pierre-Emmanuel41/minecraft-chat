package fr.pederobien.minecraft.chat.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.minecraft.chat.commands.EChatCode;
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
	private Map<String, Player> quitPlayers;

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
		quitPlayers = new HashMap<String, Player>();

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
	public void sendMessage(CommandSender sender, String message) {
		checkPlayer(sender);
		for (Player player : getPlayers().toList())
			MessageManager.sendMessage(player, String.format("%s %s", getPrefix(sender, player), message));
	}

	@Override
	public void sendMessage(CommandSender sender, IMinecraftCode code, Object... args) {
		checkPlayer(sender);
		for (Player player : getPlayers())
			MessageManager.sendMessage(player, String.format("%s %s", getPrefix(sender, player), getMessage(player, code, args)));
	}

	@Override
	public String toString() {
		StringJoiner players = new StringJoiner(" ", "[", "]");
		for (Player player : getPlayers())
			players.add(player.getName());
		return getColor().getInColor(getName() + " " + players.toString());
	}

	private void checkPlayer(CommandSender sender) {
		if (!(sender instanceof Player))
			return;

		Player player = (Player) sender;
		if (player.isOp())
			return;

		if (!getPlayers().getPlayer(sender.getName()).isPresent())
			throw new PlayerNotRegisteredInChatException(this, player);
	}

	/**
	 * Get the prefix to use in order to send a message to the given player.
	 * 
	 * @param sender The object that sends a message to this chat.
	 * @param player The player to which a message is sent.
	 * 
	 * @return The prefix to use.
	 */
	private String getPrefix(CommandSender sender, CommandSender player) {
		String senderName = player.equals(sender) ? getMessage(sender, EChatCode.CHAT__ME) : sender.getName();
		return color.getInColor(String.format("[%s -> %s] ", senderName, getName()));
	}

	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		lock.lock();
		try {
			if (!getPlayers().getPlayer(event.getPlayer().getName()).isPresent())
				return;

			getPlayers().remove(event.getPlayer());
			quitPlayers.put(event.getPlayer().getName(), event.getPlayer());
		} finally {
			lock.unlock();
		}
	}

	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		lock.lock();
		try {
			if (quitPlayers.get(event.getPlayer().getName()) == null)
				return;

			quitPlayers.remove(event.getPlayer().getName());
			getPlayers().add(event.getPlayer());
		} finally {
			lock.unlock();
		}
	}
}
