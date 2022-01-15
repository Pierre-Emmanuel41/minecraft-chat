package fr.pederobien.minecraft.chat.commands.chats;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;

public class ChatsNode extends MinecraftCodeNode {
	private Supplier<IChatList> chats;

	/**
	 * Create a chats list node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected ChatsNode(Supplier<IChatList> chats, String label, IMinecraftCode explanation, Function<IChatList, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(chats.get()));
		this.chats = chats;
	}

	/**
	 * @return The list of chats associated to this node.
	 */
	public IChatList getChats() {
		return chats.get();
	}
}
