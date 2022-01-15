package fr.pederobien.minecraft.chat.commands.chats;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.chat.ChatCommandTree;
import fr.pederobien.minecraft.chat.commands.chat.ChatModifyColorNode;
import fr.pederobien.minecraft.chat.commands.chat.ChatModifyNameNode;
import fr.pederobien.minecraft.chat.impl.EChatCode;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatsModifyNode extends ChatsNode {
	private ChatCommandTree chatTree;

	/**
	 * Creates a node that modify characteristics of a chat.
	 * 
	 * @param chats    The list of chats associated to this node.
	 * @param chatTree The command tree in order create or modify a chat.
	 */
	protected ChatsModifyNode(Supplier<IChatList> chats, ChatCommandTree chatTree) {
		super(chats, "modify", EChatCode.CHATS__MODIFY__EXPLANATION, c -> c != null && !c.toList().isEmpty());
		this.chatTree = chatTree;

		add(chatTree.getModifyNode().getNameNode());
		add(chatTree.getModifyNode().getColorNode());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getChats().stream().map(chat -> chat.getName()), args);
		default:
			Optional<IChat> optChat = getChats().getChat(args[0]);
			if (!optChat.isPresent())
				return emptyList();

			updateExceptedList();
			chatTree.setChat(optChat.get());
			return super.onTabComplete(sender, command, alias, extract(args, 1));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<IChat> optChat;
		try {
			optChat = getChats().getChat(args[0]);
			if (!optChat.isPresent()) {
				send(eventBuilder(sender, EChatCode.CHATS__MODIFY__CHAT_NOT_FOUND, args[0]));
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		updateExceptedList();
		chatTree.setChat(optChat.get());
		return super.onCommand(sender, command, label, extract(args, 1));
	}

	/**
	 * @return The node that modifies the name of a chat.
	 */
	public ChatModifyNameNode getNameNode() {
		return chatTree.getModifyNode().getNameNode();
	}

	/**
	 * @return The node that modifies the color of a chat.
	 */
	public ChatModifyColorNode getColorNode() {
		return chatTree.getModifyNode().getColorNode();
	}

	private void updateExceptedList() {
		chatTree.getModifyNode().getNameNode().getExceptedNames().clear();
		chatTree.getModifyNode().getColorNode().getExceptedColors().clear();
		chatTree.getAddPlayerNode().getExceptedPlayers().clear();
		getChats().toList().forEach(chat -> {
			chatTree.getModifyNode().getNameNode().getExceptedNames().add(chat.getName());
			chatTree.getModifyNode().getColorNode().getExceptedColors().add(chat.getColor());
		});
	}
}
