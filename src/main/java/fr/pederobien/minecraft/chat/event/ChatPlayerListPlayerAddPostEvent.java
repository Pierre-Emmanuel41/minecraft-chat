package fr.pederobien.minecraft.chat.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.interfaces.IChatPlayerList;

public class ChatPlayerListPlayerAddPostEvent extends ChatPlayerListEvent {
	private Player player;

	/**
	 * Creates an event thrown when a player has been added to the players list of a team.
	 * 
	 * @param list   The list to which a player has been added.
	 * @param player The added player.
	 */
	public ChatPlayerListPlayerAddPostEvent(IChatPlayerList list, Player player) {
		super(list);
		this.player = player;
	}

	/**
	 * @return The added player.
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
