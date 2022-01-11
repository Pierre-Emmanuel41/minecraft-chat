package fr.pederobien.minecraft.chat.commands.chatconfig;

import fr.pederobien.minecraft.chat.commands.chatconfig.add.AddFactory;
import fr.pederobien.minecraft.chat.commands.chatconfig.add.EChatConfigAddMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;

public class CommonAdd<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonAdd() {
		super(EChatConfigLabel.ADD, EChatConfigAddMessageCode.CHAT_ADD__EXPLANATION);
		addEdition(AddFactory.addChat());
		addEdition(AddFactory.addPlayer());
	}
}
