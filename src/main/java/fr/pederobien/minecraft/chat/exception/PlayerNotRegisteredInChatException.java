package fr.pederobien.minecraft.chat.exception;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChat;

public class PlayerNotRegisteredInChatException extends ChatException {
	private static final long serialVersionUID = 1L;
	private Player player;

	public PlayerNotRegisteredInChatException(IChat chat, Player player) {
		super(String.format("The player %s is not registered in chat %s", player.getName(), chat.getName()), chat);
		this.player = player;
	}

	/**
	 * @return The sender that is not registered in the chat involved in this event.
	 */
	public Player getPlayer() {
		return player;
	}
}
