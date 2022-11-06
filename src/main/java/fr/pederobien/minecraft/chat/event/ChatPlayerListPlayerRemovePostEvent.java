package fr.pederobien.minecraft.chat.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;

public class ChatPlayerListPlayerRemovePostEvent extends ChatPlayerListEvent {
	private Player player;

	/**
	 * Creates an event thrown when a player has been removed from a players list associated to a team.
	 * 
	 * @param list   The list from which a player has been removed.
	 * @param player The removed player.
	 */
	public ChatPlayerListPlayerRemovePostEvent(IChatPlayerList list, Player player) {
		super(list);
		this.player = player;
	}

	/**
	 * @return The removed player.
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
