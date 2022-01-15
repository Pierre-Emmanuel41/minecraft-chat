package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public abstract class ChatAlreadyExistsException extends ChatListException {
	private static final long serialVersionUID = 1L;
	private IChat chat;

	protected ChatAlreadyExistsException(String message, IChatList list, IChat chat) {
		super(message, list);
		this.chat = chat;
	}

	/**
	 * @return The already existing chat for this configuration.
	 */
	public IChat getChat() {
		return chat;
	}
}
