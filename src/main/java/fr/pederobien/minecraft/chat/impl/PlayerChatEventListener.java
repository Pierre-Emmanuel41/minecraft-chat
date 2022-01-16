package fr.pederobien.minecraft.chat.impl;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EventListener;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class PlayerChatEventListener extends EventListener implements IEventListener {
	private Map<IGame, ITeamConfigurable> teams;

	public PlayerChatEventListener() {
		teams = new HashMap<IGame, ITeamConfigurable>();
		EventManager.registerListener(this);
	}

	@fr.pederobien.utils.event.EventHandler
	private void onGameStart(GameStartPostEvent event) {
		if (!(event.getGame() instanceof ITeamConfigurable))
			return;

		teams.put(event.getGame(), (ITeamConfigurable) event.getGame());
	}

	@fr.pederobien.utils.event.EventHandler
	private void onGameStop(GameStopPostEvent event) {
		if (!(event.getGame() instanceof ITeamConfigurable))
			return;

		teams.remove(event.getGame());
	}

	@org.bukkit.event.EventHandler
	private void onPlayerChat(AsyncPlayerChatEvent event) {
		for (ITeamConfigurable configurable : teams.values())
			for (ITeam team : configurable.getTeams())
				if (team.getPlayers().toList().contains(event.getPlayer()))
					event.setFormat(String.format("<%s> ", team.getColor().getInColor(event.getPlayer().getName())).concat("%2$s"));
	}
}
