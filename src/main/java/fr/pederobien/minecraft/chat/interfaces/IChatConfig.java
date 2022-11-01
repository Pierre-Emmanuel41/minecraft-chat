package fr.pederobien.minecraft.chat.interfaces;

public interface IChatConfig {

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
