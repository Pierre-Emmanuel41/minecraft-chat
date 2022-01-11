package fr.pederobien.minecraft.chat.commands.chatconfig;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.AbstractParentCommand;

public class ChatConfigCommand extends AbstractParentCommand<IChatConfiguration> {

	public ChatConfigCommand(JavaPlugin plugin) {
		super(plugin, new ChatConfigParent(plugin));
	}

}
