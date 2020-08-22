package fr.pederobien.minecraftchat.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.impl.ChatConfiguration;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonNew;
import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public class NewChatConfig extends CommonNew<IChatConfiguration> {

	protected NewChatConfig() {
		super(EChatMessageCode.NEW_CHAT_CONFIG__EXPLANATION);
	}

	@Override
	protected void onNameAlreadyTaken(CommandSender sender, String name) {
		sendMessageToSender(sender, EChatMessageCode.NEW_CHAT_CONFIG__NAME_ALREADY_TAKEN, name);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendMessageToSender(sender, EChatMessageCode.NEW_CHAT_CONFIG__NAME_IS_MISSING);
	}

	@Override
	protected IChatConfiguration create(String name) {
		return new ChatConfiguration(name);
	}

	@Override
	protected void onCreated(CommandSender sender, String name) {
		sendMessageToSender(sender, EChatMessageCode.NEW_CHAT_CONFIG__CONFIGURATION_CREATED, name);
		setAllAvailable();
	}

	private void setAllAvailable() {
		for (ILabel label : ECommonLabel.values())
			setAvailableLabelEdition(label);
		for (ILabel label : EChatConfigLabel.values())
			setAvailableLabelEdition(label);
	}
}
