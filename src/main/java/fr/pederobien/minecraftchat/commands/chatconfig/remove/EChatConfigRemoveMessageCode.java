package fr.pederobien.minecraftchat.commands.chatconfig.remove;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EChatConfigRemoveMessageCode implements IMinecraftMessageCode {
	REMOVE__EXPLANATION,

	// Code for command remove chat
	REMOVE_CHAT__EXPLANATION, REMOVE_CHAT__ALL_CHATS_REMOVED, REMOVE_CHAT__CHAT_DOES_NOT_EXIST, REMOVE_CHAT__ANY_CHAT_REMOVED, REMOVE_CHAT__ONE_CHAT_REMOVED,
	REMOVE_CHAT__SEVERAL_CHATS_REMOVED,

	// Code for command remove player
	REMOVE_PLAYER__EXPLANATION, REMOVE_PLAYER__ALL_PLAYERS_REMOVED_FROM_CHATS, REMOVE_PLAYER__PLAYER_NOT_REGISTERED_IN_CHATS, REMOVE_PLAYER__ANY_PLAYER_REMOVED_FROM_CHAT,
	REMOVE_PLAYER__ONE_PLAYER_REMOVED_FROM_CHAT, REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED_FROM_CHATS;

	private Permission permission;

	private EChatConfigRemoveMessageCode() {
		this(Permission.OPERATORS);
	}

	private EChatConfigRemoveMessageCode(Permission permission) {
		this.permission = permission;
	}

	@Override
	public String value() {
		return toString();
	}

	@Override
	public Permission getPermission() {
		return permission;
	}

	@Override
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
