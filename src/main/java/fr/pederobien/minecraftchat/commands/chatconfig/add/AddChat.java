package fr.pederobien.minecraftchat.commands.chatconfig.add;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.commands.chatconfig.AbstractChatEdition;
import fr.pederobien.minecraftchat.exception.ChatNameForbiddenException;
import fr.pederobien.minecraftchat.exception.ChatWithSameColorAlreadyExistsException;
import fr.pederobien.minecraftchat.exception.ChatWithSameNameAlreadyExistsException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.exceptions.ColorNotFoundException;
import fr.pederobien.minecraftgameplateform.exceptions.PlayerNotFoundException;
import fr.pederobien.minecraftmanagers.EColor;

public class AddChat<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected AddChat() {
		super(EChatConfigAddLabel.CHAT, EChatConfigAddMessageCode.CHAT_ADD_CHAT__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String chatName = "";
		try {
			chatName = args[0];
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__CHAT_NAME_IS_MISSING, get().getName());
			return false;
		}

		EColor chatColor = null;
		try {
			chatColor = getColor(args[1]);
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__COLOR_NAME_IS_MISSING, chatName, get().getName());
			return false;
		} catch (ColorNotFoundException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__COLOR_DOES_NOT_EXIST, chatName, get().getName(), e.getColorName());
			return false;
		}

		IChat chat = null;
		try {
			chat = get().register(chatName, chatColor);
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__CHAT_ADDED, chat.getColoredName(), get().getName());
		} catch (ChatNameForbiddenException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__CHAT_NAME_FORBIDDEN, e.getForbiddenName(), get().getName());
			return false;
		} catch (ChatWithSameNameAlreadyExistsException e) {
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__CHAT_NAME_ALREADY_USED, e.getAlreadyExistingChat().getColoredName(), get().getName());
			return false;
		} catch (ChatWithSameColorAlreadyExistsException e) {
			IChat alreadyExistingChat = e.getAlreadyExistingChat();
			sendSynchro(sender, EChatConfigAddMessageCode.CHAT_ADD_CHAT__COLOR_ALREADY_USED, chatName, get().getName(), alreadyExistingChat.getColor(),
					alreadyExistingChat.getColoredName());
			return false;
		}

		String[] playerNames = extract(args, 2);
		String playerNamesConcatenated = null;
		List<Player> players = new ArrayList<Player>();
		try {
			players = getPlayers(playerNames);
			playerNamesConcatenated = concat(getPlayerNames(players));
			for (Player player : players)
				chat.add(player);
		} catch (PlayerNotFoundException e) {
			sendSynchro(sender, ECommonMessageCode.COMMON_PLAYER_DOES_NOT_EXIST, e.getPlayerName());
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
			return asList(getMessage(sender, ECommonMessageCode.COMMON_NEW_TAB_COMPLETE));
		case 2:
			return filter(getFreeColorNames(false).stream(), args);
		default:
			return filter(getPlayers(asList(extract(args, 2))).map(player -> player.getName()), args);
		}
	}
}
