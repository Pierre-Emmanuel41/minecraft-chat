package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class ChatAlreadyRegisteredException extends ChatConfigurationException {
	private static final long serialVersionUID = 1L;
	private IChat chat;

	public ChatAlreadyRegisteredException(IChatConfiguration configuration, IChat chat) {
		super(configuration);
		this.chat = chat;
	}

	@Override
	protected String getInternalMessage() {
		return "The chat " + getChat().getName() + " is already registered in the configuration " + getConfiguration().getName();
	}

	/**
	 * @return The already registered chat.
	 */
	public IChat getChat() {
		return chat;
	}

}
