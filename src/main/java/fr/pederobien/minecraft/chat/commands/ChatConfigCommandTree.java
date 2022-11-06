package fr.pederobien.minecraft.chat.commands;

import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;

public class ChatConfigCommandTree {
	private IChatConfig config;
	private IMinecraftCodeRootNode root;
	private ChatConfigAddNode addNode;
	private ChatConfigModifyNode modifyNode;
	private ChatConfigListNode listNode;
	private ChatConfigRemoveNode removeNode;
	private ChatConfigMoveNode moveNode;

	/**
	 * Creates a command tree in order to manage a chat configuration.
	 * 
	 * @param config The chat configuration associated to this command tree.
	 */
	public ChatConfigCommandTree(IChatConfig config) {
		this.config = config;

		root = new ChatConfigRoot(this);
		root.add(addNode = new ChatConfigAddNode(() -> getConfig()));
		root.add(modifyNode = new ChatConfigModifyNode(() -> getConfig()));
		root.add(listNode = ChatConfigListNode.newInstance(() -> getConfig() == null ? null : getConfig().getChats()));
		root.add(removeNode = new ChatConfigRemoveNode(() -> getConfig()));
		root.add(moveNode = new ChatConfigMoveNode(() -> getConfig()));
	}

	/**
	 * @return The chat configuration associated to this command tree.
	 */
	public IChatConfig getConfig() {
		return config;
	}

	/**
	 * Set the chat configuration associated to this command tree.
	 * 
	 * @param config The new chat configuration of this tree.
	 */
	public void setConfig(IChatConfig config) {
		this.config = config;
	}

	/**
	 * @return The root of this command tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node that adds a chat to a chats list or players to a chat.
	 */
	public ChatConfigAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that renames a chat.
	 */
	public ChatConfigModifyNode getModifyNode() {
		return modifyNode;
	}

	/**
	 * @return The node that display all the chats registered in a chats list.
	 */
	public ChatConfigListNode getListNode() {
		return listNode;
	}

	/**
	 * @return The node that removes chats from a list or players from a chat.
	 */
	public ChatConfigRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node that moves a player from a chats list to another one.
	 */
	public ChatConfigMoveNode getMoveNode() {
		return moveNode;
	}
}
