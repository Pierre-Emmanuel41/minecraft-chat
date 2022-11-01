package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.game.event.TeamListTeamAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamListTeamRemovePostEvent;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.IEventListener;

public class SynchronizedChatList extends ChatList implements IEventListener {
	private ITeamList teams;

	/**
	 * Creates a chats list synchronized with the given teams list.
	 * 
	 * @param teams The list of teams associated to this node.
	 */
	public SynchronizedChatList(ITeamList teams) {
		super(teams.getName());
		this.teams = teams;

		for (ITeam team : teams)
			add(new SynchronizedChat(team));
	}

	@Override
	public String getName() {
		return teams.getName();
	}

	@EventHandler
	private void onTeamAdd(TeamListTeamAddPostEvent event) {
		if (!event.getList().equals(teams))
			return;

		add(new SynchronizedChat(event.getTeam()));
	}

	@EventHandler
	private void onTeamRemove(TeamListTeamRemovePostEvent event) {
		if (!event.getList().equals(teams))
			return;

		remove(event.getTeam().getName());
	}
}
