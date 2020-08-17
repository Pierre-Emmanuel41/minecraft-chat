package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.exceptions.MinecraftPlateformException;

public class ChatAlreadyRegisteredException extends MinecraftPlateformException {
	private static final long serialVersionUID = 1L;
	private IChatConfiguration configuration;
	private IChat chat;

	public ChatAlreadyRegisteredException(IChatConfiguration configuration, IChat chat) {
		this.configuration = configuration;
		this.chat = chat;
	}

	@Override
	protected String getInternalMessage() {
		return "The chat " + getChat().getName() + " is already registered in the configuration " + getConfiguration().getName();
	}

	/**
	 * @return The configuration in which the chat is already registered.
	 */
	public IChatConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @return The already registered chat.
	 */
	public IChat getChat() {
		return chat;
	}

}
