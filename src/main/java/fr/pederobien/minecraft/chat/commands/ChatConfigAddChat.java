package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.impl.Chat;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.EColor;

public class ChatConfigAddChat extends ChatConfigNode {

	protected ChatConfigAddChat(Supplier<IChatConfig> config) {
		super(config, "chat", EChatCode.CHAT_CONFIG__ADD__CHAT__EXPLANATION, c -> c != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		case 2:
			Predicate<String> isNameValid = name -> !getChats().get(name).isPresent();
			List<String> colors = emptyList();
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
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__CHAT__CHAT_NAME_IS_MISSING).build());
			return false;
		}

		if (getChats().get(name).isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__CHAT__CHAT_ALREADY_EXISTS, name, getChats().getName()));
			return false;
		}

		EColor color;
		try {
			color = EColor.getByColorName(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__CHAT__COLOR_NAME_IS_MISSING, name, getChats().getName()));
			return false;
		}

		if (color == null) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__CHAT__COLOR_NOT_FOUND, name, getChats().getName(), args[1]));
			return false;
		}

		Optional<IChat> optChat = getChats().get(color);
		if (optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__ADD__CHAT__COLOR_ALREADY_USED, name, getChats().getName(), optChat.get().getColoredName()));
			return false;
		}

		IChat chat = new Chat(name);
		chat.setColor(color);

		getChats().add(chat);
		sendSuccessful(sender, EChatCode.CHAT_CONFIG__ADD__CHAT__CHAT_ADDED, chat.getColoredName(EColor.GOLD), getChats().getName());
		return true;
	}
}
