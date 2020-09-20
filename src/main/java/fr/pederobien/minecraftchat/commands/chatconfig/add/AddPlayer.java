package fr.pederobien.minecraftchat.commands.chatconfig.add;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.commands.chatconfig.AbstractChatEdition;
import fr.pederobien.minecraftchat.exception.PlayerAlreadyRegisteredInChatException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.exceptions.PlayerNotFoundException;

public class AddPlayer<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected AddPlayer() {
		super(EChatConfigAddLabel.PLAYER, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name = "";
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__CHAT_NAME_IS_MISSING);
			return false;
		}

		String[] playerNames = extract(args, 1);
		String playerNamesConcatenated = null;
		IChat chat = null;
		List<Player> players = new ArrayList<Player>();

		try {
			players = getPlayers(playerNames);
			playerNamesConcatenated = concat(getPlayerNames(players));
			Optional<IChat> optChat = get().getChat(name);
			if (!optChat.isPresent()) {
				sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__CHAT_DOES_NOT_EXIST, name, get().getName());
				return false;
			}
			chat = optChat.get();
			for (Player player : players)
				chat.add(player);
		} catch (PlayerNotFoundException e) {
			sendSynchro(sender, ECommonMessageCode.COMMON_PLAYER_DOES_NOT_EXIST, e.getPlayerName());
			return false;
		} catch (PlayerAlreadyRegisteredInChatException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__PLAYER_ALREADY_REGISTERED_IN_CHAT, e.getPlayer().getName(), e.getChat().getColoredName());
			return false;
		}

		switch (playerNames.length) {
		case 0:
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__ANY_PLAYER_ADDED);
			break;
		case 1:
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__ONE_PLAYER_ADDED, playerNamesConcatenated, chat.getColoredName());
			break;
		default:
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_PLAYER__SEVERAL_PLAYERS_ADDED, playerNamesConcatenated, chat.getColoredName());
			break;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(get().getChats().stream().map(chat -> chat.getName()), args);
		default:
			return filter(getPlayers(asList(extract(args, 1))).map(player -> player.getName()), args);
		}
	}
}
