package fr.pederobien.minecraft.chat.commands.superchats;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.SuperChatListNode;
import fr.pederobien.minecraft.chat.commands.chats.ChatsAddNode;
import fr.pederobien.minecraft.chat.commands.chats.ChatsCommandTree;
import fr.pederobien.minecraft.chat.commands.chats.ChatsModifyNode;
import fr.pederobien.minecraft.chat.commands.chats.ChatsMoveNode;
import fr.pederobien.minecraft.chat.commands.chats.ChatsRemoveNode;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class SuperChatsModifyNode extends SuperChatListNode {
	private ChatsCommandTree chatsTree;

	/**
	 * Creates a node to modify a list of chats.
	 * 
	 * @param list      The super chats list associated to this node.
	 * @param chatsTree The command tree to create/modify a list of chats.
	 */
	protected SuperChatsModifyNode(ISuperChatList list, ChatsCommandTree chatsTree) {
		super(list, "modify", EChatCode.CHAT_CONFIG__MODIFY__EXPLANATION, c -> c != null && !c.toList().isEmpty());
		this.chatsTree = chatsTree;
		add(chatsTree.getAddNode());
		add(chatsTree.getRemoveNode());
		add(chatsTree.getModifyNode());
		add(chatsTree.getMoveNode());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getList().stream().map(list -> list.getName()), args);
		default:
			Optional<IChatList> optList = getList().getChats(args[0]);
			if (!optList.isPresent())
				return emptyList();

			chatsTree.setChats(optList.get());
			return super.onTabComplete(sender, command, alias, extract(args, 1));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<IChatList> optList;
		try {
			optList = getList().getChats(args[0]);
			if (!optList.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__LIST_NOT_FOUND, args[0]));
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		chatsTree.setChats(optList.get());
		return super.onCommand(sender, command, label, extract(args, 1));
	}

	/**
	 * @return The node to add chat to a list or to add players to a chat.
	 */
	public ChatsAddNode getAddNode() {
		return chatsTree.getAddNode();
	}

	/**
	 * @return The node to remove chats from a list of players from a chat.
	 */
	public ChatsRemoveNode getRemoveNode() {
		return chatsTree.getRemoveNode();
	}

	/**
	 * @return The node that modifies the name of a chat.
	 */
	public ChatsModifyNode getNameNode() {
		return chatsTree.getModifyNode();
	}

	/**
	 * @return The node to move a player from one chat to another one.
	 */
	public ChatsMoveNode getMoveNode() {
		return chatsTree.getMoveNode();
	}
}
