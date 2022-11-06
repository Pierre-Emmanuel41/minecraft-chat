package fr.pederobien.minecraft.chat.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.event.ChatPlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.chat.event.ChatPlayerListPlayerAddPreEvent;
import fr.pederobien.minecraft.chat.event.ChatPlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.chat.event.ChatPlayerListPlayerRemovePreEvent;
import fr.pederobien.minecraft.chat.exception.ChatPlayerAlreadyRegisteredException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.utils.event.EventManager;

public class ChatPlayerList extends PlayerList<IChat> implements IChatPlayerList {

	public ChatPlayerList(IChat parent, String name) {
		super(parent, name);
	}

	@Override
	public void add(Player player) {
		getLock().lock();
		try {
			if (getPlayers().get(player.getName()) != null)
				throw new ChatPlayerAlreadyRegisteredException(this, player);

			Runnable update = () -> getPlayers().put(player.getName(), player);
			EventManager.callEvent(new ChatPlayerListPlayerAddPreEvent(this, player), update, new ChatPlayerListPlayerAddPostEvent(this, player));
		} finally {
			getLock().unlock();
		}
	}

	@Override
	public Player remove(String name) {
		getLock().lock();
		try {
			Optional<Player> optPlayer = getPlayer(name);
			if (!optPlayer.isPresent())
				return null;

			Runnable update = () -> getPlayers().remove(optPlayer.get().getName());
			ChatPlayerListPlayerRemovePreEvent preEvent = new ChatPlayerListPlayerRemovePreEvent(this, optPlayer.get());
			EventManager.callEvent(preEvent, update, new ChatPlayerListPlayerRemovePostEvent(this, optPlayer.get()));

			return preEvent.isCancelled() ? null : optPlayer.get();
		} finally {
			getLock().unlock();
		}
	}

	@Override
	public boolean remove(Player player) {
		getLock().lock();
		try {
			if (getPlayers().get(player.getName()) == null)
				return false;

			Runnable update = () -> getPlayers().remove(player.getName());
			ChatPlayerListPlayerRemovePreEvent preEvent = new ChatPlayerListPlayerRemovePreEvent(this, player);
			EventManager.callEvent(preEvent, update, new ChatPlayerListPlayerRemovePostEvent(this, player));

			return !preEvent.isCancelled();
		} finally {
			getLock().unlock();
		}
	}

	@Override
	public void clear() {
		getLock().lock();
		try {
			Set<String> names = new HashSet<String>(getPlayers().keySet());
			for (String name : names)
				EventManager.callEvent(new ChatPlayerListPlayerRemovePostEvent(this, getPlayers().remove(name)));
		} finally {
			getLock().unlock();
		}
	}
}
