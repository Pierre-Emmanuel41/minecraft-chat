package fr.pederobien.minecraft.chat.exception;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChat;

public class PlayerNotRegisteredInChatException extends ChatException {
	private static final long serialVersionUID = 1L;
	private Player player;

	public PlayerNotRegisteredInChatException(IChat chat, Player player) {
		super(chat);
		this.player = player;
	}

	@Override
	protected String getInternalMessage() {
		return "The player " + getPlayer() + " is not registered in chat " + getChat().getName();
	}

	/**
	 * @return The player trying to send a message in a chat in which it is not registered.
	 */
	public Player getPlayer() {
		return player;
	}
}
