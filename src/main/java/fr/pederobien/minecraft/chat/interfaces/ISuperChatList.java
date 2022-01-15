package fr.pederobien.minecraft.chat.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

public interface ISuperChatList extends Iterable<IChatList> {

	/**
	 * @return The name of this list
	 */
	String getName();

	/**
	 * Adds the given chats list to this list.
	 * 
	 * @param list The list of chats to add.
	 * 
	 * @throws ChatListAlreadyRegisteredException If a list is already registered for the list name.
	 */
	void add(IChatList list);

	/**
	 * Removes the chats list associated to the given name.
	 * 
	 * @param name The chats list name to remove.
	 * 
	 * @return The removed list if registered, null otherwise.
	 */
	IChatList remove(String name);

	/**
	 * Removes the given chats list from this list.
	 * 
	 * @param list The chats list to remove.
	 * 
	 * @return True if the list was registered, false otherwise.
	 */
	boolean remove(IChatList list);

	/**
	 * Removes all registered chats list.
	 */
	void clear();

	/**
	 * Get the chats list associated to the given name.
	 * 
	 * @param name The name of the chats list to retrieve.
	 * 
	 * @return An optional that contains the list associated to the given name if registered, an empty optional otherwise.
	 */
	Optional<IChatList> getChats(String name);

	/**
	 * Get the list of chats in which a player is registered.
	 * 
	 * @param player The player used to get the list of chat in which it is registered.
	 * 
	 * @return The list of chat in which the player is registered.
	 */
	List<IChat> getChats(Player player);

	/**
	 * Get the chat associated to the given name and whose the player list contains the specified player.
	 * 
	 * @param name   The chat name.
	 * @param player The player the chat should contains.
	 * 
	 * @return The chat associated to the given name and that contains a the specified player, or null.
	 */
	Optional<IChat> getChat(String name, Player player);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<IChatList> stream();

	/**
	 * @return A copy of the underlying list.
	 */
	List<IChatList> toList();

}
