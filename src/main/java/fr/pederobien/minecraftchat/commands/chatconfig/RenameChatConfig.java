package fr.pederobien.minecraftchat.commands.chatconfig;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonRename;

public class RenameChatConfig extends CommonRename<IChatConfiguration> {

	protected RenameChatConfig() {
		super(EChatMessageCode.RENAME_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onNameAlreadyTaken(CommandSender sender, String currentName, String newName) {
		sendSynchro(sender, EChatMessageCode.RENAME_CHAT_CONFIG__NAME_ALREADY_TAKEN, currentName, newName);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender, String oldName) {
		sendSynchro(sender, EChatMessageCode.RENAME_CHAT_CONFIG__NAME_IS_MISSING, oldName);
	}

	@Override
	protected void onRenamed(CommandSender sender, String oldName, String newName) {
		sendSynchro(sender, EChatMessageCode.RENAME_CHAT_CONFIG__CONFIGURATION_RENAMED, oldName, newName);
	}
}
