package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public abstract class ChatAlreadyExistsException extends ChatConfigurationException {
	private static final long serialVersionUID = 1L;
	private IChat alreadyExistingChat;

	protected ChatAlreadyExistsException(IChatConfiguration chatConfiguration, IChat alreadyExistingChat) {
		super(chatConfiguration);
		this.alreadyExistingChat = alreadyExistingChat;
	}

	/**
	 * @return The already existing chat for this configuration.
	 */
	public IChat getAlreadyExistingChat() {
		return alreadyExistingChat;
	}
}
