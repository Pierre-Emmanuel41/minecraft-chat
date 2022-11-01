package fr.pederobien.minecraft.chat.commands.chats;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.commands.chat.ChatCommandTree;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatsAddPlayerNode extends ChatsNode {
	private ChatCommandTree chatTree;

	/**
	 * Creates a node in order to add a chat to add players to a chat.
	 * 
	 * @param chats    The list of chats associated to this node.
	 * @param chatTree The command tree in order create or modify a chat.
	 */
	protected ChatsAddPlayerNode(Supplier<IChatList> chats, ChatCommandTree chatTree) {
		super(chats, "player", chatTree.getAddPlayerNode().getExplanation(), c -> c != null);
		this.chatTree = chatTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getChats().stream().map(team -> team.getName()), args);
		default:
			Optional<IChat> optChat = getChats().getChat(args[0]);
			if (!optChat.isPresent())
				return emptyList();

			chatTree.setChat(optChat.get());
			return chatTree.getAddPlayerNode().onTabComplete(sender, command, alias, extract(args, 1));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<IChat> optChat;
		try {
			optChat = getChats().getChat(args[0]);
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHAT__ADD_PLAYER__CHAT_NOT_FOUND, args[0]));
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		chatTree.setChat(optChat.get());
		return chatTree.getAddPlayerNode().onCommand(sender, command, label, extract(args, 1));
	}
}
