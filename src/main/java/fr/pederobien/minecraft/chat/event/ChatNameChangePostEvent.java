package fr.pederobien.minecraft.chat.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.chat.interfaces.IChat;

public class ChatNameChangePostEvent extends ChatEvent {
	private String oldName;

	/**
	 * Creates an event thrown when the name of a chat has changed.
	 * 
	 * @param chat    The chat whose the name has changed.
	 * @param oldName The old chat name.
	 */
	public ChatNameChangePostEvent(IChat chat, String oldName) {
		super(chat);
		this.oldName = oldName;
	}

	/**
	 * @return The old chat name.
	 */
	public String getOldName() {
		return oldName;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("chat=" + getChat().getName());
		joiner.add("oldName=" + getOldName());
		return String.format("%s_%s", getName(), joiner);
	}
}
