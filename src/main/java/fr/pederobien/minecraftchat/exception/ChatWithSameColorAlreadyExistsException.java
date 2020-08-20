package fr.pederobien.minecraftchat.exception;

import java.util.StringJoiner;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class ChatWithSameColorAlreadyExistsException extends ChatAlreadyExistsException {
	private static final long serialVersionUID = 1L;

	public ChatWithSameColorAlreadyExistsException(IChatConfiguration chatConfiguration, IChat alreadyExistingChat) {
		super(chatConfiguration, alreadyExistingChat);
	}

	@Override
	protected String getInternalMessage() {
		StringJoiner joiner = new StringJoiner(" ");
		joiner.add("The chat \"" + getAlreadyExistingChat().getName() + "\"");
		joiner.add("already has the color \"" + getAlreadyExistingChat().getColor().getName() + "\"");
		joiner.add("in configuration " + getConfiguration().getName());
		return joiner.toString();
	}
}
