package fr.pederobien.minecraft.chat.commands;

import java.util.function.Function;

import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;

public class SuperChatListNode extends MinecraftCodeNode {
	private ISuperChatList list;

	/**
	 * Create a chats node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param chats       The list of chats list associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected SuperChatListNode(ISuperChatList list, String label, IMinecraftCode explanation, Function<ISuperChatList, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(list));
		this.list = list;
	}

	/**
	 * Create a chats node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param chats       The list of chats list associated to this node.
	 * @param chats       The list of chats associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected SuperChatListNode(ISuperChatList list, String label, IMinecraftCode explanation) {
		super(label, explanation, () -> list != null);
		this.list = list;
	}

	/**
	 * @return The list of chats associated to this node.
	 */
	public ISuperChatList getList() {
		return list;
	}
}
