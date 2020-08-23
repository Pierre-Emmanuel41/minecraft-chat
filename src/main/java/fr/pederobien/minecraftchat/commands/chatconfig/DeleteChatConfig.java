package fr.pederobien.minecraftchat.commands.chatconfig;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonDelete;

public class DeleteChatConfig extends CommonDelete<IChatConfiguration> {

	protected DeleteChatConfig() {
		super(EChatMessageCode.DELETE_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onDidNotDelete(CommandSender sender, String name) {
		sendMessageToSender(sender, EChatMessageCode.DELETE_CHAT_CONFIG__DID_NOT_DELETE, name);
	}

	@Override
	protected void onDeleted(CommandSender sender, String name) {
		sendMessageToSender(sender, EChatMessageCode.DELETE_CHAT_CONFIG__CONFIGURATION_DELETED, name);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendMessageToSender(sender, EChatMessageCode.DELETE_CHAT_CONFIG__NAME_IS_MISSING);
	}
}
