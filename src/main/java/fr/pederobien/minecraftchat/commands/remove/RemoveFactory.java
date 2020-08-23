package fr.pederobien.minecraftchat.commands.remove;

import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class RemoveFactory {

	/**
	 * @return An edition to remove a chat from a chat configuration.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> removeChat() {
		return new RemoveChat<T>();
	}

	/**
	 * @return An edition to remove a player from a chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> removePlayer() {
		return new RemovePlayer<T>();
	}
}
