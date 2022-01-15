package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.platform.impl.PlatformException;

public abstract class ChatException extends PlatformException {
	private static final long serialVersionUID = 1L;
	private IChat chat;

	public ChatException(String message, IChat chat) {
		super(message);
		this.chat = chat;
	}

	/**
	 * @return The chat involved in this exception
	 */
	public IChat getChat() {
		return chat;
	}
}
