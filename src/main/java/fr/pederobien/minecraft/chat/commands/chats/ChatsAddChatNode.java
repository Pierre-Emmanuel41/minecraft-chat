package fr.pederobien.minecraft.chat.commands.chats;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.chat.ChatCommandTree;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.managers.EColor;

public class ChatsAddChatNode extends ChatsNode {
	private ChatCommandTree chatTree;

	/**
	 * Creates a node in order to add a chat to a list of chats.
	 * 
	 * @param chats    The list of chats associated to this node.
	 * @param chatTree The command tree in order create or modify a chat.
	 */
	protected ChatsAddChatNode(Supplier<IChatList> chats, ChatCommandTree chatTree) {
		super(chats, "chat", EChatCode.CHATS__ADD_CHAT__EXPLANATION, c -> c != null);
		this.chatTree = chatTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		updateExceptedList();
		return chatTree.getNewNode().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		updateExceptedList();
		boolean result = chatTree.getNewNode().onCommand(sender, command, label, args);
		if (result) {
			IChat chat = chatTree.getChat();
			getChats().add(chat);
			sendSuccessful(sender, EChatCode.CHATS__ADD_CHAT__CHAT_ADDED, chat.getColoredName(EColor.GOLD), getChats().getName());
		}
		return result;
	}

	private void updateExceptedList() {
		chatTree.getNewNode().getExceptedNames().clear();
		chatTree.getNewNode().getExceptedColors().clear();
		getChats().toList().forEach(chat -> {
			chatTree.getNewNode().getExceptedNames().add(chat.getName());
			chatTree.getNewNode().getExceptedColors().add(chat.getColor());
		});
	}
}
