package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.chat.interfaces.IChatList;

public class ChatConfig implements IChatConfig {
	private IChat global;
	private IChat operators;
	private IChatList chats;

	/**
	 * Creates a chat configuration.
	 * 
	 * @param name The configuration name.
	 */
	public ChatConfig(String name) {
		global = new GlobalChat();
		operators = new OperatorsChat();
		chats = new ChatList(name);
	}

	@Override
	public IChat getGlobalChat() {
		return global;
	}

	@Override
	public IChat getOperatorChat() {
		return operators;
	}

	@Override
	public IChatList getChats() {
		return chats;
	}
}
