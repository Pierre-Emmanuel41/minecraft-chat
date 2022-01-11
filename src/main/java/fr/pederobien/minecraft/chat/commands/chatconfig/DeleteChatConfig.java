package fr.pederobien.minecraft.chat.commands.chatconfig;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.EChatMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonDelete;

public class DeleteChatConfig extends CommonDelete<IChatConfiguration> {

	protected DeleteChatConfig() {
		super(EChatMessageCode.DELETE_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onDidNotDelete(CommandSender sender, String name) {
		sendSynchro(sender, EChatMessageCode.DELETE_CHAT_CONFIG__DID_NOT_DELETE, name);
	}

	@Override
	protected void onDeleted(CommandSender sender, String name) {
		sendSynchro(sender, EChatMessageCode.DELETE_CHAT_CONFIG__CONFIGURATION_DELETED, name);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendSynchro(sender, EChatMessageCode.DELETE_CHAT_CONFIG__NAME_IS_MISSING);
	}
}
