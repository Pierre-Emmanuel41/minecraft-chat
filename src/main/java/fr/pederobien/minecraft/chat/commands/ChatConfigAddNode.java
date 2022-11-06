package fr.pederobien.minecraft.chat.commands;

import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.interfaces.IChatConfig;

public class ChatConfigAddNode extends ChatConfigNode {
	private ChatConfigAddChat chatNode;
	private ChatConfigAddPlayerNode playerNode;

	/**
	 * Creates a node in order to add a chat to a chat configuration or players to a chat.
	 * 
	 * @param config The configuration associated to this node.
	 */
	protected ChatConfigAddNode(Supplier<IChatConfig> config) {
		super(config, "add", EChatCode.CHAT_CONFIG__ADD__EXPLANATION, c -> c != null);

		add(chatNode = new ChatConfigAddChat(config));
		add(playerNode = new ChatConfigAddPlayerNode(config));
	}

	/**
	 * @return The node that adds a chat to a chats list.
	 */
	public ChatConfigAddChat getChatNode() {
		return chatNode;
	}

	/**
	 * @return The node that adds a player to a chat.
	 */
	public ChatConfigAddPlayerNode getPlayerNode() {
		return playerNode;
	}
}
