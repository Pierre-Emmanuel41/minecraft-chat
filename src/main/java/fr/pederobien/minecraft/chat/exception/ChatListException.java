package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.platform.impl.PlatformException;

public abstract class ChatListException extends PlatformException {
	private static final long serialVersionUID = 1L;
	private IChatList list;

	protected ChatListException(String message, IChatList list) {
		super(message);
		this.list = list;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public IChatList getList() {
		return list;
	}
}
