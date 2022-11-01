package fr.pederobien.minecraft.chat.commands.opmsg;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.SuperChatListNode;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class OpMsgToListNode extends SuperChatListNode {

	/**
	 * Creates a node to send a message to each chat from a chats list.
	 * 
	 * @param list The super list of chats associated to this node.
	 */
	protected OpMsgToListNode(ISuperChatList list) {
		super(list, "toList", EChatCode.OP_MSG__TO_LIST__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getList().stream().map(list -> list.getName()), args);
		default:
			return asList(getMessage(sender, EChatCode.CHAT__MESSAGE_COMPLETION));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.OP_MSG__TO_LIST__NAME_IS_MISSING).build());
			return false;
		}

		Optional<IChatList> optChats = getList().getChats(name);
		if (!optChats.isPresent()) {
			send(eventBuilder(sender, EChatCode.OP_MSG__TO_LIST__LIST_NOT_FOUND, name));
			return false;
		}

		String message = concat(extract(args, 1), " ");
		if (message.equals(""))
			return true;

		for (IChat chat : optChats.get())
			chat.sendMessage(sender, message);

		return true;
	}
}
