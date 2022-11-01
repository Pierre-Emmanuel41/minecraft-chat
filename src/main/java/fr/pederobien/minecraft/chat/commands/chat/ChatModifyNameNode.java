package fr.pederobien.minecraft.chat.commands.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.EColor;

public class ChatModifyNameNode extends ChatNode {
	private List<String> exceptedNames;

	protected ChatModifyNameNode(Supplier<IChat> chat) {
		super(chat, "name", EChatCode.CHAT__MODIFY_NAME__EXPLANATION, c -> c != null);
		exceptedNames = new ArrayList<String>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IChat chat = getChat();

		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT__MODIFY_NAME__NAME_IS_MISSING, chat.getName()));
			return false;
		}

		if (exceptedNames.contains(name)) {
			send(eventBuilder(sender, EChatCode.CHAT__MODIFY_NAME__NAME_ALREADY_USED, chat.getColoredName(), chat.getColor().getInColor(name)));
			return false;
		}

		String oldName = chat.getColoredName(EColor.GOLD);
		chat.setName(name);
		sendSuccessful(sender, EChatCode.CHAT__MODIFY_NAME__CHAT_NAME_UPDATED, oldName, chat.getColoredName(EColor.GOLD));
		return true;
	}

	/**
	 * @return The list of names that should not be used for a chat.
	 */
	public List<String> getExceptedNames() {
		return exceptedNames;
	}
}
