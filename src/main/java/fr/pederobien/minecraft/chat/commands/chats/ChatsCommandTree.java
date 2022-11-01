package fr.pederobien.minecraft.chat.commands.chats;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.commands.chat.ChatCommandTree;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;

public class ChatsCommandTree {
	private IChatList chats;
	private IMinecraftCodeRootNode root;
	private ChatCommandTree chatTree;
	private ChatsNewNode newNode;
	private ChatsAddNode addNode;
	private ChatsRemoveNode removeNode;
	private ChatsModifyNode modifyNode;
	private ChatsListNode listNode;
	private ChatsMoveNode moveNode;

	public ChatsCommandTree() {
		root = new MinecraftCodeRootNode("chats", EChatCode.CHATS__ROOT__EXPLANATION, () -> getChats() != null);

		chatTree = new ChatCommandTree();
		newNode = new ChatsNewNode(this);
		root.add(addNode = new ChatsAddNode(() -> getChats(), chatTree));
		root.add(removeNode = new ChatsRemoveNode(() -> getChats()));
		root.add(modifyNode = new ChatsModifyNode(() -> getChats(), chatTree));
		root.add(listNode = ChatsListNode.newInstance(() -> getChats()));
		root.add(moveNode = new ChatsMoveNode(() -> getChats()));
	}

	/**
	 * @return The list of chats associated to this tree.
	 */
	public IChatList getChats() {
		return chats;
	}

	/**
	 * Set the list of chat associated to this tree.
	 * 
	 * @param list The new chats list associated to this tree.
	 */
	public void setChats(IChatList list) {
		this.chats = list;
	}

	/**
	 * @return The root of this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The chats to create a new chats list.
	 */
	public ChatsNewNode getNewNode() {
		return newNode;
	}

	/**
	 * @return The node to add chat to a list or to add players to a chat.
	 */
	public ChatsAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node to remove chats from a list of players from a chat.
	 */
	public ChatsRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node to modify the characteristics of a chat.
	 */
	public ChatsModifyNode getModifyNode() {
		return modifyNode;
	}

	/**
	 * @return The node to display the chats list.
	 */
	public ChatsListNode getListNode() {
		return listNode;
	}

	/**
	 * @return The node to move a player from one chat to another one.
	 */
	public ChatsMoveNode getMoveNode() {
		return moveNode;
	}
}
