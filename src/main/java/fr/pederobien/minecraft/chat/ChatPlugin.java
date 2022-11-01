package fr.pederobien.minecraft.chat;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.chat.commands.ChatMsgNode;
import fr.pederobien.minecraft.chat.commands.opmsg.OpMsgCommandTree;
import fr.pederobien.minecraft.chat.commands.superchats.SuperChatsCommandTree;
import fr.pederobien.minecraft.chat.impl.PlayerChatEventListener;
import fr.pederobien.minecraft.chat.impl.SuperChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.utils.AsyncConsole;

public class ChatPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/chat/";

	private static Plugin instance;
	private static ISuperChatList list;
	private static ChatMsgNode chatMsgNode;
	private static OpMsgCommandTree opMsgTree;
	private static SuperChatsCommandTree superChatsTree;

	/**
	 * @return The plugin associated to this chat plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	/**
	 * @return The list of list of chats associated to this plugin.
	 */
	public static ISuperChatList getList() {
		return list;
	}

	/**
	 * @return The node to run the "./chat" command.
	 */
	public static ChatMsgNode getChatMsgNode() {
		return chatMsgNode;
	}

	/**
	 * @return The node to run the "./opMsg" command.
	 */
	public static OpMsgCommandTree getOpMsgTree() {
		return opMsgTree;
	}

	/**
	 * Get the tree to modify the characteristics of a list that contains a list of chats.
	 * 
	 * @return The super chats tree associated to this plugin.
	 */
	public static SuperChatsCommandTree getSuperChatsTree() {
		return superChatsTree;
	}

	@Override
	public void onEnable() {
		instance = this;
		list = new SuperChatList("Global");
		chatMsgNode = new ChatMsgNode(list);
		opMsgTree = new OpMsgCommandTree(list);
		superChatsTree = new SuperChatsCommandTree(list);

		new PlayerChatEventListener().register(this);

		registerDictionaries();
		registerTabExecutors();
	}

	private void registerDictionaries() {
		JarXmlDictionaryParser dictionaryParser = new JarXmlDictionaryParser(getFile().toPath());

		MinecraftDictionaryContext context = MinecraftDictionaryContext.instance();
		String[] dictionaries = new String[] { "English.xml", "French.xml" };
		for (String dictionary : dictionaries)
			try {
				context.register(dictionaryParser.parse(DICTIONARY_FOLDER.concat(dictionary)));
			} catch (Exception e) {
				AsyncConsole.print(e);
				for (StackTraceElement element : e.getStackTrace())
					AsyncConsole.print(element);
			}
	}

	private void registerTabExecutors() {
		PluginCommand chatMsg = getCommand(chatMsgNode.getLabel());
		chatMsg.setTabCompleter(chatMsgNode);
		chatMsg.setExecutor(chatMsgNode);

		PluginCommand chatOpMsg = getCommand(opMsgTree.getRoot().getLabel());
		chatOpMsg.setTabCompleter(opMsgTree.getRoot());
		chatOpMsg.setExecutor(opMsgTree.getRoot());

		PluginCommand chatConfig = getCommand(superChatsTree.getRoot().getLabel());
		chatConfig.setTabCompleter(superChatsTree.getRoot());
		chatConfig.setExecutor(superChatsTree.getRoot());
	}
}
