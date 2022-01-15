package fr.pederobien.minecraft.chat.commands.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.managers.PlayerManager;

public class ChatAddPlayerNode extends ChatNode {
	private List<Player> exceptedPlayers;

	/**
	 * Creates a node that adds players to a chat.
	 * 
	 * @param chat The chat associated to this node.
	 */
	protected ChatAddPlayerNode(Supplier<IChat> chat) {
		super(chat, "add", EChatCode.CHAT__ADD_PLAYER__EXPLANATION, c -> c != null);
		exceptedPlayers = new ArrayList<Player>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Predicate<Player> filter;
		switch (args.length) {
		case 0:
			return emptyList();
		default:
			List<String> alreadyMentionned = asList(args);
			filter = player -> !exceptedPlayers.contains(player) && !alreadyMentionned.contains(player.getName())
					&& !getChat().getPlayers().getPlayer(player.getName()).isPresent();

			return filter(PlayerGroup.ALL.toStream().filter(filter).map(player -> player.getName()), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String chatColoredName = getChat().getColoredName();

		List<Player> players = new ArrayList<Player>();
		for (String name : args) {
			Player player = PlayerManager.getPlayer(name);

			// Checking if the player name refers to an existing player.
			if (player == null) {
				send(eventBuilder(sender, EChatCode.CHAT__ADD_PLAYER__PLAYER_NOT_FOUND, name, chatColoredName));
				return false;
			}

			// Checking if the player is registered in the current chat.
			// Checking if player is in the "black player list".
			if (getChat().getPlayers().toList().contains(player) || exceptedPlayers.contains(player)) {
				send(eventBuilder(sender, EChatCode.CHAT__ADD_PLAYER__PLAYER_ALREADY_REGISTERED, name, chatColoredName));
				return false;
			}

			players.add(player);
		}

		String playerNames = concat(args);

		for (Player player : players)
			getChat().getPlayers().add(player);

		switch (args.length) {
		case 0:
			sendSuccessful(sender, EChatCode.CHAT__ADD_PLAYER__NO_PLAYER_ADDED, chatColoredName);
			break;
		case 1:
			sendSuccessful(sender, EChatCode.CHAT__ADD_PLAYER__ONE_PLAYER_ADDED, playerNames, chatColoredName);
			break;
		default:
			sendSuccessful(sender, EChatCode.CHAT__ADD_PLAYER__SEVERAL_PLAYERS_ADDED, playerNames, chatColoredName);
			break;
		}
		return true;
	}

	/**
	 * @return The list of players that should not be added in a chat.
	 */
	public List<Player> getExceptedPlayers() {
		return exceptedPlayers;
	}
}
