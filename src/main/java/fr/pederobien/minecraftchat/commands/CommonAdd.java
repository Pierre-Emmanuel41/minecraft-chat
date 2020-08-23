package fr.pederobien.minecraftchat.commands;

import fr.pederobien.minecraftchat.commands.add.AddFactory;
import fr.pederobien.minecraftchat.commands.add.EChatConfigAddMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class CommonAdd<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonAdd() {
		super(EChatConfigLabel.ADD, EChatConfigAddMessageCode.ADD__EXPLANATION);
		addEdition(AddFactory.addChat());
		addEdition(AddFactory.addPlayer());
	}
}
