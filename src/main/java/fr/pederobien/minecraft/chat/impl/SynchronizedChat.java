package fr.pederobien.minecraft.chat.impl;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.event.ChatListChatRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamColorChangePostEvent;
import fr.pederobien.minecraft.game.event.TeamNameChangePostEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class SynchronizedChat extends Chat implements IEventListener {
	private ITeam team;

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param team The team associated to this chat.
	 */
	public SynchronizedChat(ITeam team) {
		super(team.getName());
		this.team = team;

		setColor(team.getColor());
		for (Player player : team.getPlayers())
			getPlayers().add(player);
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
	private void onPlayerAdd(TeamPlayerListPlayerAddPostEvent event) {
		if (!event.getList().getParent().equals(team))
			return;

		getPlayers().add(event.getPlayer());
	}

	@EventHandler
	private void onPlayerRemove(TeamPlayerListPlayerRemovePostEvent event) {
		if (!(event.getList().getParent().equals(team)))
			return;

		getPlayers().remove(event.getPlayer());
	}

	@EventHandler
	private void onChatRemove(ChatListChatRemovePostEvent event) {
		if (!event.getChat().equals(this))
			return;

		getPlayers().clear();
		EventManager.unregisterListener(this);
	}
}
