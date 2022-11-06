package fr.pederobien.minecraft.chat.event;

import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;

public class ChatPlayerListEvent extends ProjectChatEvent {
	private IChatPlayerList list;

	/**
	 * Creates a players list event associated to a chat.
	 * 
	 * @param list The list source involved in this event.
	 */
	public ChatPlayerListEvent(IChatPlayerList list) {
		this.list = list;
	}

	/**
	 * @return The list involved in this event.
	 */
	public IChatPlayerList getList() {
		return list;
	}
}
