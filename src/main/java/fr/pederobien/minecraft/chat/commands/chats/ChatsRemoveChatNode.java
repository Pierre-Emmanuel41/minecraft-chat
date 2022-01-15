package fr.pederobien.minecraft.chat.commands.chats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.managers.EColor;

public class ChatsRemoveChatNode extends ChatsNode {

	/**
	 * Creates a node in order to remove chats.
	 * 
	 * @param chats The list of chats associated to this node.
	 */
	protected ChatsRemoveChatNode(Supplier<IChatList> chats) {
		super(chats, "chat", EChatCode.CHATS__REMOVE_CHAT__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> chatNames = asList(args);
		Stream<String> chats = getChats().stream().map(chat -> chat.getName()).filter(name -> !chatNames.contains(name));

		// Adding all to delete all registered chats.
		if (args.length == 1)
			return filter(Stream.concat(chats, Stream.of("all")), args);

		// If the first argument is all -> any chat is proposed
		// Else propose not already mentioned chats
		return filter(args[0].equals("all") ? emptyStream() : chats, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String first;
		try {
			first = args[0];
		} catch (IndexOutOfBoundsException e) {
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_CHAT__NO_CHAT_REMOVED, getChats().getName());
			return true;
		}

		// Clearing the current configuration from its chats.
		if (first.equals("all")) {
			getChats().clear();
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_CHAT__ALL_CHATS_REMOVED, getChats().getName());
			return true;
		}

		List<IChat> chats = new ArrayList<IChat>();
		for (String name : args) {
			Optional<IChat> optChat = getChats().getChat(name);

			// Checking if the chat name refers to a registered chat.
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHATS__REMOVE_CHAT__CHAT_NOT_FOUND, name, getChats().getName()));
				return false;
			}

			chats.add(optChat.get());
		}

		String chatNames = concat(chats.stream().map(chat -> chat.getColoredName(EColor.GOLD)).collect(Collectors.toList()), ", ");
		for (IChat chat : chats)
			getChats().remove(chat);

		switch (chats.size()) {
		case 0:
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_CHAT__NO_CHAT_REMOVED, getChats().getName());
			break;
		case 1:
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_CHAT__ONE_CHAT_REMOVED, chatNames, getChats().getName());
			break;
		default:
			sendSuccessful(sender, EChatCode.CHATS__REMOVE_CHAT__SEVERAL_CHATS_REMOVED, chatNames, getChats().getName());
			break;
		}

		return true;
	}

}
