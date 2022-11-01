package fr.pederobien.minecraft.chat;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.chat.commands.ChatNode;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.utils.AsyncConsole;

public class ChatPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/chat/";

	private static Plugin instance;
	private static ChatNode chatNode;

	/**
	 * @return The plugin associated to this chat plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		chatNode = new ChatNode();

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
		PluginCommand chat = getCommand(chatNode.getLabel());
		chat.setTabCompleter(chatNode);
		chat.setExecutor(chatNode);
	}
}
