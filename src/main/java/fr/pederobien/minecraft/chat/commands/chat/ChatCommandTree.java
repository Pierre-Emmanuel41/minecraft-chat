package fr.pederobien.minecraft.chat.commands.chat;

import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;

public class ChatCommandTree {
	private IChat chat;
	private IMinecraftCodeRootNode root;
	private ChatNewNode newNode;
	private ChatModifyNode modifyNode;
	private ChatAddPlayerNode addPlayerNode;
	private ChatRemovePlayerNode removePlayerNode;

	public ChatCommandTree() {
		root = new MinecraftCodeRootNode("chat", EChatCode.CHAT__ROOT__EXPLANATION, () -> true);
		root.add(newNode = new ChatNewNode(this));
		root.add(modifyNode = new ChatModifyNode(() -> getChat()));
		root.add(addPlayerNode = new ChatAddPlayerNode(() -> getChat()));
		root.add(removePlayerNode = new ChatRemovePlayerNode(() -> getChat()));
	}

	/**
	 * @return The chat associated to this tree.
	 */
	public IChat getChat() {
		return chat;
	}

	/**
	 * Set the chat associated to this tree.
	 * 
	 * @param chat The new chat associated to this tree.
	 */
	public void setChat(IChat chat) {
		this.chat = chat;
	}

	/**
	 * @return The root of this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node to create a new chat.
	 */
	public ChatNewNode getNewNode() {
		return newNode;
	}

	/**
	 * @return The node to modify the characteristics of the current chat.
	 */
	public ChatModifyNode getModifyNode() {
		return modifyNode;
	}

	/**
	 * @return The node to add players to the current chat.
	 */
	public ChatAddPlayerNode getAddPlayerNode() {
		return addPlayerNode;
	}

	/**
	 * @return The node to remove players from the current chat.
	 */
	public ChatRemovePlayerNode getRemovePlayerNode() {
		return removePlayerNode;
	}
}
