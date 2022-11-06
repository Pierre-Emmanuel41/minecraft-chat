package fr.pederobien.minecraft.chat.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.event.ChatListChatAddPostEvent;
import fr.pederobien.minecraft.chat.event.ChatListChatRemovePostEvent;
import fr.pederobien.minecraft.chat.exception.ChatAlreadyRegisteredException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.utils.event.EventManager;

public class SimpleChatList implements IChatList {
	private String name;
	private Map<String, IChat> chats;
	private Lock lock;

	/**
	 * Creates a list of chats associated to the given name.
	 * 
	 * @param name The list's name.
	 */
	public SimpleChatList(String name) {
		this.name = name;
		chats = new LinkedHashMap<String, IChat>();
		lock = new ReentrantLock(true);
	}

	@Override
	public Iterator<IChat> iterator() {
		return chats.values().iterator();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void add(IChat chat) {
		addChat(chat);
		EventManager.callEvent(new ChatListChatAddPostEvent(this, chat));
	}

	@Override
	public IChat remove(String name) {
		IChat chat = removeChat(name);
		if (chat != null)
			EventManager.callEvent(new ChatListChatRemovePostEvent(this, chat));
		return chat;
	}

	@Override
	public IChat remove(EColor color) {
		lock.lock();
		try {
			Iterator<Map.Entry<String, IChat>> iterator = chats.entrySet().iterator();
			IChat chat = null;
			while (iterator.hasNext()) {
				Map.Entry<String, IChat> entry = iterator.next();
				if (entry.getValue().getColor().equals(color))
					chat = entry.getValue();
			}

			if (chat != null) {
				remove(chat);
				return chat;
			}
		} finally {
			lock.unlock();
		}
		return null;
	}

	@Override
	public boolean remove(IChat chat) {
		return remove(chat.getName()) != null;
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			Set<String> names = new HashSet<String>(chats.keySet());
			for (String name : names) {
				IChat chat = chats.remove(name);
				chat.getPlayers().clear();
				EventManager.callEvent(new ChatListChatRemovePostEvent(this, chat));
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Optional<IChat> get(String name) {
		return Optional.ofNullable(chats.get(name));
	}

	@Override
	public Optional<IChat> get(EColor color) {
		lock.lock();
		try {
			Iterator<Map.Entry<String, IChat>> iterator = chats.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, IChat> entry = iterator.next();
				if (entry.getValue().getColor().equals(color))
					return Optional.of(entry.getValue());
			}
		} finally {
			lock.unlock();
		}
		return Optional.empty();
	}

	@Override
	public List<IChat> getChats(Player player) {
		List<IChat> playerChats = new ArrayList<IChat>();
		lock.lock();
		try {
			for (IChat chat : chats.values())
				if (chat.getPlayers().toList().contains(player))
					playerChats.add(chat);
		} finally {
			lock.unlock();
		}
		return playerChats;
	}

	@Override
	public Stream<IChat> stream() {
		return chats.values().stream();
	}

	@Override
	public List<IChat> toList() {
		return new ArrayList<IChat>(chats.values());
	}

	@Override
	public List<IChat> movePlayer(Player player, IChat... chats) {
		if (chats.length == 0)
			return new ArrayList<IChat>();

		List<IChat> origins = getChats(player);
		List<IChat> removed = new ArrayList<IChat>();

		lock.lock();
		try {
			for (IChat origin : origins)
				if (!origin.getName().equals(GlobalChat.NAME) && !origin.getName().equals(OperatorsChat.NAME)) {
					origin.getPlayers().remove(player);
					removed.add(origin);
				}

			for (IChat chat : chats)
				chat.getPlayers().add(player);
		} finally {
			lock.unlock();
		}
		return removed;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("Name : " + getName());
		joiner.add("Chats :" + (toList().isEmpty() ? " none" : ""));
		for (IChat chat : toList())
			joiner.add(chat.toString());
		return joiner.toString();
	}

	protected void onChatNameChange(IChat chat, String oldName) {
		Optional<IChat> optOldChat = get(oldName);
		if (!optOldChat.isPresent())
			return;

		Optional<IChat> optNewChat = get(chat.getName());
		if (optNewChat.isPresent())
			throw new ChatAlreadyRegisteredException(this, optNewChat.get());

		lock.lock();
		try {
			chats.remove(oldName);
			chats.put(chat.getName(), chat);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that adds a chat to the chats list.
	 * 
	 * @param chat The chat to add.
	 * 
	 * @throws ChatAlreadyRegisteredException if a chat is already registered for the chat name.
	 */
	private void addChat(IChat chat) {
		lock.lock();
		try {
			if (chats.get(chat.getName()) != null)
				throw new ChatAlreadyRegisteredException(this, chat);

			chats.put(chat.getName(), chat);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a chats from the chats list.
	 * 
	 * @param chat The chat to remove.
	 * 
	 * @return The chat associated to the given name if registered, null otherwise.
	 */
	private IChat removeChat(String name) {
		lock.lock();
		try {
			IChat chat = chats.remove(name);
			if (chat != null)
				chat.getPlayers().clear();
			return chat;
		} finally {
			lock.unlock();
		}
	}
}
