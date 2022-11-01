package fr.pederobien.minecraft.chat.commands.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.managers.EColor;

public class ChatModifyColorNode extends ChatNode {
	private List<EColor> exceptedColors;

	/**
	 * Creates a node to modify the color of the current chat.
	 * 
	 * @param chat The chat associated to this node.
	 */
	protected ChatModifyColorNode(Supplier<IChat> chat) {
		super(chat, "color", EChatCode.CHAT__MODIFY_COLOR__EXPLANATION, c -> c != null);
		exceptedColors = new ArrayList<EColor>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<String> colors = new ArrayList<String>();
			for (EColor color : EColor.values())
				if (!exceptedColors.contains(color))
					colors.add(color.toString());
			return filter(colors.stream(), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		IChat chat = getChat();
		EColor color;
		try {
			color = EColor.getByColorName(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT__MODIFY_COLOR__COLOR_IS_MISSING, chat.getColoredName()));
			return false;
		}

		if (color == null) {
			send(eventBuilder(sender, EChatCode.CHAT__MODIFY_COLOR__COLOR_NOT_FOUND, chat.getColoredName(), args[0]));
			return false;
		}

		if (exceptedColors.contains(color)) {
			send(eventBuilder(sender, EChatCode.CHAT__MODIFY_COLOR__COLOR_IS_ALREADY_USED, chat.getColoredName(), color.getColoredColorName()));
			return false;
		}

		String oldTeamColor = chat.getColoredName(EColor.GOLD);
		chat.setColor(color);
		sendSuccessful(sender, EChatCode.CHAT__MODIFY_COLOR__CHAT_COLOR_UPDATED, oldTeamColor, chat.getColoredName());
		return true;
	}

	/**
	 * @return The list of colors that should not be used for a chat.
	 */
	public List<EColor> getExceptedColors() {
		return exceptedColors;
	}
}
