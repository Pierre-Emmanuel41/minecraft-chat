package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class ChatListAlreadyRegisteredException extends SuperChatListException {
	private static final long serialVersionUID = 1L;
	private IChatList list;

	public ChatListAlreadyRegisteredException(ISuperChatList superList, IChatList list) {
		super(String.format("The list %s is already registered in list %s", list.getName(), superList.getName()), superList);
		this.list = list;
	}

	/**
	 * @return The already registered chat.
	 */
	public IChatList getList() {
		return list;
	}
}
