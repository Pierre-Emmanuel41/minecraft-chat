package fr.pederobien.minecraft.chat.commands.chatconfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.exception.ChatNotRegisteredException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;
import fr.pederobien.minecraftgameplateform.exceptions.ColorNotFoundException;
import fr.pederobien.minecraftgameplateform.exceptions.PlayerNotFoundException;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractLabelEdition;
import fr.pederobien.minecraftgameplateform.interfaces.element.IGameConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.minecraftmanagers.PlayerManager;

public class AbstractChatEdition<T extends IChatConfiguration> extends AbstractLabelEdition<T> {

	protected AbstractChatEdition(ILabel label, IMinecraftMessageCode explanation) {
		super(label, explanation);
	}

	@Override
	public boolean isAvailable() {
		return get() != null && !get().isSynchronized();
	}

	/**
	 * Get a list of chats associated to each chat name in array <code>chatNames</code>
	 * 
	 * @param chatNames The array that contains chat names.
	 * @return The list of chats.
	 * 
	 * @throws ChatNotRegisteredException If there no chat is registered for one chat name in the given string array.
	 * @see {@link IChatConfiguration#getChat(String)}
	 */
	protected List<IChat> getChats(String[] chatNames) {
		List<IChat> chats = new ArrayList<IChat>();
		for (String chatName : chatNames) {
			Optional<IChat> optChat = get().getChat(chatName);
			if (!optChat.isPresent())
				throw new ChatNotRegisteredException(get(), chatName);
			else
				chats.add(optChat.get());
		}
		return chats;
	}

	/**
	 * Get a list of string that correspond to the name of each chat in the given list <code>chats</code>
	 * 
	 * @param chats    The list of chat used to get their name.
	 * @param coloured True if each chat's name is colored, false otherwise.
	 * @return The list of chat's name.
	 */
	protected List<String> getChatNames(List<IChat> chats, boolean colored) {
		return chats.stream().map(chat -> colored ? chat.getColoredName() : chat.getName()).collect(Collectors.toList());
	}

	/**
	 * Remove teams already mentioned from the list returned by {@link IGameConfiguration#getTeams()}.
	 * 
	 * @param alreadyMentionedTeams A list that contains already mentioned teams.
	 * @return A stream that contains not mentioned teams.
	 */
	protected Stream<IChat> getFreeChats(List<String> alreadyMentionedTeams) {
		return get().getChats().stream().filter(team -> !alreadyMentionedTeams.contains(team.getName()));
	}

	/**
	 * Get a list of string that correspond to the name of each {@link EColor} not used by the registered chats for this
	 * configuration.
	 * 
	 * @param colored True if each color's name is colored, false otherwise.
	 * 
	 * @return The list of free color's name.
	 */
	protected List<String> getFreeColorNames(boolean colored) {
		List<EColor> alreadyUsedColors = new ArrayList<>();
		for (IChat chat : get().getChats())
			alreadyUsedColors.add(chat.getColor());
		return Arrays.asList(EColor.values()).stream().filter(color -> !alreadyUsedColors.contains(color))
				.map(color -> colored ? color.getColoredColorName() : color.toString()).collect(Collectors.toList());
	}

	/**
	 * Remove players already mentioned from the list that contains all players registered in chats.
	 * 
	 * @param alreadyMentionedPlayers A list that contains already mentioned players.
	 * @return A stream that contains not free and not mentioned players.
	 */
	protected Stream<Player> getNotFreePlayers(List<String> alreadyMentionedPlayers) {
		List<Player> registeredPlayers = new ArrayList<Player>();
		for (IChat chat : get().getChats())
			registeredPlayers.addAll(chat.getPlayers());
		return registeredPlayers.stream().filter(player -> !alreadyMentionedPlayers.contains(player.getName()));
	}

	/**
	 * Find the player associated to the given name.
	 * 
	 * @param playerName The player's name.
	 * @return The associated player if it exists.
	 * 
	 * @throws PlayerNotFoundException If the name does not correspond to a player.
	 */
	protected Player getPlayer(String playerName) {
		Player player = PlayerManager.getPlayer(playerName);
		if (player == null)
			throw new PlayerNotFoundException(playerName);
		return player;
	}

	/**
	 * Find the colour associated to the given name.
	 * 
	 * @param colorName The colour's name.
	 * @return The associated colour if it exists.
	 * 
	 * @throws ColorNotFoundException If the name does not correspond to a color.
	 */
	protected EColor getColor(String colorName) {
		EColor color = EColor.getByColorName(colorName);
		if (color == null)
			throw new ColorNotFoundException(colorName);
		return color;
	}

	/**
	 * Get a list of players associated to each player's name in array <code>playerNames</code>
	 * 
	 * @param playerNames The array that contains player names.
	 * @return The list of players.
	 * 
	 * @see #getPlayer(String)
	 */
	protected List<Player> getPlayers(String[] playerNames) {
		List<Player> players = new ArrayList<Player>();
		for (String playerName : playerNames)
			players.add(getPlayer(playerName));
		return players;
	}

	/**
	 * Remove players already mentioned from the stream returned by {@link PlayerManager#getPlayers()}.
	 * 
	 * @param alreadyMentionedPlayers A list that contains already mentioned players.
	 * @return A stream that contains not mentioned players.
	 */
	public Stream<Player> getPlayers(List<String> alreadyMentionedPlayers) {
		return PlayerManager.getPlayers().filter(player -> !alreadyMentionedPlayers.contains(player.getName()));
	}

	/**
	 * Get a list of string that correspond to the name of each player in the given list <code>players</code>
	 * 
	 * @param players The list of player used to get their name.
	 * @return The list of player's name.
	 */
	protected List<String> getPlayerNames(List<Player> players) {
		return players.stream().map(player -> player.getName()).collect(Collectors.toList());
	}
}
