package fr.pederobien.minecraft.chat.commands.superchats;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.commands.SuperChatListNode;
import fr.pederobien.minecraft.chat.commands.chats.ChatsCommandTree;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class SuperChatsAddNode extends SuperChatListNode {
	private ChatsCommandTree chatsTree;

	/**
	 * Creates a node to add a chat list to a super chats list.
	 * 
	 * @param list      The super chats list associated to this node.
	 * @param chatsTree The command tree to create/modify a list of chats.
	 */
	protected SuperChatsAddNode(ISuperChatList list, ChatsCommandTree chatsTree) {
		super(list, "add", EChatCode.CHAT_CONFIG__ADD__EXPLANATION);
		this.chatsTree = chatsTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return chatsTree.getNewNode().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		chatsTree.getNewNode().getExceptedNames().clear();
		getList().toList().forEach(chat -> chatsTree.getNewNode().getExceptedNames().add(chat.getName()));

		boolean result = chatsTree.getNewNode().onCommand(sender, command, label, args);
		if (result) {
			IChatList list = chatsTree.getChats();
			getList().add(list);
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__ADD__LIST_ADDED, list.getName(), getList().getName());
		}
		return result;
	}
}
