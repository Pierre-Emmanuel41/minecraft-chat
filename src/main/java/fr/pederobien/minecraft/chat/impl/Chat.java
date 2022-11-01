package fr.pederobien.minecraft.chat.impl;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.minecraft.game.impl.PlayerQuitOrJoinEventHandler;

public class Chat extends SimpleChat {

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param name The chat name.
	 */
	public Chat(String name) {
		super(name);

		PlayerQuitOrJoinEventHandler.instance().registerQuitEventHandler(this, event -> onPlayerQuitEvent(event));
		PlayerQuitOrJoinEventHandler.instance().registerJoinEventHandler(this, event -> onPlayerJoinEvent(event));
	}

	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		getLock().lock();
		try {
			if (!getPlayers().getPlayer(event.getPlayer().getName()).isPresent())
				return;

			getPlayers().remove(event.getPlayer());
			getQuitPlayers().put(event.getPlayer().getName(), event.getPlayer());
		} finally {
			getLock().unlock();
		}
	}

	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		getLock().lock();
		try {
			if (getQuitPlayers().get(event.getPlayer().getName()) == null)
				return;

			getQuitPlayers().remove(event.getPlayer().getName());
			getPlayers().add(event.getPlayer());
		} finally {
			getLock().unlock();
		}
	}
}
