package fr.pederobien.minecraftchat.commands.chatconfig;

import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class ChatConfigEditionFactory {

	/**
	 * @return An edition to create a new chat configuration.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> newChatConfig() {
		return new NewChatConfig();
	}

	/**
	 * @return An edition to rename a chat configuration.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> renameChatConfig() {
		return new RenameChatConfig();
	}

	/**
	 * @return An edition to save a chat configuration.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> saveChatConfig() {
		return new SaveChatConfig();
	}

	/**
	 * @return An edition to display the name of all registered chat configurations.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> listChatConfig() {
		return new ListChatConfig();
	}

	/**
	 * @return An edition to delete the file of a chat configuration.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> deleteChatConfig() {
		return new DeleteChatConfig();
	}

	/**
	 * @return An edition to display the characteristics of the current chat configuration.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> detailsChatConfig() {
		return new DetailsChatConfig();
	}

	/**
	 * @return An edition to load a chat configuration.
	 */
	public static IMapPersistenceEdition<IChatConfiguration> loadChatConfig() {
		return new LoadChatConfig();
	}

	/**
	 * @return An edition to add a chat to a configuration or a player to chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> commonAdd() {
		return new CommonAdd<T>();
	}

	/**
	 * @return An edition to define if the chat configuration should be synchronized with the game to run.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> synchronizedChatConfig() {
		return new SynchronizedChatConfig<T>();
	}

	/**
	 * @return An edition to remove a chat from a chat configuration or a player from a chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> commonRemove() {
		return new CommonRemove<T>();
	}

	/**
	 * @return An edition to modify the name or the color of a chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> commonModify() {
		return new CommonModify<T>();
	}
}
