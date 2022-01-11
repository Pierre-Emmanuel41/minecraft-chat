package fr.pederobien.minecraft.chat.commands.chatconfig.modify;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.chatconfig.AbstractChatEdition;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftmanagers.EColor;

public class ModifyColor<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected ModifyColor() {
		super(EChatConfigModifyLabel.COLOR, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String chatName = "";
		String colorName = "";
		try {
			chatName = args[0];
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__CHAT_NAME_IS_MISSING);
			return false;
		}

		try {
			colorName = args[1];
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__COLOR_NAME_IS_MISSING, chatName);
			return false;
		}

		Optional<IChat> optChat = get().getChat(chatName);
		if (!optChat.isPresent()) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__CHAT_DOES_NOT_EXIST, chatName);
			return false;
		}

		IChat chat = optChat.get();

		EColor color = EColor.getByColorName(colorName);
		if (color == null) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__COLOR_DOES_NOT_EXIST, chatName, colorName);
			return false;
		}

		for (IChat c : get().getChats())
			if (c.getColor().equals(color)) {
				sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__COLOR_ALREADY_USED, chatName, c.getColor(), c.getColoredName());
				return false;
			}

		chat.setColor(color);
		sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_COLOR__COLOR_UPDATED, chat.getColoredName(), get().getName());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getChatNames(get().getChats(), false).stream(), args);
		case 2:
			return filter(check(args[0], e -> get().getChat(args[0]).isPresent(), getFreeColorNames(false)).stream(), args);
		default:
			return emptyList();
		}
	}
}
