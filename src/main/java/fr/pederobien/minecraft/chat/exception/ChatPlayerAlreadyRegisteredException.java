package fr.pederobien.minecraft.chat.exception;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;

public class ChatPlayerAlreadyRegisteredException extends ChatPlayerListException {
	private static final long serialVersionUID = 1L;
	private Player player;

	public ChatPlayerAlreadyRegisteredException(IChatPlayerList list, Player player) {
		super(String.format("A player %s is already registered", player.getName()), list);
		this.player = player;
	}

	/**
	 * @return The registered player.
	 */
	public Player getPlayer() {
		return player;
	}
}
