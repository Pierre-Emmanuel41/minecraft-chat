package fr.pederobien.minecraftchat.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.exception.PlayerAlreadyRegisteredInChatException;
import fr.pederobien.minecraftchat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.AbstractNominable;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.EColor;
import fr.pederobien.minecraftmanagers.MessageManager;

public class Chat extends AbstractNominable implements IChat {
	private List<Player> players;
	private EColor color;
	private IChatConfiguration configuration;

	public Chat(String name, IChatConfiguration configuration) {
		super(name);
		this.configuration = configuration;
		players = new ArrayList<>();
		color = EColor.RESET;
	}

	@Override
	public void onColorChanged(ITeam team, EColor oldColor, EColor newColor) {
		color = newColor;
	}

	@Override
	public void onPlayerAdded(ITeam team, Player player) {
		internalAdd(player);
	}

	@Override
	public void onPlayerRemoved(ITeam team, Player player) {
		players.remove(player);
	}

	@Override
	public void onClone(ITeam team) {

	}

	@Override
	public String getColoredName() {
		return getColor().getInColor(getName());
	}

	@Override
	public void add(Player player) {
		if (!isSynchronized())
			internalAdd(player);
	}

	@Override
	public void remove(Player player) {
		if (!isSynchronized())
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
		if (!isSynchronized())
			this.color = color;
	}

	@Override
	public void sendMessage(Player sender, String message) {
		if (!players.contains(sender))
			throw new PlayerNotRegisteredInChatException(this, sender);
		for (Player player : players)
			MessageManager.sendMessage(player, color.getInColor("[" + (player.equals(sender) ? "me" : sender.getName()) + " -> " + getName() + "] ") + message);
	}

	@Override
	public String toString() {
		StringJoiner players = new StringJoiner(" ", "[", "]");
		for (Player player : getPlayers())
			players.add(player.getName());
		return getColor().getInColor(getName() + " " + players.toString());
	}

	private void internalAdd(Player player) {
		if (players.contains(player))
			throw new PlayerAlreadyRegisteredInChatException(this, player);
		players.add(player);
	}

	private boolean isSynchronized() {
		return configuration.isSynchronized();
	}
}
