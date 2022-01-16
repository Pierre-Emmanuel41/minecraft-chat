package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class ChatMsgNode extends SuperChatListNode {

	/**
	 * Creates a node to send a message in a chat.
	 * 
	 * @param list The list that contains a list of chats associated to this node.
	 */
	public ChatMsgNode(ISuperChatList list) {
		super(list, "chatMsg", EChatCode.CHAT_MSG__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!(sender instanceof Player))
			return emptyList();

		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getList().getChats((Player) sender).stream().map(chat -> chat.getName()), args);
		default:
			return asList(getMessage(sender, EChatCode.CHAT__MESSAGE_COMPLETION));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		Optional<IChat> optChat = getList().getChat(name, (Player) sender);
		if (!optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_MSG__CHAT_NOT_AVAILABLE, name));
			return false;
		}

		String message = concat(extract(args, 1), " ");
		if (message.equals(""))
			return true;

		try {
			optChat.get().sendMessage((Player) sender, false, message);
		} catch (PlayerNotRegisteredInChatException e) {
			send(eventBuilder(sender, EChatCode.CHAT_MSG__PLAYER_NOT_REGISTERED, optChat.get().getName()));
			return false;
		}
		return true;
	}
}
