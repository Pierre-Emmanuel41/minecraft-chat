package fr.pederobien.minecraftchat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.pederobien.minecraftchat.exception.ChatNameForbiddenException;
import fr.pederobien.minecraftchat.exception.ChatNotRegisteredException;
import fr.pederobien.minecraftchat.exception.ChatWithSameColorAlreadyExistsException;
import fr.pederobien.minecraftchat.exception.ChatWithSameNameAlreadyExistsException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.AbstractNominable;
import fr.pederobien.minecraftgameplateform.utils.EColor;

public class ChatConfiguration extends AbstractNominable implements IChatConfiguration {
	private Map<String, IChat> chats;
	private boolean isSynchronized;

	public ChatConfiguration(String name) {
		super(name);
		chats = new HashMap<String, IChat>();
		isSynchronized = false;
	}

	@Override
	public IChat register(String name, EColor color) {
		if (isSynchronized)
			return null;

		IChat chat = chats.get(name);
		if (chat != null)
			throw new ChatWithSameNameAlreadyExistsException(this, chat);
		if (name.equals("all"))
			throw new ChatNameForbiddenException(this, "all");

		for (IChat c : getChats())
			if (c.getColor().equals(color))
				throw new ChatWithSameColorAlreadyExistsException(this, c);

		IChat registeredChat = new Chat(name);
		registeredChat.setColor(color);
		chats.put(name, registeredChat);
		return registeredChat;
	}

	@Override
	public IChat unRegister(String name) {
		if (isSynchronized)
			return null;

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

	@Override
	public boolean isSynchronized() {
		return isSynchronized;
	}

	@Override
	public void setIsSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
		chats.clear();
	}
}
