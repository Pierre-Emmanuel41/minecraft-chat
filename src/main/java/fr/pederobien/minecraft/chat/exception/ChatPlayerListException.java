package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;

public class ChatPlayerListException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IChatPlayerList list;

	public ChatPlayerListException(String message, IChatPlayerList list) {
		super(message);
		this.list = list;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public IChatPlayerList getList() {
		return list;
	}
}