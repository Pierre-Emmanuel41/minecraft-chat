package fr.pederobien.minecraft.chat.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.managers.EColor;

public class ChatConfigModifyColorNode extends ChatConfigNode {

	/**
	 * Creates a node in order to modify the color of a chat.
	 * 
	 * @param config The chats configuration associated to this node.
	 */
	protected ChatConfigModifyColorNode(Supplier<IChatConfig> config) {
		super(config, "color", EChatCode.CHAT_CONFIG__MODIFY__COLOR__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			Predicate<IChat> filter = chat -> !chat.equals(getConfig().getOperatorChat()) && !chat.equals(getConfig().getGlobalChat());
			return filter(getChats().stream().filter(filter).map(chat -> chat.getName()), args);
		case 2:
			Predicate<String> isNameValid = name -> getChats().get(name).isPresent();
			List<String> colors = new ArrayList<String>();
			for (EColor color : EColor.values())
				if (!getChats().get(color).isPresent())
					colors.add(color.toString());
			return filter(check(args[0], isNameValid, colors.stream()), args);
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
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__NAME_IS_MISSING).build());
			return false;
		}

		Optional<IChat> optChat = getChats().get(name);
		if (!optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__CHAT_NOT_FOUND, name));
			return false;
		}

		String colorName;
		try {
			colorName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__COLOR_IS_MISSING, name));
			return false;
		}

		EColor color = EColor.getByColorName(colorName);
		if (color == null) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__COLOR_NOT_FOUND, name, colorName));
			return false;
		}

		Optional<IChat> optColorChat = getChats().get(color);
		if (optColorChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__COLOR_ALREADY_USED, name, optColorChat.get().getColoredName()));
			return false;
		}

		String oldChatColor = optChat.get().getColoredName(EColor.GOLD);
		try {
			optChat.get().setColor(color);
			sendSuccessful(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__COLOR_UPDATED, oldChatColor, optChat.get().getColoredName());
		} catch (IllegalStateException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__MODIFY__COLOR__COLOR_CANNOT_BE_MODIFIED, name));
			return false;
		}

		return true;
	}
}
