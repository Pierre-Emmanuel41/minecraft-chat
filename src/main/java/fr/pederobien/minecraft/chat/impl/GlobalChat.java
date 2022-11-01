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
import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.minecraft.game.impl.PlayerQuitOrJoinEventHandler;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.LogEvent;

public class GlobalChat implements IChat, ICodeSender {
	private EColor color;
	private Lock lock;
	private IPlayerList players;
	private Map<String, Player> quitPlayers;

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param name The chat name.
	 */
	public GlobalChat() {
		this.color = EColor.RESET;

		lock = new ReentrantLock(true);
		players = new PlayerList(getName());
		quitPlayers = new HashMap<String, Player>();

		PlayerGroup.ALL.toStream().forEach(player -> getPlayers().add(player));
		PlayerQuitOrJoinEventHandler.instance().registerQuitEventHandler(this, event -> onPlayerQuitEvent(event));
		PlayerQuitOrJoinEventHandler.instance().registerJoinEventHandler(this, event -> onPlayerJoinEvent(event));
	}

	@Override
	public String getName() {
		return "Global";
	}

	@Override
	public void setName(String name) {
		throw new IllegalStateException("The global chat cannot be renamed");
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
		throw new IllegalStateException("The color of the global chat cannot be changed");
	}

	@Override
	public IPlayerList getPlayers() {
		return players;
	}

	@Override
	public void sendMessage(CommandSender sender, String message) {
		EventManager.callEvent(new LogEvent("Sending message \"%s\" to global chat (%s players)", message, getPlayers().toList().size()));
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
		if (sender instanceof Player) {
			Player playerSender = (Player) sender;
			Platform platform = Platform.get(playerSender);
			if (platform != null)
				senderName = ((ITeamConfigurable) platform.getGame()).getTeams().getTeam(playerSender).get().getColor().getInColor(senderName);
		}
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
			getPlayers().add(event.getPlayer());
			quitPlayers.remove(event.getPlayer().getName());
		} finally {
			lock.unlock();
		}
	}
}
