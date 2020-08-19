package fr.pederobien.minecraftchat;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.minecraftgameplateform.utils.Plateform;

public class ChatPlugin extends JavaPlugin {
	public static final String NAME = "minecraft-chat";

	@Override
	public void onEnable() {
		Plateform.getPluginHelper().register(this);
		registerDictionaries();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	private void registerDictionaries() {
		// Registering French dictionaries
		registerDictionary("French", "Chat.xml");

		// Registering English dictionaries
		registerDictionary("English", "Chat.xml");
	}

	private void registerDictionary(String parent, String... dictionaryNames) {
		Path jarPath = Plateform.ROOT.getParent().resolve(NAME.concat(".jar"));
		String dictionariesFolder = "resources/dictionaries/".concat(parent).concat("/");
		for (String name : dictionaryNames)
			registerDictionary(Plateform.getDefaultDictionaryParser(dictionariesFolder.concat(name)), jarPath);
	}

	private void registerDictionary(IDictionaryParser parser, Path jarPath) {
		try {
			Plateform.getNotificationCenter().getDictionaryContext().register(parser, jarPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
