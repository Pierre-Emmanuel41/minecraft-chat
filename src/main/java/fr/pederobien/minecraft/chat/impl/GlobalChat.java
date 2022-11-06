package fr.pederobien.minecraft.chat.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.impl.PlayerQuitOrJoinEventHandler;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.minecraft.platform.Platform;

public class GlobalChat extends SimpleChat {
	/**
	 * The name of this chat.
	 */
	public static final String NAME = "Global";

	private IChatPlayerList players;

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param name The chat name.
	 */
	public GlobalChat() {
		super(NAME);

		players = new GlobalList(this);

		PlayerQuitOrJoinEventHandler.instance().registerQuitEventHandler(this, event -> onPlayerQuitEvent(event));
		PlayerQuitOrJoinEventHandler.instance().registerJoinEventHandler(this, event -> onPlayerJoinEvent(event));
	}

	@Override
	public IChatPlayerList getPlayers() {
		return players;
	}

	@Override
	public void setName(String name) {
		throw new IllegalStateException("The global chat cannot be renamed");
	}

	@Override
	public void setColor(EColor color) {
		throw new IllegalStateException("The color of the global chat cannot be changed");
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
		return getColor().getInColor(String.format("[%s -> %s] ", senderName, getName()));
	}

	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		getLock().lock();
		try {
			if (!getPlayers().getPlayer(event.getPlayer().getName()).isPresent())
				return;

			getPlayers().remove(event.getPlayer());
			getQuitPlayers().put(event.getPlayer().getName(), event.getPlayer());
		} finally {
			getLock().unlock();
		}
	}

	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		getLock().lock();
		try {
			getPlayers().add(event.getPlayer());
			getQuitPlayers().remove(event.getPlayer().getName());
		} finally {
			getLock().unlock();
		}
	}

	private class GlobalList extends ChatPlayerList implements IChatPlayerList {

		/**
		 * Creates a list of players that only accepts operator players.
		 * 
		 * @param chat The chat associated to this players list.
		 */
		private GlobalList(IChat chat) {
			super(chat, chat.getName());
			PlayerGroup.ALL.toStream().forEach(player -> super.add(player));
		}

		@Override
		public void add(Player player) {
			return;
		}

		@Override
		public Player remove(String name) {
			return null;
		}

		@Override
		public void clear() {

		}
	}
}
