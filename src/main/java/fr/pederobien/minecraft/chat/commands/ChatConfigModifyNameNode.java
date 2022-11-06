package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;

public class ChatConfigModifyNameNode extends ChatConfigNode {

	/**
	 * Creates a node in order to modify the name of a chat.
	 * 
	 * @param config The chats list associated to this node.
	 */
	protected ChatConfigModifyNameNode(Supplier<IChatConfig> config) {
		super(config, "name", EChatCode.CHAT_CONFIG__MODIFY__NAME__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			Predicate<IChat> filter = chat -> !chat.equals(getConfig().getOperatorChat()) && !chat.equals(getConfig().getGlobalChat());
			return filter(getChats().stream().filter(filter).map(chat -> chat.getName()), args);
		case 2:
			Predicate<String> isNameValid = name -> getChats().get(name).isPresent();
			return check(args[0], isNameValid, asList(getMessage(sender, EChatCode.CHAT__NAME_COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__NAME__NAME_IS_MISSING).build());
			return false;
		}

		Optional<IChat> optChat = getChats().get(name);
		if (!optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__NAME__CHAT_NOT_FOUND, name));
			return false;
		}

		String newName;
		try {
			newName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__NAME__NEW_NAME_IS_MISSING, name));
			return false;
		}

		if (getChats().get(newName).isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__NAME__NEW_NAME_ALREADY_USED, name, newName));
			return false;
		}

		try {
			optChat.get().setName(newName);
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__MODIFY__NAME__CHAT_RENAMED, name, newName);
		} catch (IllegalStateException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__NAME__NAME_CANNOT_BE_MODIFIED, name));
			return false;
		}
		return true;
	}
}
