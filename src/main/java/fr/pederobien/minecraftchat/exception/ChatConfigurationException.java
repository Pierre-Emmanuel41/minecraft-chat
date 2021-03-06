package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.exceptions.MinecraftPlateformException;

public abstract class ChatConfigurationException extends MinecraftPlateformException {
	private static final long serialVersionUID = 1L;
	private IChatConfiguration chatConfiguration;

	protected ChatConfigurationException(IChatConfiguration chatConfiguration) {
		this.chatConfiguration = chatConfiguration;
	}

	/**
	 * @return The chat configuration in which an exception occured.
	 */
	public IChatConfiguration getConfiguration() {
		return chatConfiguration;
	}
}
