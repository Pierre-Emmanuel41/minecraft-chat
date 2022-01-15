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

public class ChatsRemovePlayerNode extends ChatsNode {

	/**
	 * Creates a node in order to remove players from a chat.
	 * 
	 * @param chats The list of chats associated to this node.
	 */
	protected ChatsRemovePlayerNode(Supplier<IChatList> chats) {
		super(chats, "player", EChatCode.CHATS__REMOVE_PLAYER__EXPLANATION, c -> c != null);
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

			List<String> alreadyMentionned = asList(extract(args, 1));
			Predicate<IChat> filter = chat -> !alreadyMentionned.contains(chat.getName());
			return filter(getChats().getChats(player).stream().filter(filter).map(chat -> chat.getName()), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		try {
			if ((player = PlayerManager.getPlayer(args[0])) == null) {
				send(eventBuilder(sender, EChatCode.CHATS__REMOVE_PLAYER__PLAYER_NOT_FOUND, args[0]));
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHATS__REMOVE_PLAYER__NAME_IS_MISSING).build());
			return false;
		}

		String[] names = extract(args, 1);
		List<IChat> chats = new ArrayList<IChat>();
		for (String name : names) {
			Optional<IChat> optChat = getChats().getChat(name);
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHATS__REMOVE_PLAYER__CHAT_NOT_FOUND, player.getName(), name));
				return false;
			}

			if (!optChat.get().getPlayers().getPlayer(player.getName()).isPresent())
				continue;

			chats.add(optChat.get());
		}

		String chatNames = concat(chats.stream().map(chat -> chat.getColoredName(EColor.GOLD)).collect(Collectors.toList()), ", ");
		for (IChat chat : chats)
			chat.getPlayers().remove(player);

		switch (chats.size()) {
		case 0:
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_PLAYER__PLAYER_NOT_REMOVED, player.getName());
			break;
		case 1:
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_PLAYER__PLAYER_REMOVED_FROM_ONE_CHAT, player.getName(), chatNames);
			break;
		default:
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_PLAYER__PLAYER_REMOVED_FROM_SEVERAL_CHATS, player.getName(), chatNames);
			break;
		}
		return true;
	}
}
