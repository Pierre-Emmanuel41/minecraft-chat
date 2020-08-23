package fr.pederobien.minecraftchat.commands.chatconfig;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonSave;

public class SaveChatConfig extends CommonSave<IChatConfiguration> {

	protected SaveChatConfig() {
		super(EChatMessageCode.SAVE_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onSave(CommandSender sender, String name) {
		sendMessageToSender(sender, EChatMessageCode.SAVE_CHAT_CONFIG__CONFIGURATION_SAVED, name);
	}
}
