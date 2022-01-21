package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.chat.event.SuperChatListListRemovePostEvent;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.game.event.TeamListTeamAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamListTeamRemovePostEvent;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class SynchronizedChatList extends ChatList implements IEventListener {
	private ITeamList teams;
	private IChat operators;

	/**
	 * Creates a chats list synchronized with the given teams list.
	 * 
	 * @param teams The list of teams associated to this node.
	 */
	public SynchronizedChatList(ITeamList teams) {
		super(teams.getName());
		this.teams = teams;

		add(operators = new OperatorsChat());

		for (ITeam team : teams)
			add(new SynchronizedChat(team, operators));
	}

	@Override
	public String getName() {
		return teams.getName();
	}

	@EventHandler
	private void onTeamAdd(TeamListTeamAddPostEvent event) {
		if (!event.getList().equals(teams))
			return;

		add(new SynchronizedChat(event.getTeam(), operators));
	}

	@EventHandler
	private void onTeamRemove(TeamListTeamRemovePostEvent event) {
		if (!event.getList().equals(teams))
			return;

		remove(event.getTeam().getName());
	}

	@EventHandler
	private void onChatListRemove(SuperChatListListRemovePostEvent event) {
		if (!event.getList().equals(this))
			return;

		EventManager.unregisterListener(this);
	}
}
