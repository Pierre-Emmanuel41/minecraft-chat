package fr.pederobien.minecraftchat.interfaces;

import java.util.List;
import java.util.Optional;

import fr.pederobien.minecraftchat.exception.ChatAlreadyRegisteredException;
import fr.pederobien.minecraftchat.exception.ChatNotRegisteredException;
import fr.pederobien.minecraftgameplateform.interfaces.element.INominable;

public interface IChatConfiguration extends INominable {

	/**
	 * Creates a new chat with the given name.
	 * 
	 * @param name The name of the chat to create.
	 * @return The created chat.
	 * 
	 * @throws ChatAlreadyRegisteredException if a chat with the given name is already registered in this configuration.
	 */
	IChat register(String name);

	/**
	 * Unregisters the chat associated to the given name.
	 * 
	 * @param name The name of the chat to unregister.
	 * @return The unregistered chat.
	 * 
	 * @throws ChatNotRegisteredException if there is any registered chat associated to the given name.
	 */
	IChat unRegister(String name);

	/**
	 * Get the chat associated to the given name.
	 * 
	 * @param name The name of the chat to retrieve.
	 * 
	 * @return An optional that contains the chat associated to the given name if registered, an empty optional otherwise.
	 */
	Optional<IChat> getChat(String name);

	/**
	 * @return The list of registered chats. This list is unmodifiable.
	 */
	List<IChat> getChats();
}
