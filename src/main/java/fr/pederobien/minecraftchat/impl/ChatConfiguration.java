package fr.pederobien.minecraftchat.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraftchat.exception.ChatNameForbiddenException;
import fr.pederobien.minecraftchat.exception.ChatNotRegisteredException;
import fr.pederobien.minecraftchat.exception.ChatWithSameColorAlreadyExistsException;
import fr.pederobien.minecraftchat.exception.ChatWithSameNameAlreadyExistsException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.AbstractNominable;
import fr.pederobien.minecraftgameplateform.interfaces.element.IGameConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecraftmanagers.EColor;

public class ChatConfiguration extends AbstractNominable implements IChatConfiguration {
	private Map<String, IChat> chats;
	private boolean isSynchronized;
	private Map<ITeam, IChat> synchronizedChats;

	public ChatConfiguration(String name) {
		super(name);
		chats = new HashMap<String, IChat>();
		synchronizedChats = new HashMap<ITeam, IChat>();
		isSynchronized = false;
	}

	@Override
	public void onTeamAdded(IGameConfiguration configuration, ITeam team) {
		synchronizedAdd(team);
	}

	@Override
	public void onTeamRemoved(IGameConfiguration configuration, ITeam team) {
		synchronizedRemove(team, true);
	}

	@Override
	public void onPvpTimeChanged(IGameConfiguration configuration, LocalTime oldTime, LocalTime newTime) {

	}

	@Override
	public void onConfigurationChanged(IGameConfiguration oldConfiguration, IGameConfiguration newConfiguration) {
		unSynchronizeOnConfiguration(oldConfiguration);
		synchronizeOnConfiguration(newConfiguration);
	}

	@Override
	public IChat register(String name, EColor color) {
		if (isSynchronized)
			return null;
		return add(name, color);
	}

	@Override
	public IChat unRegister(String name) {
		if (isSynchronized)
			return null;
		return remove(name);
	}

	@Override
	public List<IChat> clearChats() {
		List<IChat> removedChats = new ArrayList<>(chats.values());
		chats.clear();
		return removedChats;
	}

	@Override
	public Optional<IChat> getChat(String name) {
		return Optional.ofNullable(chats.get(name));
	}

	@Override
	public List<IChat> getChats(Player player) {
		List<IChat> playerChats = new ArrayList<IChat>();
		for (IChat chat : chats.values())
			if (chat.getPlayers().contains(player))
				playerChats.add(chat);
		return playerChats;
	}

	@Override
	public List<IChat> getChats() {
		return Collections.unmodifiableList(new ArrayList<IChat>(chats.values()));
	}

	@Override
	public boolean isSynchronized() {
		return isSynchronized;
	}

	@Override
	public void setIsSynchronized(boolean isSynchronized) {
		if (isSynchronized && !this.isSynchronized) {
			synchronizeOnConfiguration(Plateform.getGameConfigurationContext().getGameConfiguration());
			Plateform.getGameConfigurationContext().addContextObserver(this);
		} else if (!isSynchronized && this.isSynchronized) {
			unSynchronizeOnConfiguration(Plateform.getGameConfigurationContext().getGameConfiguration());
			Plateform.getGameConfigurationContext().removeContextObserver(this);
		}
		this.isSynchronized = isSynchronized;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("Name : " + getName());
		joiner.add("Synchronized : " + isSynchronized());
		joiner.add("Chats :" + (getChats().isEmpty() ? " none" : ""));
		for (IChat chat : getChats())
			joiner.add(chat.toString());
		return joiner.toString();
	}

	private IChat add(String name, EColor color) {
		IChat chat = chats.get(name);
		if (chat != null)
			throw new ChatWithSameNameAlreadyExistsException(this, chat);
		if (name.equals("all"))
			throw new ChatNameForbiddenException(this, "all");

		for (IChat c : getChats())
			if (c.getColor().equals(color))
				throw new ChatWithSameColorAlreadyExistsException(this, c);

		IChat registeredChat = new Chat(name, this);
		registeredChat.setColor(color);
		chats.put(name, registeredChat);
		return registeredChat;
	}

	private IChat remove(String name) {
		IChat chat = chats.remove(name);
		if (chat == null)
			throw new ChatNotRegisteredException(this, name);

		return chat;
	}

	private void synchronizeOnConfiguration(IGameConfiguration configuration) {
		if (configuration == null)
			return;

		isSynchronized = false;
		configuration.addObserver(this);
		for (ITeam team : configuration.getTeams())
			synchronizedAdd(team);
		isSynchronized = true;
	}

	private void unSynchronizeOnConfiguration(IGameConfiguration configuration) {
		if (configuration == null)
			return;

		configuration.removeObserver(this);
		Iterator<Map.Entry<ITeam, IChat>> iterator = synchronizedChats.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<ITeam, IChat> entry = iterator.next();
			synchronizedRemove(entry.getKey(), false);
			iterator.remove();
		}

		chats.clear();
		synchronizedChats.clear();
	}

	private void synchronizedAdd(ITeam team) {
		isSynchronized = false;
		IChat chat = add(team.getName(), team.getColor());
		team.addObserver(chat);
		synchronizedChats.put(team, chat);
		for (Player player : team.getPlayers())
			chat.add(player);
		isSynchronized = true;
	}

	private void synchronizedRemove(ITeam team, boolean removeFromSynchronizedChats) {
		isSynchronized = false;
		remove(team.getName());
		IChat chat = synchronizedChats.get(team);
		if (chat != null) {
			team.removeObserver(chat);
			for (Player player : team.getPlayers())
				chat.remove(player);
			if (removeFromSynchronizedChats)
				synchronizedChats.remove(team);
		}
		isSynchronized = true;
	}
}
