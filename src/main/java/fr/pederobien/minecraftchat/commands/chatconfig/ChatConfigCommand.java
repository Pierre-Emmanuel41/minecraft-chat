package fr.pederobien.minecraftchat.commands.chatconfig;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.AbstractParentCommand;

public class ChatConfigCommand extends AbstractParentCommand<IChatConfiguration> {

	public ChatConfigCommand(JavaPlugin plugin) {
		super(plugin, new ChatConfigParent(plugin));
	}

}
