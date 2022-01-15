package fr.pederobien.minecraft.chat.event;

import fr.pederobien.minecraft.chat.interfaces.IChat;

public class ChatEvent extends ProjectChatEvent {
	private IChat chat;

	/**
	 * Creates a chat event.
	 * 
	 * @param chat The chat source involved in this event.
	 */
	public ChatEvent(IChat chat) {
		this.chat = chat;
	}

	/**
	 * @return The chat involved in this event.
	 */
	public IChat getChat() {
		return chat;
	}
}
