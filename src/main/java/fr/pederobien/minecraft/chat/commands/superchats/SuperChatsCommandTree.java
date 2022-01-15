package fr.pederobien.minecraft.chat.commands.superchats;

import fr.pederobien.minecraft.chat.commands.chats.ChatsCommandTree;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;

public class SuperChatsCommandTree {
	private ISuperChatList lists;
	private IMinecraftCodeRootNode root;

	private ChatsCommandTree chatsTree;
	private SuperChatsAddNode addNode;
	private SuperChatsRemoveNode removeNode;
	private SuperChatsModifyNode modifyNode;

	public SuperChatsCommandTree(ISuperChatList lists) {
		this.lists = lists;

		root = new MinecraftCodeRootNode("chatConfig", EChatCode.CHAT_CONFIG__ROOT__EXPLANATION, () -> true);

		chatsTree = new ChatsCommandTree();
		root.add(addNode = new SuperChatsAddNode(lists, chatsTree));
		root.add(removeNode = new SuperChatsRemoveNode(lists));
		root.add(modifyNode = new SuperChatsModifyNode(lists, chatsTree));
	}

	/**
	 * @return The list that contains a list of chats
	 */
	public ISuperChatList getLists() {
		return lists;
	}

	/**
	 * @return The root of this tree
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node to add a chats list to the list.
	 */
	public SuperChatsAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node to remove chats lists to the list.
	 */
	public SuperChatsRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node to modify a chats list or a chat.
	 */
	public SuperChatsModifyNode getModifyNode() {
		return modifyNode;
	}
}
