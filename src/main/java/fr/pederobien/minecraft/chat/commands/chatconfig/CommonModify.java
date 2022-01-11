package fr.pederobien.minecraft.chat.commands.chatconfig;

import fr.pederobien.minecraft.chat.commands.chatconfig.modify.EChatConfigModifyMessageCode;
import fr.pederobien.minecraft.chat.commands.chatconfig.modify.ModifyFactory;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;

public class CommonModify<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonModify() {
		super(EChatConfigLabel.MODIFY, EChatConfigModifyMessageCode.CHAT_MODIFY__EXPLANATION);
		addEdition(ModifyFactory.modifyName());
		addEdition(ModifyFactory.modifyColor());
	}
}
