package fr.pederobien.minecraft.chat.commands.opmsg;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;

public class OpMsgCommandTree {
	private ISuperChatList list;
	private IMinecraftCodeRootNode root;
	private OpMsgToChatNode toChatNode;
	private OpMsgToListNode toListNode;
	private OpMsgToAllNode toAllNode;

	/**
	 * Creates a node in order to send a message to each chat from a chat list.
	 * 
	 * @param list The list that contains a list of chats associated to this node.
	 */
	public OpMsgCommandTree(ISuperChatList list) {
		root = new MinecraftCodeRootNode("opMsg", EChatCode.OP_MSG__EXPLANATION, () -> list != null);
		root.add(toChatNode = new OpMsgToChatNode(list));
		root.add(toListNode = new OpMsgToListNode(list));
		root.add(toAllNode = new OpMsgToAllNode(list));
	}

	/**
	 * @return The super list of chats associated to this command tree.
	 */
	public ISuperChatList getList() {
		return list;
	}

	/**
	 * Set the new super list of chats associated to this node.
	 * 
	 * @param list The new super list.
	 */
	public void setList(ISuperChatList list) {
		this.list = list;
	}

	/**
	 * @return The root of this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node to send a message as operator to a chat.
	 */
	public OpMsgToChatNode getToChatNode() {
		return toChatNode;
	}

	/**
	 * @return The node to send a message as operator to each chat of a chats list.
	 */
	public OpMsgToListNode getToListNode() {
		return toListNode;
	}

	/**
	 * @return The node to send a message as operator to each chat from each chats list of the super list.
	 */
	public OpMsgToAllNode getToAllNode() {
		return toAllNode;
	}
}
