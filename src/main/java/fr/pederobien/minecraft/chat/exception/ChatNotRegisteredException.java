package fr.pederobien.minecraft.chat.exception;

import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;

public class ChatNotRegisteredException extends ChatConfigurationException {
	private static final long serialVersionUID = 1L;
	private String name;

	public ChatNotRegisteredException(IChatConfiguration configuration, String name) {
		super(configuration);
		this.name = name;
	}

	@Override
	protected String getInternalMessage() {
		return "The chat " + getName() + " is not registered in the configuration " + getConfiguration().getName();
	}

	/**
	 * @return The name of the not registered chat.
	 */
	public String getName() {
		return name;
	}

}
