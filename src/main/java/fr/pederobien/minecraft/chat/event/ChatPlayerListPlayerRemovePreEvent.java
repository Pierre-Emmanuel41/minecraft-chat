package fr.pederobien.minecraft.chat.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;
import fr.pederobien.utils.ICancellable;

public class ChatPlayerListPlayerRemovePreEvent extends ChatPlayerListEvent implements ICancellable {
	private boolean isCancelled;
	private Player player;

	/**
	 * Creates an event thrown when a player is about to be removed from a players list associated to a team.
	 * 
	 * @param list   The list from which a player is about to be removed.
	 * @param player The player that is about to be removed from a players list associated to a team.
	 */
	public ChatPlayerListPlayerRemovePreEvent(IChatPlayerList list, Player player) {
		super(list);
		this.player = player;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	/**
	 * @return The player that is about to be removed.
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("list=" + getList().getName());
		joiner.add("player=" + getPlayer().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
