package fr.pederobien.minecraftchat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.pederobien.minecraftchat.exception.ChatAlreadyRegisteredException;
import fr.pederobien.minecraftchat.exception.ChatNotRegisteredException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.AbstractNominable;

public class ChatConfiguration extends AbstractNominable implements IChatConfiguration {
	private Map<String, IChat> chats;

	public ChatConfiguration(String name) {
		super(name);
		chats = new HashMap<String, IChat>();
	}

	@Override
	public IChat register(String name) {
		IChat chat = chats.get(name);
		if (chat != null)
			throw new ChatAlreadyRegisteredException(this, chat);

		chats.put(name, new Chat(name));
		return null;
	}

	@Override
	public IChat unRegister(String name) {
		IChat chat = chats.remove(name);
		if (chat == null)
			throw new ChatNotRegisteredException(this, name);

		return chat;
	}

	@Override
	public Optional<IChat> getChat(String name) {
		return Optional.ofNullable(chats.get(name));
	}

	@Override
	public List<IChat> getChats() {
		return Collections.unmodifiableList(new ArrayList<IChat>(chats.values()));
	}
}
