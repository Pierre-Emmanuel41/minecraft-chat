package fr.pederobien.minecraft.chat.commands.chatconfig;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.EChatMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonList;

public class ListChatConfig extends CommonList<IChatConfiguration> {

	protected ListChatConfig() {
		super(EChatMessageCode.LIST_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onNoElement(CommandSender sender) {
		sendSynchro(sender, EChatMessageCode.LIST_CHAT_CONFIG__NO_REGISTERED_CONFIGURATION);
	}

	@Override
	protected void onOneElement(CommandSender sender, String name) {
		sendSynchro(sender, EChatMessageCode.LIST_CHAT_CONFIG__ONE_REGISTERED_CONFIGURATION, name);
	}

	@Override
	protected void onSeveralElement(CommandSender sender, String names) {
		sendSynchro(sender, EChatMessageCode.LIST_CHAT_CONFIG__SEVERAL_ELEMENTS, names);
	}
}
