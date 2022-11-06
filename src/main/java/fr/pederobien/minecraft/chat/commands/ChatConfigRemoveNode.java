package fr.pederobien.minecraft.chat.commands;

import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.interfaces.IChatConfig;

public class ChatConfigRemoveNode extends ChatConfigNode {
	private ChatConfigRemoveChatNode chatNode;
	private ChatConfigRemovePlayerNode playerNode;

	/**
	 * Creates a anode to remove a chat from a chats list or players from a chat.
	 * 
	 * @param config The chats list associated to this node.
	 */
	protected ChatConfigRemoveNode(Supplier<IChatConfig> config) {
		super(config, "remove", EChatCode.CHAT_CONFIG__REMOVE__EXPLANATION, c -> c != null);

		add(chatNode = new ChatConfigRemoveChatNode(config));
		add(playerNode = new ChatConfigRemovePlayerNode(config));
	}

	/**
	 * @return The node that removes chats from a list.
	 */
	public ChatConfigRemoveChatNode getChatNode() {
		return chatNode;
	}

	/**
	 * @return The node that removes players from a chat.
	 */
	public ChatConfigRemovePlayerNode getPlayerNode() {
		return playerNode;
	}
}
