package fr.pederobien.minecraftchat.commands;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftchat.persistence.ChatPersistence;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractConfigurationParentPersistenceEdition;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class ChatConfigParent extends AbstractConfigurationParentPersistenceEdition<IChatConfiguration> {

	public ChatConfigParent(Plugin plugin) {
		super("chatconfig", EChatMessageCode.CHAT_CONFIG__EXPLANATION, plugin, ChatPersistence.getInstance());
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getNewEdition() {
		return new NewChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getRenameEdition() {
		return new RenameChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getSaveEdition() {
		return new SaveChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getListEdition() {
		return new ListChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getDeleteEdition() {
		return new DeleteChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getDetailsEdition() {
		return new DetailsChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getLoadEdition() {
		return new LoadChatConfig();
	}
}
