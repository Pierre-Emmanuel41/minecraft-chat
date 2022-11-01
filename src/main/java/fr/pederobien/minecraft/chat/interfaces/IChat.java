package fr.pederobien.minecraft.chat.interfaces;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.platform.interfaces.INominable;

public interface IChat extends INominable {

	/**
	 * @return The name of this chat using {@link EColor#getInColor(String)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName();

	/**
	 * Get the name of this chat, in the chat color and specify which color to use after.
	 * 
	 * @param next The color after to used after.
	 * 
	 * @return The name of this chat using {@link EColor#getInColor(String, EColor)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName(EColor next);

	/**
	 * @return The chat color.
	 */
	EColor getColor();

	/**
	 * Set the chat color.
	 * 
	 * @param color The new chat color.
	 */
	void setColor(EColor color);

	/**
	 * @return The list of players associated to this chat.
	 */
	IPlayerList getPlayers();

	/**
	 * Send the given message to each player registered in this room.
	 * 
	 * @param sender  The player who send the message.
	 * @param message The message to send to the chat.
	 * 
	 * @throws PlayerNotRegisteredInChatException If the sender is not registered in this chat.
	 */
	void sendMessage(CommandSender sender, String message);

	/**
	 * For each player in this team, send the message associated to the given code.
	 * 
	 * @param sender The player who send the message to the team.
	 * @param code   Used as key to get the right message in the right dictionary.
	 * @param args   Some arguments (optional) used for dynamic messages.
	 * 
	 * @throws PlayerNotRegisteredInChatException If the sender is not registered in this chat.
	 */
	void sendMessage(CommandSender sender, IMinecraftCode code, Object... args);
}
