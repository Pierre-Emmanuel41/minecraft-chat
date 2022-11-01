package fr.pederobien.minecraft.chat.commands.chats;

import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatsRemoveNode extends ChatsNode {
	private ChatsRemoveChatNode chatNode;
	private ChatsRemovePlayerNode playerNode;

	/**
	 * Creates a node in order to remove chats or players from a chat.
	 * 
	 * @param chats The list of chats associated to this node.
	 */
	protected ChatsRemoveNode(Supplier<IChatList> chats) {
		super(chats, "remove", EChatCode.CHATS__REMOVE__EXPLANATION, c -> c != null);
		add(chatNode = new ChatsRemoveChatNode(chats));
		add(playerNode = new ChatsRemovePlayerNode(chats));
	}

	/**
	 * @return The node to add chats to a list of chats.
	 */
	public ChatsRemoveChatNode getChatNode() {
		return chatNode;
	}

	/**
	 * @return The node to remove players from a chat.
	 */
	public ChatsRemovePlayerNode getPlayerNode() {
		return playerNode;
	}
}
