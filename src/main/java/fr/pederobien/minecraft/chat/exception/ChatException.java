package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraftgameplateform.exceptions.MinecraftPlateformException;

public abstract class ChatException extends MinecraftPlateformException {
	private static final long serialVersionUID = 1L;
	private IChat chat;

	public ChatException(IChat chat) {
		this.chat = chat;
	}

	/**
	 * @return The chat in which an exception occured.
	 */
	public IChat getChat() {
		return chat;
	}
}
