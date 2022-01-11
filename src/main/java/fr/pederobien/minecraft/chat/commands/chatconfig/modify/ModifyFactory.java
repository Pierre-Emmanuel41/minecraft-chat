package fr.pederobien.minecraft.chat.commands.chatconfig.modify;

import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class ModifyFactory {

	/**
	 * @return An edition to rename a chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> modifyName() {
		return new ModifyName<T>();
	}

	/**
	 * @return An edition to modify the color of a chat.
	 */
	public static <T extends IChatConfiguration> IMapPersistenceEdition<T> modifyColor() {
		return new ModifyColor<T>();
	}
}
