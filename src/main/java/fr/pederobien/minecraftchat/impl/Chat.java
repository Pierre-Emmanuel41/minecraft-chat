package fr.pederobien.minecraftchat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftgameplateform.impl.element.AbstractNominable;
import fr.pederobien.minecraftgameplateform.utils.EColor;
import fr.pederobien.minecraftmanagers.MessageManager;

public class Chat extends AbstractNominable implements IChat {
	private List<Player> players;
	private EColor color;

	public Chat(String name) {
		super(name);
		players = new ArrayList<>();
	}

	@Override
	public void add(Player player) {
		players.add(player);
	}

	@Override
	public void remove(Player player) {
		players.remove(player);
	}

	@Override
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	@Override
	public EColor getColor() {
		return color;
	}

	@Override
	public void setColor(EColor color) {
		this.color = color;
	}

	@Override
	public void sendMessage(Player sender, String message) {
		if (!players.contains(sender))
			throw new PlayerNotRegisteredInChatException(sender, this);
		for (Player player : players)
			MessageManager.sendMessage(player, "[" + color.getInColor(player.equals(sender) ? "me" : player.getName()) + " -> " + getName() + "] " + message);
	}
}
