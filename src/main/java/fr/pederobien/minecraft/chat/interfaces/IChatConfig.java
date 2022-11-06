package fr.pederobien.minecraft.chat.interfaces;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public interface IChatConfig extends IFeature {

	/**
	 * @return The chat that contains all the players currently connected in game.
	 */
	IChat getGlobalChat();

	/**
	 * @return The chat that contains only operators.
	 */
	IChat getOperatorChat();

	/**
	 * @return The list of chats associated to this feature.
	 */
	IChatList getChats();
}
