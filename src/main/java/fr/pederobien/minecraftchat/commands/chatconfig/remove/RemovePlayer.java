package fr.pederobien.minecraftchat.commands.chatconfig.remove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.commands.chatconfig.AbstractChatEdition;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.exceptions.PlayerNotFoundException;
import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;

public class RemovePlayer<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected RemovePlayer() {
		super(EchatConfigRemoveLabel.PLAYER, EChatConfigRemoveMessageCode.REMOVE_PLAYER__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<Player> players = new ArrayList<Player>();

		if (args[0].equals(IGameConfigurationHelper.ALL)) {
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_PLAYER__ALL_PLAYERS_REMOVED_FROM_CHATS);
			return true;
		}

		String playerNamesConcatenated = null;
		List<IChat> chats = emptyList();
		try {
			players = getPlayers(args);
			playerNamesConcatenated = concat(getPlayerNames(players));
			for (Player player : players) {
				boolean chatFound = false;
				for (IChat chat : get().getChats()) {
					if (chat.getPlayers().contains(player)) {
						chatFound = true;
						chats.add(chat);
						break;
					}
				}
				if (!chatFound) {
					sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_PLAYER__PLAYER_NOT_REGISTERED_IN_CHATS, player.getName(), get().getName());
					return false;
				}
				chats.get(chats.size() - 1).remove(player);
			}
		} catch (PlayerNotFoundException e) {
			sendMessageToSender(sender, ECommonMessageCode.COMMON_PLAYER_DOES_NOT_EXIST, e.getPlayerName(), get().getName());
			return false;
		}

		switch (players.size()) {
		case 0:
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_PLAYER__ANY_PLAYER_REMOVED_FROM_CHAT);
			break;
		case 1:
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_PLAYER__ONE_PLAYER_REMOVED_FROM_CHAT, playerNamesConcatenated,
					concat(getChatNames(chats, true)));
			break;
		default:
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED_FROM_CHATS, playerNamesConcatenated,
					concat(getChatNames(chats, true)));
			break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Stream<String> players = getNotFreePlayers(Arrays.asList(args)).map(player -> player.getName());

		// Adding all to delete all registered players
		if (args.length == 1)
			return filter(Stream.concat(players, Stream.of(IGameConfigurationHelper.ALL)), args[0]);

		// If the first argument is all -> any player is proposed
		// Else propose not already mentioned players
		return filter(args[0].equals(IGameConfigurationHelper.ALL) ? emptyStream() : players, args[args.length - 1]);
	}

}
