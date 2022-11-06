package fr.pederobien.minecraft.chat.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.exception.PlayerNotOperatorException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.managers.PlayerManager;

public class ChatConfigAddPlayerNode extends ChatConfigNode {

	/**
	 * Creates a node in order to add a chat to add players to a chat.
	 * 
	 * @param chats    The list of chats associated to this node.
	 * @param chatTree The command tree in order create or modify a chat.
	 */
	protected ChatConfigAddPlayerNode(Supplier<IChatConfig> chats) {
		super(chats, "player", EChatCode.CHAT_CONFIG__ADD__PLAYER__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			Predicate<IChat> filter0 = chat -> !chat.equals(getConfig().getOperatorChat()) && !chat.equals(getConfig().getGlobalChat());
			return filter(getChats().stream().filter(filter0).map(chat -> chat.getName()), args);
		default:
			Optional<IChat> optChat = getChats().get(args[0]);
			if (!optChat.isPresent() || optChat.get().equals(getConfig().getOperatorChat()) || optChat.get().equals(getConfig().getGlobalChat()))
				return emptyList();

			String[] players = extract(args, 1);
			Predicate<String> filter1 = player -> !asList(players).contains(player) && !optChat.get().getPlayers().getPlayer(player).isPresent();
			return filter(PlayerGroup.ALL.toStream().map(player -> player.getName()).filter(filter1), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__CHAT_NAME_IS_MISSING).build());
			return false;
		}

		Optional<IChat> optChat = getChats().get(name);
		if (!optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__CHAT_NOT_FOUND, name));
			return false;
		}

		if (optChat.get().equals(getConfig().getGlobalChat()) || optChat.get().equals(getConfig().getOperatorChat())) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__PLAYERS_CANNOT_BE_ADDED, optChat.get().getName()));
			return false;
		}

		String chatColoredName = optChat.get().getColoredName();

		List<Player> players = new ArrayList<Player>();
		String[] names = extract(args, 1);
		for (String playerName : names) {
			Player player = PlayerManager.getPlayer(playerName);

			// Checking if the player name refers to an existing player.
			if (player == null) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__PLAYER_NOT_FOUND, playerName, chatColoredName));
				return false;
			}

			// Checking if the player is registered in the current chat.
			if (optChat.get().getPlayers().toList().contains(player)) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__PLAYER_ALREADY_REGISTERED, playerName, chatColoredName));
				return false;
			}

			players.add(player);
		}

		String playerNames = concat(names);

		try {
			for (Player player : players)
				optChat.get().getPlayers().add(player);
		} catch (PlayerNotOperatorException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__PLAYER_NOT_OPERATOR, e.getPlayer().getName(), optChat.get().getName()));
			return false;
		}

		switch (players.size()) {
		case 0:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__NO_PLAYER_ADDED, chatColoredName);
			return true;
		case 1:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__ONE_PLAYER_ADDED, playerNames, chatColoredName);
			return true;
		default:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__ADD__PLAYER__SEVERAL_PLAYERS_ADDED, playerNames, chatColoredName);
			return true;
		}
	}
}
