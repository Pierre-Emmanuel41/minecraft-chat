package fr.pederobien.minecraft.chat.commands;

import java.util.StringJoiner;
import java.util.function.Supplier;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNodeWrapper;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.commands.ListNode;
import fr.pederobien.minecraft.game.commands.ListNode.ListNodeBuilder;

public class ChatConfigListNode extends MinecraftCodeNodeWrapper {

	private ChatConfigListNode(ListNode<IChat> node) {
		super(node);
	}

	/**
	 * Creates a ChatsListNode based on the given chats list in order to display the chats from the list.
	 * 
	 * @param chats The list of chats to display.
	 */
	public static ChatConfigListNode newInstance(Supplier<IChatList> chats) {
		return new ChatChatListNodeBuilder(chats).build();
	}

	private static class ChatChatListNodeBuilder implements ICodeSender {
		private ListNodeBuilder<IChat> builder;

		/**
		 * Creates a ChatsListNode based on the given chats list in order to display the chats from the list.
		 * 
		 * @param chats The list of chats to display.
		 */
		public ChatChatListNodeBuilder(Supplier<IChatList> chats) {
			builder = ListNode.builder(() -> chats == null ? null : chats.get().toList());
			builder.onNoElement(sender -> sendSuccessful(sender, EChatCode.CHAT_CONFIG__LIST__NO_CHAT, chats.get().getName()));
			builder.onOneElement((sender, chat) -> sendSuccessful(sender, EChatCode.CHAT_CONFIG__LIST__ONE_CHAT, chats.get().getName(), chat));
			builder.onSeveralElements((sender, chatsList) -> {
				StringJoiner joiner = new StringJoiner("\n");
				for (IChat chat : chatsList)
					joiner.add("" + chat);
				sendSuccessful(sender, EChatCode.CHAT_CONFIG__LIST__SEVERAL_CHATS, chats.get().getName(), joiner.toString());
			});
		}

		/**
		 * @return Creates a new node to display each chat from the current chats list.
		 */
		public ChatConfigListNode build() {
			return new ChatConfigListNode(builder.build(EChatCode.CHAT_CONFIG__LIST__EXPLANATION));
		}
	}
}
