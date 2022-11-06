package fr.pederobien.minecraft.chat.commands;

import java.util.function.Supplier;

import com.google.common.base.Function;

import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;

public class ChatConfigNode extends MinecraftCodeNode {
	private Supplier<IChatConfig> config;

	/**
	 * Create a minecraft node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected ChatConfigNode(Supplier<IChatConfig> config, String label, IMinecraftCode explanation, Function<IChatConfig, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(config == null ? null : config.get()));
		this.config = config;
	}

	/**
	 * @return The chat configuration associated to this node.
	 */
	public IChatConfig getConfig() {
		return config == null ? null : config.get();
	}

	/**
	 * @return The list of chats associated to this node.
	 */
	public IChatList getChats() {
		return getConfig() == null ? null : getConfig().getChats();
	}
}
