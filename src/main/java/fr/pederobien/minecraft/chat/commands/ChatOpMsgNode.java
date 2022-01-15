package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class ChatOpMsgNode extends SuperChatListNode {

	/**
	 * Creates a node in order to send a message to each chat from a chat list.
	 * 
	 * @param list The list that contains a list of chats associated to this node.
	 */
	public ChatOpMsgNode(ISuperChatList list) {
		super(list, "chatOpMsg", EChatCode.CHAT_OP_MSG__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getList().stream().map(list -> list.getName()), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		Optional<IChatList> optList = getList().getChats(name);
		if (!optList.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_OP_MSG__LIST_NOT_FOUND, name));
			return false;
		}

		for (IChat chat : optList.get())
			chat.sendMessage(sender, concat(extract(args, 1), " "));

		return true;
	}
}
