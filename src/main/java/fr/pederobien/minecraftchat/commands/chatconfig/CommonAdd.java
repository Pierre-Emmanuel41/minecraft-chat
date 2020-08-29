package fr.pederobien.minecraftchat.commands.chatconfig;

import fr.pederobien.minecraftchat.commands.chatconfig.add.AddFactory;
import fr.pederobien.minecraftchat.commands.chatconfig.add.EChatConfigAddMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class CommonAdd<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonAdd() {
		super(EChatConfigLabel.ADD, EChatConfigAddMessageCode.CHAT_ADD__EXPLANATION);
		addEdition(AddFactory.addChat());
		addEdition(AddFactory.addPlayer());
	}
}
