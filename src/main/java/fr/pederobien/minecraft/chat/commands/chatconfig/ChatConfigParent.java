package fr.pederobien.minecraft.chat.commands.chatconfig;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.chat.EChatMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraft.chat.persistence.ChatPersistence;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractConfigurationParentPersistenceEdition;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class ChatConfigParent extends AbstractConfigurationParentPersistenceEdition<IChatConfiguration> {

	public ChatConfigParent(Plugin plugin) {
		super("chatconfig", EChatMessageCode.CHAT_CONFIG__EXPLANATION, plugin, ChatPersistence.getInstance());
		addEdition(ChatConfigEditionFactory.commonAdd());
		addEdition(ChatConfigEditionFactory.synchronizedChatConfig());
		addEdition(ChatConfigEditionFactory.commonRemove());
		addEdition(ChatConfigEditionFactory.commonModify());
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
