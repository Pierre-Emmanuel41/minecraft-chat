package fr.pederobien.minecraftchat.exception;

import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;

public class ChatNameForbiddenException extends ChatConfigurationException {
	private static final long serialVersionUID = 1L;
	private String forbiddenName;

	public ChatNameForbiddenException(IChatConfiguration chatConfiguration, String fobiddenName) {
		super(chatConfiguration);
		this.forbiddenName = fobiddenName;
	}

	@Override
	protected String getInternalMessage() {
		return "The name \"" + getForbiddenName() + "\" is forbidden";
	}

	/**
	 * @return The name that is forbidden.
	 */
	public String getForbiddenName() {
		return forbiddenName;
	}
}
