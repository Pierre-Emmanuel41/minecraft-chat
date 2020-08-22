package fr.pederobien.minecraftchat.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonLoad;
import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public class LoadChatConfig extends CommonLoad<IChatConfiguration> {

	protected LoadChatConfig() {
		super(EChatMessageCode.LOAD_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onStyleLoaded(CommandSender sender, String name) {
		sendMessageToSender(sender, EChatMessageCode.LOAD_CHAT_CONFIG__CONFIGURATION_LOADED, name);
		setAllAvailable();
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendMessageToSender(sender, EChatMessageCode.LOAD_CHAT_CONFIG__NAME_IS_MISSING);
	}

	private void setAllAvailable() {
		for (ILabel label : ECommonLabel.values())
			setAvailableLabelEdition(label);
		for (ILabel label : EChatConfigLabel.values())
			setAvailableLabelEdition(label);
	}
}
