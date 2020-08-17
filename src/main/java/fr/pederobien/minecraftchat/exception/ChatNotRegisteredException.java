package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.exceptions.MinecraftPlateformException;

public class ChatNotRegisteredException extends MinecraftPlateformException {
	private static final long serialVersionUID = 1L;
	private IChatConfiguration configuration;
	private String name;

	public ChatNotRegisteredException(IChatConfiguration configuration, String name) {
		this.configuration = configuration;
		this.name = name;
	}

	@Override
	protected String getInternalMessage() {
		return "The chat " + getName() + " is not registered in the configuration " + getConfiguration().getName();
	}

	/**
	 * @return The configuration in which the chat is not registered.
	 */
	public IChatConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @return The name of the not registered chat.
	 */
	public String getName() {
		return name;
	}

}
