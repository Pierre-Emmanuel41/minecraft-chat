package fr.pederobien.minecraft.chat.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatListChatRemovePostEvent extends ChatListEvent {
	private IChat chat;

	/**
	 * Creates an event thrown when a chat is removed from a list of chats.
	 * 
	 * @param list The list from which a chat has been removed.
	 * @param chat The removed chat.
	 */
	public ChatListChatRemovePostEvent(IChatList list, IChat chat) {
		super(list);
		this.chat = chat;
	}

	/**
	 * @return The removed chat.
	 */
	public IChat getChat() {
		return chat;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("list=" + getList().getName());
		joiner.add("chat=" + getChat().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
