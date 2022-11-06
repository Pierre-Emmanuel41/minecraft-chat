package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;

public class ChatConfigRemoveChatNode extends ChatConfigNode {

	/**
	 * Creates a node in order to remove a chat from a chat list.
	 * 
	 * @param config The chat list associated to this node.
	 */
	protected ChatConfigRemoveChatNode(Supplier<IChatConfig> config) {
		super(config, "chat", EChatCode.CHAT_CONFIG__REMOVE__CHAT__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			Predicate<IChat> filter = chat -> !chat.equals(getConfig().getOperatorChat()) && !chat.equals(getConfig().getGlobalChat());
			return filter(getChats().stream().filter(filter).map(chat -> chat.getName()), args);
		default:
			for (String name : args)
				if (!getChats().get(name).isPresent())
					return emptyList();

			Predicate<String> freeChats = name -> !asList(args).contains(name);
			return filter(getChats().stream().map(chat -> chat.getName()).filter(freeChats), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<IChat> chats = emptyList();
		for (String name : args) {
			Optional<IChat> optChat = getChats().get(name);
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__REMOVE__CHAT__CHAT_NOT_FOUND, name, getChats().getName()));
				return false;
			}

			if (optChat.get().equals(getConfig().getGlobalChat()) || optChat.get().equals(getConfig().getOperatorChat())) {
				send(eventBuilder(sender, EChatCode.CHAT_CONFIG__REMOVE__CHAT__CHAT_CANNOT_BE_REMOVED, name));
				return false;
			}

			chats.add(optChat.get());
		}

		String chatNames = concat(args, ", ");
		for (IChat chat : chats)
			getChats().remove(chat);

		switch (chats.size()) {
		case 0:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__CHAT__NO_CHAT_REMOVED, getChats().getName());
			return true;
		case 1:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__CHAT__ONE_CHAT_REMOVED, chatNames, getChats().getName());
			return true;
		default:
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__REMOVE__CHAT__SEVERAL_CHATS_REMOVED, chatNames, getChats().getName());
			return true;
		}
	}
}
