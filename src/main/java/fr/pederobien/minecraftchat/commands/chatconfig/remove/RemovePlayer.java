package fr.pederobien.minecraftchat.commands.chatconfig.remove;

import java.util.ArrayList;
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
		super(EchatConfigRemoveLabel.PLAYER, EChatConfigRemoveMessageCode.CHAT_REMOVE_PLAYER__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<Player> players = new ArrayList<Player>();

		if (args[0].equals(IGameConfigurationHelper.ALL)) {
			for (IChat chat : get().getChats())
				chat.getPlayers().clear();
			sendSynchro(sender, EChatConfigRemoveMessageCode.CHAT_REMOVE_PLAYER__ALL_PLAYERS_REMOVED);
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
					sendSynchro(sender, EChatConfigRemoveMessageCode.CHAT_REMOVE_PLAYER__PLAYER_NOT_REGISTERED, player.getName(), get().getName());
					return false;
				}
				chats.get(chats.size() - 1).remove(player);
			}
		} catch (PlayerNotFoundException e) {
			sendSynchro(sender, ECommonMessageCode.COMMON_PLAYER_DOES_NOT_EXIST, e.getPlayerName(), get().getName());
			return false;
		}

		switch (players.size()) {
		case 0:
			sendSynchro(sender, EChatConfigRemoveMessageCode.CHAT_REMOVE_PLAYER__ANY_PLAYER_REMOVED);
			break;
		case 1:
			sendSynchro(sender, EChatConfigRemoveMessageCode.CHAT_REMOVE_PLAYER__ONE_PLAYER_REMOVED, playerNamesConcatenated, concat(getChatNames(chats, true)));
			break;
		default:
			sendSynchro(sender, EChatConfigRemoveMessageCode.CHAT_REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED, playerNamesConcatenated, concat(getChatNames(chats, true)));
			break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Stream<String> players = getNotFreePlayers(asList(args)).map(player -> player.getName());

		// Adding all to delete all registered players
		if (args.length == 1)
			return filter(Stream.concat(players, Stream.of(IGameConfigurationHelper.ALL)), args);

		// If the first argument is all -> any player is proposed
		// Else propose not already mentioned players
		return filter(args[0].equals(IGameConfigurationHelper.ALL) ? emptyStream() : players, args);
	}

}
