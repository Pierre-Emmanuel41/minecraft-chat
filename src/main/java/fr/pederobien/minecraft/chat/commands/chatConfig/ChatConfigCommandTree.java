package fr.pederobien.minecraft.chat.commands.chatConfig;

import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;

public class ChatConfigCommandTree {
	private IChatConfig config;
	private IMinecraftCodeRootNode root;

	/**
	 * Creates a command tree in order to manage a chat configuration.
	 * 
	 * @param config The chat configuration associated to this command tree.
	 */
	public ChatConfigCommandTree(IChatConfig config) {
		this.config = config;

		root = new ChatConfigRoot(this);
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
}
