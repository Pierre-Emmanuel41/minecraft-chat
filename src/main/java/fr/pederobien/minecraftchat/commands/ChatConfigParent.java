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
		addEdition(ChatConfigEditionFactory.commonAdd());
		addEdition(ChatConfigEditionFactory.synchronizedChatConfig());
		addEdition(ChatConfigEditionFactory.commonRemove());
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getNewEdition() {
		return ChatConfigEditionFactory.newChatConfig().setModifiable(false);
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getRenameEdition() {
		return ChatConfigEditionFactory.renameChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getSaveEdition() {
		return ChatConfigEditionFactory.saveChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getListEdition() {
		return ChatConfigEditionFactory.listChatConfig().setModifiable(false);
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getDeleteEdition() {
		return ChatConfigEditionFactory.deleteChatConfig().setModifiable(false);
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getDetailsEdition() {
		return ChatConfigEditionFactory.detailsChatConfig();
	}

	@Override
	protected IMapPersistenceEdition<IChatConfiguration> getLoadEdition() {
		return ChatConfigEditionFactory.loadChatConfig().setModifiable(false);
	}
}
