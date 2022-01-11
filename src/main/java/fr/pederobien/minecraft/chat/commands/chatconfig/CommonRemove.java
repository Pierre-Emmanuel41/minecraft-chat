package fr.pederobien.minecraft.chat.commands.chatconfig;

import fr.pederobien.minecraft.chat.commands.chatconfig.remove.EChatConfigRemoveMessageCode;
import fr.pederobien.minecraft.chat.commands.chatconfig.remove.RemoveFactory;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;

public class CommonRemove<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected CommonRemove() {
		super(EChatConfigLabel.REMOVE, EChatConfigRemoveMessageCode.CHAT_REMOVE__EXPLANATION);
		addEdition(RemoveFactory.removeChat());
		addEdition(RemoveFactory.removePlayer());
	}
}
