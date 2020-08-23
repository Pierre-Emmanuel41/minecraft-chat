package fr.pederobien.minecraftchat.interfaces;

import java.util.List;

import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.exception.PlayerAlreadyRegisteredInChatException;
import fr.pederobien.minecraftchat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraftgameplateform.interfaces.element.INominable;
import fr.pederobien.minecraftgameplateform.utils.EColor;

public interface IChat extends INominable {

	/**
	 * @return The name of this chat using {@link EColor#getInColor(String)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName();

	/**
	 * Set the name of this chat.
	 * 
	 * @param name The new name of the chat.
	 */
	void setName(String name);

	/**
	 * Appends the given player to this room.
	 * 
	 * @param player The player to append.
	 * 
	 * @throws PlayerAlreadyRegisteredInChatException If the given player is already registered in this chat.
	 */
	void add(Player player);

	/**
	 * Removes the given player from this room.
	 * 
	 * @param player The player to remove.
	 */
	void remove(Player player);

	/**
	 * @return The list of registered players in this room. This list is unmodifiable.
	 */
	List<Player> getPlayers();

	/**
	 * @return The color of this chat.
	 */
	EColor getColor();

	/**
	 * Set the color of this chat.
	 * 
	 * @param color The new color of the chat.
	 */
	void setColor(EColor color);

	/**
	 * Send the given message to each player registered in this room.
	 * 
	 * @param sender The player who send the message.
	 * 
	 * @throws PlayerNotRegisteredInChatException If the sender is not registered in this chat.
	 */
	void sendMessage(Player sender, String message);
}
