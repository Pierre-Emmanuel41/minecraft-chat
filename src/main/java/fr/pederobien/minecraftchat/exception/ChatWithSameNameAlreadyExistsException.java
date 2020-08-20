package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class ChatWithSameNameAlreadyExistsException extends ChatAlreadyExistsException {
	private static final long serialVersionUID = 1L;

	public ChatWithSameNameAlreadyExistsException(IChatConfiguration chatConfiguration, IChat alreadyExistingChat) {
		super(chatConfiguration, alreadyExistingChat);
	}

	@Override
	protected String getInternalMessage() {
		return "The chat \"" + getAlreadyExistingChat().getName() + "\" already exists in configuration " + getConfiguration().getName();
	}
}
