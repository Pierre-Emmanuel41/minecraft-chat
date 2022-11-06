package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.chat.event.ChatNameChangePostEvent;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class ChatList extends SimpleChatList implements IEventListener {

	/**
	 * Creates a list of chats.
	 * 
	 * @param name The name of the list.
	 */
	public ChatList(String name) {
		super(name);

		EventManager.registerListener(this);
	}

	@EventHandler
	private void onChatNameChange(ChatNameChangePostEvent event) {
		onChatNameChange(event.getChat(), event.getOldName());
	}
}
