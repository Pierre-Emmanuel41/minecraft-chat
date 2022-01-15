package fr.pederobien.minecraft.chat.event;

import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatListEvent extends ProjectChatEvent {
	private IChatList list;

	/**
	 * Creates a chat list event.
	 * 
	 * @param list The list source involved in this event.
	 */
	public ChatListEvent(IChatList list) {
		this.list = list;
	}

	/**
	 * @return The list involved in this event.
	 */
	public IChatList getList() {
		return list;
	}
}
