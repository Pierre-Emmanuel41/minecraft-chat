package fr.pederobien.minecraft.chat.commands.chatconfig;

import fr.pederobien.minecraft.chat.EChatMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonDetails;

public class DetailsChatConfig extends CommonDetails<IChatConfiguration> {

	protected DetailsChatConfig() {
		super(EChatMessageCode.DETAILS_CHAT_CONFIG__EXPLANATION, EChatMessageCode.DETAILS_CHAT_CONFIG__ON_DETAILS);
	}

}
