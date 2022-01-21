package fr.pederobien.minecraft.chat.exception;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChat;

public class PlayerNotOperatorException extends ChatException {
	private static final long serialVersionUID = 1L;
	private Player player;

	public PlayerNotOperatorException(IChat chat, Player player) {
		super(String.format("Cannot add %s to %s, this player is not an operator", player.getName(), chat.getName()), chat);
		this.player = player;
	}

	/**
	 * @return The player add.
	 */
	public Player getPlayer() {
		return player;
	}
}
