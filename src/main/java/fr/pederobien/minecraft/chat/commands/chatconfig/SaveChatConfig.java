package fr.pederobien.minecraft.chat.commands.chatconfig;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.EChatMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonSave;

public class SaveChatConfig extends CommonSave<IChatConfiguration> {

	protected SaveChatConfig() {
		super(EChatMessageCode.SAVE_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onSave(CommandSender sender, String name) {
		sendSynchro(sender, EChatMessageCode.SAVE_CHAT_CONFIG__CONFIGURATION_SAVED, name);
	}
}
