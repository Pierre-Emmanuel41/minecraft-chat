package fr.pederobien.minecraft.chat.commands.opmsg;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.google.common.base.Predicate;

import fr.pederobien.minecraft.chat.commands.SuperChatListNode;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class OpMsgToChatNode extends SuperChatListNode {

	/**
	 * Creates a node to send a message to one chat from a chats list.
	 * 
	 * @param list The super list of chats associated to this node.
	 */
	protected OpMsgToChatNode(ISuperChatList list) {
		super(list, "toChat", EChatCode.OP_MSG__TO_CHAT__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getList().stream().map(list -> list.getName()), args);
		case 2:
			Optional<IChatList> optList = getList().getChats(args[0]);
			if (!optList.isPresent())
				return emptyList();

			List<String> alreadyMentionned = asList(extract(args, 1));
			Predicate<IChat> filter = chat -> !alreadyMentionned.contains(chat.getName());
			return filter(optList.get().stream().filter(filter).map(chat -> chat.getName()), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String listName;
		try {
			listName = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.OP_MSG__TO_CHAT__LIST_NAME_IS_MISSING).build());
			return false;
		}

		Optional<IChatList> optChats = getList().getChats(listName);
		if (!optChats.isPresent()) {
			send(eventBuilder(sender, EChatCode.OP_MSG__TO_CHAT__LIST_NOT_FOUND, listName));
			return false;
		}

		String chatName;
		try {
			chatName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.OP_MSG__TO_CHAT__CHAT_NAME_IS_MISSING, listName));
			return false;
		}

		Optional<IChat> optChat = optChats.get().getChat(chatName);
		if (!optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.OP_MSG__TO_CHAT__CHAT_NOT_FOUND, chatName));
			return false;
		}

		String message = concat(extract(args, 2), " ");
		if (message.equals(""))
			return true;

		optChat.get().sendMessage(sender, message);
		return true;
	}
}
