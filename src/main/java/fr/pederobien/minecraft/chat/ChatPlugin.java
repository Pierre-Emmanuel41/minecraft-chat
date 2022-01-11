package fr.pederobien.minecraft.chat;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.minecraft.chat.commands.chat.ChatCommand;
import fr.pederobien.minecraft.chat.commands.chatconfig.ChatConfigCommand;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.EventListener;
import fr.pederobien.minecraftgameplateform.interfaces.commands.IParentCommand;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.Plateform;

public class ChatPlugin extends JavaPlugin {
	private static Plugin instance;
	private static IParentCommand<IChatConfiguration> chatConfigCommand;

	/**
	 * @return The plugin associated to this chat plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	/**
	 * @return The current chat configuration for this plugin.
	 */
	public static IChatConfiguration getCurrentChatConfiguration() {
		return chatConfigCommand.getParent().get();
	}

	@Override
	public void onEnable() {
		Plateform.getPluginHelper().register(this);
		instance = this;

		chatConfigCommand = new ChatConfigCommand(this);
		new ChatCommand(this, (ChatConfigCommand) chatConfigCommand);

		new PlayerChatEventListener().register(this);
		registerDictionaries();
	}

	private void registerDictionaries() {
		String[] dictionaries = new String[] { "Chat.xml" };
		// Registering French dictionaries
		registerDictionary("French", dictionaries);

		// Registering English dictionaries
		registerDictionary("English", dictionaries);
	}

	private void registerDictionary(String parent, String... dictionaryNames) {
		Path jarPath = Plateform.ROOT.getParent().resolve(getName().concat(".jar"));
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

	private class PlayerChatEventListener extends EventListener {

		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
			if (chatConfigCommand.getParent().get() != null) {
				for (IChat chat : chatConfigCommand.getParent().get().getChats())
					if (chat.getPlayers().contains(event.getPlayer())) {
						event.setFormat("<" + chat.getColor().getInColor("%s") + "> %2$s");
						return;
					}
			}

			if (Plateform.getGameConfigurationContext().getGameConfiguration() != null) {
				for (ITeam team : Plateform.getGameConfigurationContext().getTeams())
					if (team.getPlayers().contains(event.getPlayer()))
						event.setFormat("<" + team.getColor().getInColor("%s") + "> %2$s");
			}
		}
	}
}
