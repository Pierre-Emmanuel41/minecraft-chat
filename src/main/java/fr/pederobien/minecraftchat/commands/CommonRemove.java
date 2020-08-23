package fr.pederobien.minecraftchat.commands;

import fr.pederobien.minecraftchat.commands.remove.EChatConfigRemoveMessageCode;
import fr.pederobien.minecraftchat.commands.remove.RemoveFactory;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class CommonRemove<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonRemove() {
		super(EChatConfigLabel.REMOVE, EChatConfigRemoveMessageCode.REMOVE__EXPLANATION);
		addEdition(RemoveFactory.removeChat());
		addEdition(RemoveFactory.removePlayer());
	}
}
