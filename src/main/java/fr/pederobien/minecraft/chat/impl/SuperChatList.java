package fr.pederobien.minecraft.chat.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.event.SuperChatListListAddPostEvent;
import fr.pederobien.minecraft.chat.event.SuperChatListListRemovePostEvent;
import fr.pederobien.minecraft.chat.exception.ChatListAlreadyRegisteredException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;
import fr.pederobien.utils.event.EventManager;

public class SuperChatList implements ISuperChatList {
	private String name;
	private Map<String, IChatList> lists;
	private Lock lock;

	public SuperChatList(String name) {
		this.name = name;
		lists = new LinkedHashMap<String, IChatList>();
		lock = new ReentrantLock(true);
	}

	@Override
	public Iterator<IChatList> iterator() {
		return lists.values().iterator();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void add(IChatList list) {
		addList(list);
		EventManager.callEvent(new SuperChatListListAddPostEvent(this, list));
	}

	@Override
	public IChatList remove(String name) {
		IChatList list = removeList(name);
		if (list != null)
			EventManager.callEvent(new SuperChatListListRemovePostEvent(this, list));
		return list;
	}

	@Override
	public boolean remove(IChatList list) {
		return remove(list.getName()) != null;
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			Set<String> names = new HashSet<String>(lists.keySet());
			for (String name : names) {
				IChatList list = lists.remove(name);
				list.clear();
				EventManager.callEvent(new SuperChatListListRemovePostEvent(this, list));
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Optional<IChatList> getChats(String name) {
		return Optional.ofNullable(lists.get(name));
	}

	@Override
	public List<IChat> getChats(Player player) {
		List<IChat> chats = new ArrayList<IChat>();
		for (IChatList list : this)
			chats.addAll(list.getChats(player));
		return chats;
	}

	@Override
	public Optional<IChat> getChat(String name, Player player) {
		for (IChat chat : getChats(player))
			if (chat.getName().equals(name))
				return Optional.of(chat);
		return Optional.empty();
	}

	@Override
	public Stream<IChatList> stream() {
		return lists.values().stream();
	}

	@Override
	public List<IChatList> toList() {
		return new ArrayList<IChatList>(lists.values());
	}

	/**
	 * Thread safe operation that adds a chats list to the list.
	 * 
	 * @param list The chats list to add.
	 * 
	 * @throws ChatListAlreadyRegisteredException if a list is already registered for the list name.
	 */
	private void addList(IChatList list) {
		lock.lock();
		try {
			Optional<IChatList> optList = getChats(list.getName());
			if (optList.isPresent())
				throw new ChatListAlreadyRegisteredException(this, optList.get());

			lists.put(list.getName(), list);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a chats list from the list.
	 * 
	 * @param name The name of the list to remove.
	 * 
	 * @return The list associated to the given name if registered, null otherwise.
	 */
	private IChatList removeList(String name) {
		lock.lock();
		try {
			IChatList list = lists.remove(name);
			if (list != null)
				list.clear();
			return list;
		} finally {
			lock.unlock();
		}
	}
}
