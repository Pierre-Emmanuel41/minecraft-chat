package fr.pederobien.minecraft.chat.commands;

import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.interfaces.IChatConfig;

public class ChatConfigModifyNode extends ChatConfigNode {
	private ChatConfigModifyNameNode nameNode;
	private ChatConfigModifyColorNode colorNode;

	/**
	 * Creates a node in order to modify the name of a chat or the color of a chat.
	 * 
	 * @param config The chats list associated to this node.
	 */
	protected ChatConfigModifyNode(Supplier<IChatConfig> config) {
		super(config, "modify", EChatCode.CHAT_CONFIG__MODIFY__EXPLANATION, c -> c != null);

		add(nameNode = new ChatConfigModifyNameNode(config));
		add(colorNode = new ChatConfigModifyColorNode(config));
	}

	/**
	 * @return The node that renames a chat.
	 */
	public ChatConfigModifyNameNode getNameNode() {
		return nameNode;
	}

	/**
	 * @return The node that modifies the color of a chat.
	 */
	public ChatConfigModifyColorNode getColorNode() {
		return colorNode;
	}
}
