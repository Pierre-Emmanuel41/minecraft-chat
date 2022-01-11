package fr.pederobien.minecraft.chat.commands.chatconfig.add;

import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class AddFactory {

	/**
	 * @return An edition to add chats to a configuration.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> addChat() {
		return new AddChat<T>();
	}

	/**
	 * @return An edition to add players to a chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> addPlayer() {
		return new AddPlayer<T>();
	}
}
