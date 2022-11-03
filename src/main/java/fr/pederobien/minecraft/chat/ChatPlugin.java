package fr.pederobien.minecraft.chat;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.chat.commands.ChatCommand;
import fr.pederobien.minecraft.chat.commands.chatConfig.ChatConfigCommandTree;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.utils.AsyncConsole;

public class ChatPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/chat/";

	private static Plugin instance;
	private static ChatCommand chatCommand;
	private static ChatConfigCommandTree chatConfigCommandTree;

	/**
	 * @return The plugin associated to this chat plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	/**
	 * @return The node that send a message to a specific chat.
	 */
	public static ChatCommand getChatNode() {
		return chatCommand;
	}

	/**
	 * @return The command tree to modify a list of chats.
	 */
	public static ChatConfigCommandTree getChatConfigCommandTree() {
		return chatConfigCommandTree;
	}

	@Override
	public void onEnable() {
		instance = this;
		chatCommand = new ChatCommand();
		chatConfigCommandTree = new ChatConfigCommandTree(null);

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
		PluginCommand chat = getCommand(chatCommand.getLabel());
		chat.setTabCompleter(chatCommand);
		chat.setExecutor(chatCommand);

		PluginCommand chatConfig = getCommand(chatConfigCommandTree.getRoot().getLabel());
		chatConfig.setTabCompleter(chatConfigCommandTree.getRoot());
		chatConfig.setExecutor(chatConfigCommandTree.getRoot());
	}
}
