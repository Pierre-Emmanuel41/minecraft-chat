package fr.pederobien.minecraft.chat.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.exception.ChatAlreadyRegisteredException;
import fr.pederobien.minecraft.managers.EColor;

public interface IChatList extends Iterable<IChat> {

	/**
	 * @return The name of this list
	 */
	String getName();

	/**
	 * Adds the given chat to this list.
	 * 
	 * @param chat The chat to add.
	 * 
	 * @throws ChatAlreadyRegisteredException if a chat with the given name is already registered in this configuration.
	 */
	void add(IChat chat);

	/**
	 * Removes the chat associated to the given name.
	 * 
	 * @param name The chat name to remove.
	 * 
	 * @return The removed chat if registered, null otherwise.
	 */
	IChat remove(String name);

	/**
	 * Removes the given chat from this list.
	 * 
	 * @param chat The chat to remove.
	 * 
	 * @return True if the chat was registered, false otherwise.
	 */
	boolean remove(IChat chat);

	/**
	 * Removes the chat associated to the given color.
	 * 
	 * @param color The color used to remove the associated chat.
	 * 
	 * @return The removed chat if registered, null otherwise.
	 */
	IChat remove(EColor color);

	/**
	 * Removes all registered chats. It also clears the list of players.
	 */
	void clear();

	/**
	 * Get the chat associated to the given name.
	 * 
	 * @param name The name of the chat to retrieve.
	 * 
	 * @return An optional that contains the chat associated to the given name if registered, an empty optional otherwise.
	 */
	Optional<IChat> getChat(String name);

	/**
	 * Get the chat associated to the given color.
	 * 
	 * @param color The color of the chat to retrieve.
	 * 
	 * @return An optional that contains the chat associated to the given color if registered, an empty optional otherwise.
	 */
	Optional<IChat> getChat(EColor color);

	/**
	 * Get the list of chat in which the given player is registered.
	 * 
	 * @param player The player used to get its chats.
	 * 
	 * @return The list of chat in which the given player is registered.
	 */
	List<IChat> getChats(Player player);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<IChat> stream();

	/**
	 * @return The list of registered chats. This list is unmodifiable.
	 */
	List<IChat> toList();

	/**
	 * Appends or moves the specified player in the given chat. If the player was registered in one or several chats, then the players
	 * is removed from them.
	 * 
	 * @param player The player to move.
	 * @param chats  The new player chats.
	 * 
	 * @return The list of chats in which the player was registered, an empty list otherwise.
	 */
	List<IChat> movePlayer(Player player, IChat... chats);
}
