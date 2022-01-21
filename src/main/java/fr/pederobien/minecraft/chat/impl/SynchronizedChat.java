package fr.pederobien.minecraft.chat.impl;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.event.ChatListChatRemovePostEvent;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.game.event.PlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.PlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamColorChangePostEvent;
import fr.pederobien.minecraft.game.event.TeamNameChangePostEvent;
import fr.pederobien.minecraft.game.exceptions.PlayerAlreadyRegisteredException;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class SynchronizedChat extends Chat implements IEventListener {
	private ITeam team;
	private IChat operators;

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param team      The team associated to this chat.
	 * @param operators The chat that contains only operators.
	 */
	public SynchronizedChat(ITeam team, IChat operators) {
		super(team.getName());
		this.team = team;
		this.operators = operators;

		setColor(team.getColor());
		for (Player player : team.getPlayers())
			register(player);
		EventManager.registerListener(this);
	}

	@EventHandler
	private void onTeamNameChange(TeamNameChangePostEvent event) {
		if (!event.getTeam().equals(team))
			return;

		setName(event.getTeam().getName());
	}

	@EventHandler
	private void onTeamColorChange(TeamColorChangePostEvent event) {
		if (!event.getTeam().equals(team))
			return;

		setColor(event.getTeam().getColor());
	}

	@EventHandler
	private void onPlayerAdd(PlayerListPlayerAddPostEvent event) {
		if (!event.getList().equals(team.getPlayers()))
			return;

		register(event.getPlayer());
	}

	@EventHandler
	private void onPlayerRemove(PlayerListPlayerRemovePostEvent event) {
		if (!event.getList().equals(team.getPlayers()))
			return;

		getPlayers().remove(event.getPlayer());
	}

	@EventHandler
	private void onChatRemove(ChatListChatRemovePostEvent event) {
		if (!event.getChat().equals(this))
			return;

		EventManager.unregisterListener(this);
	}

	private void register(Player player) {
		try {
			getPlayers().add(player);
			if (player.isOp())
				operators.getPlayers().add(player);
		} catch (PlayerAlreadyRegisteredException e) {
			// do nothing
		}
	}
}
