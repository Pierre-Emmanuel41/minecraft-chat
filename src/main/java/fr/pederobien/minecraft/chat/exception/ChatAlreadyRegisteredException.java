package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatAlreadyRegisteredException extends ChatAlreadyExistsException {
	private static final long serialVersionUID = 1L;

	public ChatAlreadyRegisteredException(IChatList list, IChat chat) {
		super(String.format("The chat %s already exists in list %s", chat.getName(), list.getName()), list, chat);
	}
}
