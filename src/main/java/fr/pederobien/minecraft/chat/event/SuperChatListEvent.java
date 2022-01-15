package fr.pederobien.minecraft.chat.event;

import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class SuperChatListEvent extends ProjectChatEvent {
	private ISuperChatList superList;

	/**
	 * Creates a super chats list event.
	 * 
	 * @param list The list source involved in this event.
	 */
	public SuperChatListEvent(ISuperChatList superList) {
		this.superList = superList;
	}

	/**
	 * @return The list involved in this event.
	 */
	public ISuperChatList getSuperList() {
		return superList;
	}
}
