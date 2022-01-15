package fr.pederobien.minecraft.chat.commands.chats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Predicate;

import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.PlayerManager;

public class ChatsMoveNode extends ChatsNode {

	/**
	 * Creates a node in order to move a player from one chat to another one.
	 * 
	 * @param chats The list of chats associated to this node.
	 */
	protected ChatsMoveNode(Supplier<IChatList> chats) {
		super(chats, "move", EChatCode.CHATS__MOVE__EXPLANATION, c -> c != null && c.toList().size() > 1);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			List<Player> players = new ArrayList<Player>();
			for (IChat chat : getChats())
				for (Player player : chat.getPlayers())
					players.add(player);
			return filter(players.stream().map(player -> player.getName()), args);
		default:
			Player player = PlayerManager.getPlayer(args[0]);
			if (player == null)
				return emptyList();

			List<String> alreadyMentionned = asList(args);
			Predicate<IChat> filter = chat -> !alreadyMentionned.contains(chat.getName()) && !chat.getPlayers().getPlayer(player.getName()).isPresent();
			return filter(getChats().stream().filter(filter).map(chat -> chat.getName()), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String playerName;
		try {
			playerName = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHATS__MOVE__PLAYER_NAME_IS_MISSING).build());
			return false;
		}

		Player player = PlayerManager.getPlayer(playerName);
		if (player == null) {
			send(eventBuilder(sender, EChatCode.CHATS__MOVE__PLAYER_NOT_FOUND, playerName));
			return false;
		}

		String[] names = extract(args, 1);
		IChat[] chats = new IChat[names.length];
		for (int i = 0; i < chats.length; i++) {
			Optional<IChat> optChat = getChats().getChat(names[i]);
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHATS__MOVE__CHAT_NOT_FOUND, playerName, names[i]));
				return false;
			}

			if (optChat.get().getPlayers().getPlayer(playerName).isPresent()) {
				send(eventBuilder(sender, EChatCode.CHAT__ADD_PLAYER__PLAYER_ALREADY_REGISTERED, playerName, optChat.get().getColoredName()));
				return false;
			}

			chats[i] = optChat.get();
		}

		List<IChat> oldChats = getChats().movePlayer(player, chats);
		String oldChatNames = concat(oldChats.stream().map(chat -> chat.getColoredName(EColor.GOLD)).collect(Collectors.toList()), ", ");
		String newChatNames = concat(asList(chats).stream().map(chat -> chat.getColoredName(EColor.GOLD)).collect(Collectors.toList()), ", ");

		if (!oldChats.isEmpty())
			sendSuccessful(sender, EChatCode.CHATS__MOVE__PLAYER_MOVED_FROM_TO, playerName, oldChatNames, newChatNames);
		else
			sendSuccessful(sender, EChatCode.CHAT__ADD_PLAYER__ONE_PLAYER_ADDED, playerName, newChatNames);
		return true;
	}
}
