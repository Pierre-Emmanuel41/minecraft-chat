package fr.pederobien.minecraft.chat.commands.chats;

import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.commands.chat.ChatCommandTree;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatsAddNode extends ChatsNode {
	private ChatsAddChatNode chatNode;
	private ChatsAddPlayerNode playerNode;

	/**
	 * Creates a node in order to add a chat to a list of chats or to add players to a chat.
	 * 
	 * @param chats    The list of chats associated to this node.
	 * @param chatTree The command tree in order create or modify a chat.
	 */
	protected ChatsAddNode(Supplier<IChatList> chats, ChatCommandTree chatTree) {
		super(chats, "add", EChatCode.CHATS__ADD__EXPLANATION, c -> c != null);
		add(chatNode = new ChatsAddChatNode(chats, chatTree));
		add(playerNode = new ChatsAddPlayerNode(chats, chatTree));
	}

	/**
	 * @return The node to add chats to the chats list.
	 */
	public ChatsAddChatNode getChatNode() {
		return chatNode;
	}

	/**
	 * @return The node to add players to a chat.
	 */
	public ChatsAddPlayerNode getPlayerNode() {
		return playerNode;
	}
}
