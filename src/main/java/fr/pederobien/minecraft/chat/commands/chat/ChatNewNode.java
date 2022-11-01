package fr.pederobien.minecraft.chat.commands.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.impl.Chat;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.EColor;

public class ChatNewNode extends ChatNode {
	private ChatCommandTree chatTree;
	private List<String> exceptedNames;
	private List<EColor> exceptedColors;

	/**
	 * Creates a node to create a new chat.
	 * 
	 * @param chatTree The chat tree associated to this node.
	 */
	protected ChatNewNode(ChatCommandTree chatTree) {
		super(() -> chatTree.getChat(), "new", EChatCode.CHAT__NEW__EXPLANATION, c -> true);
		this.chatTree = chatTree;

		exceptedNames = new ArrayList<String>();
		exceptedColors = new ArrayList<EColor>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		case 2:
			if (exceptedNames.contains(args[0]))
				return emptyList();

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
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT__NEW__NAME_IS_MISSING).build());
			return false;
		}

		if (exceptedNames.contains(name)) {
			send(eventBuilder(sender, EChatCode.CHAT__NEW__NAME_ALREADY_USED, name));
			return false;
		}

		EColor color;
		try {
			color = EColor.getByColorName(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT__NEW__COLOR_IS_MISSING, name));
			return false;
		}

		if (color == null) {
			send(eventBuilder(sender, EChatCode.CHAT__NEW__COLOR_NOT_FOUND, name, args[1]));
			return false;
		}

		if (exceptedColors.contains(color)) {
			send(eventBuilder(sender, EChatCode.CHAT__NEW__COLOR_ALREADY_USED, name, color.getColoredColorName()));
			return false;
		}

		IChat chat = new Chat(name);
		chat.setColor(color);
		chatTree.setChat(chat);
		sendSuccessful(sender, EChatCode.CHAT__NEW__CHAT_CREATED, chatTree.getChat().getColoredName(EColor.GOLD));
		return true;
	}

	/**
	 * @return The list of names that should not be used for a team.
	 */
	public List<String> getExceptedNames() {
		return exceptedNames;
	}

	/**
	 * @return The list of colors that should no be used for a team.
	 */
	public List<EColor> getExceptedColors() {
		return exceptedColors;
	}
}
