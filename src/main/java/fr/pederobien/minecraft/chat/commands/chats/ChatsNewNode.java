package fr.pederobien.minecraft.chat.commands.chats;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.impl.ChatList;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.game.impl.EGameCode;

public class ChatsNewNode extends ChatsNode {
	private ChatsCommandTree chatsTree;
	private List<String> exceptedNames;

	/**
	 * Creates a node to create a new chats list.
	 * 
	 * @param chatsTree The tree associated to this node.
	 */
	protected ChatsNewNode(ChatsCommandTree chatsTree) {
		super(() -> chatsTree.getChats(), "new", EChatCode.CHATS__NEW__EXPLANATION, c -> true);
		this.chatsTree = chatsTree;

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
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHATS__NEW__NAME_IS_MISSING).build());
			return false;
		}

		if (exceptedNames.contains(name)) {
			send(eventBuilder(sender, EChatCode.CHATS__NEW__NAME_ALREADY_USED, name));
			return false;
		}

		chatsTree.setChats(new ChatList(name));
		sendSuccessful(sender, EChatCode.CHATS__NEW__CHATS_CREATED, name);
		return true;
	}

	/**
	 * @return The list of names that should not be used to create a new chats list.
	 */
	public List<String> getExceptedNames() {
		return exceptedNames;
	}
}
