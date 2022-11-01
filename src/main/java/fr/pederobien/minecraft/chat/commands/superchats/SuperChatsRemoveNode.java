package fr.pederobien.minecraft.chat.commands.superchats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.commands.SuperChatListNode;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class SuperChatsRemoveNode extends SuperChatListNode {

	/**
	 * Creates a node to remove chats lists from a list.
	 * 
	 * @param list The super chat list associated to this node.
	 */
	protected SuperChatsRemoveNode(ISuperChatList list) {
		super(list, "remove", EChatCode.CHAT_CONFIG__REMOVE__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(Stream.concat(getList().stream().map(list -> list.getName()), Stream.of("all")), args);
		default:
			List<String> listNames = asList(args);
			Stream<String> chats = getList().stream().map(list -> list.getName()).filter(name -> !listNames.contains(name));

			// If the first argument is all -> any chat is proposed
			// Else propose not already mentioned chats
			return filter(args[0].equals("all") ? emptyStream() : chats, args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String first;
		try {
			first = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__REMOVE__NAME_IS_MISSING).build());
			return false;
		}

		// Clearing the list from its chats lists.
		if (first.equals("all")) {
			getList().clear();
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__ALL_CHATS_REMOVED, getList().getName());
			return true;
		}

		List<IChatList> chatsLists = new ArrayList<IChatList>();
		for (String name : args) {
			Optional<IChatList> optChatList = getList().getChats(name);

			// Checking if the chat name refers to a registered chats list.
			if (!optChatList.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__REMOVE__LIST_NOT_FOUND, name, getList().getName()));
				return false;
			}

			chatsLists.add(optChatList.get());
		}

		String listNames = concat(args);

		for (IChatList list : chatsLists)
			getList().remove(list);

		switch (chatsLists.size()) {
		case 0:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__NO_LIST_REMOVED, getList().getName());
			break;
		case 1:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__ONE_LIST_REMOVED, listNames, getList().getName());
			break;
		default:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__SEVERAL_LISTS_REMOVED, listNames, getList().getName());
			break;
		}

		return true;
	}
}
