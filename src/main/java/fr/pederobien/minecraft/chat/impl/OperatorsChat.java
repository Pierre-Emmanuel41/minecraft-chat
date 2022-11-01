package fr.pederobien.minecraft.chat.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.exception.PlayerNotOperatorException;
import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.minecraft.platform.Platform;

public class OperatorsChat extends Chat {
	private IPlayerList players;

	/**
	 * Creates a chat for operators only. When an operator runs the command "./op &lt;playerName&gt;" with a valid player name, then
	 * the player associated to the given player name is automatically added to this chat. In the same way, when an operator runs the
	 * command "./deop &lt;playerName&gt;" with a valid player name, then the player associated to the given name is automatically
	 * removed from this chat.
	 */
	public OperatorsChat() {
		super("Operators");

		players = new OperatorPlayers(this);
		super.setColor(EColor.GRAY);
	}

	@Override
	public IPlayerList getPlayers() {
		return players;
	}

	@Override
	public void setName(String name) {
		throw new IllegalStateException("The operators chat cannot be renamed");
	}

	@Override
	public void setColor(EColor color) {
		throw new IllegalStateException("The color of the operators chat cannot be changed");
	}

	@Override
	public void sendMessage(CommandSender sender, String message) {
		checkPlayer(sender);
		for (Player player : getPlayers().toList())
			MessageManager.sendMessage(player, String.format("%s %s", getPrefix(sender, player), getColor().getInColor(message)));
	}

	@Override
	public void sendMessage(CommandSender sender, IMinecraftCode code, Object... args) {
		checkPlayer(sender);
		for (Player player : getPlayers())
			MessageManager.sendMessage(player, String.format("%s %s", getPrefix(sender, player), getColor().getInColor(getMessage(player, code, args))));
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
		return getColor().getInColor(String.format("[%s -> %s] ", senderName, getColoredName()));
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		String[] command = event.getMessage().substring(1).split(" ");
		if (command.length < 2)
			return;

		Player player = PlayerManager.getPlayer(command[1]);
		if (player == null)
			return;

		if (command[0].equals("op"))
			getPlayers().add(player);

		if (command[0].equals("deop"))
			getPlayers().remove(player);
	}

	private class OperatorPlayers extends PlayerList implements IPlayerList {
		private IChat chat;

		/**
		 * Creates a list of players that only accepts operator players.
		 * 
		 * @param chat The chat associated to this players list.
		 */
		private OperatorPlayers(IChat chat) {
			super(chat.getName());
			this.chat = chat;
			PlayerGroup.OPERATORS.toStream().forEach(player -> add(player));
		}

		@Override
		public void add(Player player) {
			if (!player.isOp())
				throw new PlayerNotOperatorException(chat, player);
			super.add(player);
		}
	}
}
