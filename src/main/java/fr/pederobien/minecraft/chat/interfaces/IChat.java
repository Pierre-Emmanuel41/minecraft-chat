package fr.pederobien.minecraft.chat.interfaces;

import java.util.List;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.exception.PlayerAlreadyRegisteredInChatException;
import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IPlateformCodeSender;
import fr.pederobien.minecraftgameplateform.interfaces.element.INominable;
import fr.pederobien.minecraftgameplateform.interfaces.observer.IObsPlayerQuitOrJoinEventListener;
import fr.pederobien.minecraftgameplateform.interfaces.observer.IObsTeam;
import fr.pederobien.minecraftmanagers.EColor;

public interface IChat extends INominable, IObsTeam, IObsPlayerQuitOrJoinEventListener, IPlateformCodeSender {

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
	 * Remove all registered players.
	 */
	void clear();

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

	/**
	 * For each player in this team, send the message associated to the given code.
	 * 
	 * @param sender The player who send the message to the team.
	 * @param code   Used as key to get the right message in the right dictionary.
	 * @param args   Some arguments (optional) used for dynamic messages.
	 * 
	 * @throws PlayerNotRegisteredInChatException If the sender is not registered in this chat.
	 */
	void sendMessage(Player sender, IMinecraftMessageCode code, Object... args);
}
