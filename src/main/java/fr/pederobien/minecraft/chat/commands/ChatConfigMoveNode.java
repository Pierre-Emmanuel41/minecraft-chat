package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.game.exceptions.TeamPlayerAlreadyRegisteredException;
import fr.pederobien.minecraft.managers.PlayerManager;

public class ChatConfigMoveNode extends ChatConfigNode {

	/**
	 * Creates a node in order to move a player from a chats list to another one.
	 * 
	 * @param config The chats list associated to this node.
	 */
	protected ChatConfigMoveNode(Supplier<IChatConfig> config) {
		super(config, "move", EChatCode.CHAT_CONFIG__MOVE__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(PlayerGroup.ALL.toStream().map(player -> player.getName()), args);

		default:
			Player player = PlayerManager.getPlayer(args[0]);
			if (player == null)
				return emptyList();

			List<IChat> origins = getChats().getChats(player);

			String[] names = extract(args, 1);
			List<IChat> newChats = emptyList();
			for (String name : names) {
				if (name.equals(""))
					break;

				Optional<IChat> optChat = getChats().get(name);
				if (!optChat.isPresent())
					return emptyList();
				newChats.add(optChat.get());
			}

			Predicate<IChat> filter = chat -> !origins.contains(chat) && !newChats.contains(chat);
			return filter(getChats().stream().filter(filter).map(chat -> chat.getName()), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String playerName;
		try {
			playerName = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MOVE__NAME_IS_MISSING).build());
			return false;
		}

		Player player = PlayerManager.getPlayer(playerName);
		if (player == null) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MOVE__PLAYER_NOT_FOUND, playerName));
			return false;
		}

		IChat[] chats = new IChat[args.length - 1];
		String[] names = extract(args, 1);
		for (int i = 0; i < names.length; i++) {
			Optional<IChat> optChat = getChats().get(names[i]);
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MOVE__CHAT_NOT_FOUND, playerName, names[i]));
				return false;
			}

			chats[i] = optChat.get();
		}

		String chatNames = concat(names);

		List<IChat> removed = null;
		String removedNames = null;

		try {
			removed = getChats().movePlayer(player, chats);
			removedNames = concat(removed, chat -> chat.getName());
		} catch (TeamPlayerAlreadyRegisteredException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MOVE__PLAYER_ALREADY_REGISTERED, e.getPlayer(), e.getList().getName()));
			return false;
		}

		switch (removed.size()) {
		case 0:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__MOVE__PLAYER_NOT_MOVED, playerName);
			return true;
		default:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__MOVE__PLAYER_MOVED, playerName, removedNames, chatNames);
			return true;
		}
	}
}
