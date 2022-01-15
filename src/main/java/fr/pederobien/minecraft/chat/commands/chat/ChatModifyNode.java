package fr.pederobien.minecraft.chat.commands.chat;

import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;

public class ChatModifyNode extends ChatNode {
	private ChatModifyNameNode nameNode;
	private ChatModifyColorNode colorNode;

	/**
	 * Creates a node to modify the characteristics of the chat.
	 * 
	 * @param chat The chat associated to this node.
	 */
	protected ChatModifyNode(Supplier<IChat> chat) {
		super(chat, "modify", EChatCode.CHAT__MODIFY__EXPLANATION, c -> c != null);
		add(nameNode = new ChatModifyNameNode(chat));
		add(colorNode = new ChatModifyColorNode(chat));
	}

	/**
	 * @return The node to rename the current chat.
	 */
	public ChatModifyNameNode getNameNode() {
		return nameNode;
	}

	/**
	 * @return The node to modify the color of the current chat.
	 */
	public ChatModifyColorNode getColorNode() {
		return colorNode;
	}
}
