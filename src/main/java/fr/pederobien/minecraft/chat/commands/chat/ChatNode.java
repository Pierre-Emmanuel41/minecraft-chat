package fr.pederobien.minecraft.chat.commands.chat;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;

public class ChatNode extends MinecraftCodeNode {
	private Supplier<IChat> chat;

	/**
	 * Create a chat node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param chat        the chat associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected ChatNode(Supplier<IChat> chat, String label, IMinecraftCode explanation, Function<IChat, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(chat == null ? null : chat.get()));
		this.chat = chat;
	}

	/**
	 * @return The chat associated to this node.
	 */
	public IChat getChat() {
		return chat == null ? null : chat.get();
	}
}
