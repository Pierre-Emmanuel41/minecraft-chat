package fr.pederobien.minecraft.chat.commands.opmsg;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.commands.SuperChatListNode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class OpMsgToAllNode extends SuperChatListNode {

	/**
	 * Creates a node to send a message to each chat from each chats list.
	 * 
	 * @param list The super list of chats associated to this node.
	 */
	protected OpMsgToAllNode(ISuperChatList list) {
		super(list, "all", EChatCode.OP_MSG__TO_ALL__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return asList(getMessage(sender, EChatCode.CHAT__MESSAGE_COMPLETION));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String message = concat(args, " ");
		if (message.equals(""))
			return true;

		for (IChatList list : getList())
			for (IChat chat : list)
				chat.sendMessage(sender, message);
		return true;
	}
}
