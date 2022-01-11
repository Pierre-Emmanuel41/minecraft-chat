package fr.pederobien.minecraft.chat.exception;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChat;

public class PlayerAlreadyRegisteredInChatException extends ChatException {
	private static final long serialVersionUID = 1L;
	private Player player;

	public PlayerAlreadyRegisteredInChatException(IChat chat, Player player) {
		super(chat);
		this.player = player;
	}

	@Override
	protected String getInternalMessage() {
		return "The player " + getPlayer().getName() + " is already registered in chat " + getChat().getName();
	}

	/**
	 * @return The player already registered in a chat.
	 */
	public Player getPlayer() {
		return player;
	}
}
