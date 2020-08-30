package fr.pederobien.minecraftchat;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.minecraftchat.commands.chat.ChatCommand;
import fr.pederobien.minecraftchat.commands.chatconfig.ChatConfigCommand;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.Plateform;

public class ChatPlugin extends JavaPlugin {
	public static final String NAME = "minecraft-chat";

	@Override
	public void onEnable() {
		Plateform.getPluginHelper().register(this);
		ChatConfigCommand command = new ChatConfigCommand(this);
		new ChatCommand(this, command);

		getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
			public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
				if (command.getParent().get() != null) {
					for (IChat chat : command.getParent().get().getChats())
						if (chat.getPlayers().contains(event.getPlayer())) {
							event.setFormat("<" + chat.getColor().getInColor("%s") + "> %2$s");
							return;
						}
				}

				if (Plateform.getGameConfigurationContext() != null) {
					for (ITeam team : Plateform.getGameConfigurationContext().getTeams())
						if (team.getPlayers().contains(event.getPlayer()))
							event.setFormat("<" + team.getColor().getInColor("%s") + "> %2$s");
				}
			}
		}, this);
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
