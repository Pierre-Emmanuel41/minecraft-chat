package fr.pederobien.minecraftchat.commands.chatconfig;

import fr.pederobien.minecraftchat.commands.chatconfig.modify.EChatConfigModifyMessageCode;
import fr.pederobien.minecraftchat.commands.chatconfig.modify.ModifyFactory;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class CommonModify<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonModify() {
		super(EChatConfigLabel.MODIFY, EChatConfigModifyMessageCode.CHAT_MODIFY__EXPLANATION);
		addEdition(ModifyFactory.modifyName());
		addEdition(ModifyFactory.modifyColor());
	}
}
