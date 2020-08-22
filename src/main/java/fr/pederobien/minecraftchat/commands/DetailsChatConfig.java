package fr.pederobien.minecraftchat.commands;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonDetails;

public class DetailsChatConfig extends CommonDetails<IChatConfiguration> {

	protected DetailsChatConfig() {
		super(EChatMessageCode.DETAILS_CHAT_CONFIG__EXPLANATION, EChatMessageCode.DETAILS_CHAT_CONFIG__ON_DETAILS);
	}

}
