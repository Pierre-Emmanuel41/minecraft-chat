package fr.pederobien.minecraftchat.persistence.loaders;

import fr.pederobien.minecraftchat.impl.ChatConfiguration;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistenceLoader;

public abstract class AbstractChatLoader extends AbstractXmlPersistenceLoader<IChatConfiguration> {

	protected AbstractChatLoader(Double version) {
		super(version);
	}

	@Override
	protected IChatConfiguration create() {
		return new ChatConfiguration("DefaultChatConfiguration");
	}
}
