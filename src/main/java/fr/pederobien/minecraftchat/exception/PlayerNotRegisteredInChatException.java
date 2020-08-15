package fr.pederobien.minecraftchat.exception;

import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftgameplateform.exceptions.MinecraftPlateformException;

public class PlayerNotRegisteredInChatException extends MinecraftPlateformException {
	private static final long serialVersionUID = 1L;
	private Player player;
	private IChat chat;

	public PlayerNotRegisteredInChatException(Player player, IChat chat) {
		this.player = player;
		this.chat = chat;
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

	/**
	 * @return The chat involved in this exception.
	 */
	public IChat getChat() {
		return chat;
	}
}
