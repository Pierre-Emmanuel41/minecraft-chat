package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;
import fr.pederobien.minecraft.platform.impl.PlatformException;

public class SuperChatListException extends PlatformException {
	private static final long serialVersionUID = 1L;
	private ISuperChatList superList;

	public SuperChatListException(String message, ISuperChatList superList) {
		super(message);
		this.superList = superList;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public ISuperChatList getSuperList() {
		return superList;
	}
}
