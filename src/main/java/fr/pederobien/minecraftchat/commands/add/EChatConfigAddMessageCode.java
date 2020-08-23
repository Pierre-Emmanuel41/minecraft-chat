package fr.pederobien.minecraftchat.commands.add;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EChatConfigAddMessageCode implements IMinecraftMessageCode {
	// Code for command add
	ADD__EXPLANATION,

	// Code for command add chat
	ADD_CHAT__EXPLANATION, ADD_CHAT__CHAT_NAME_IS_MISSING, ADD_CHAT__COLOR_NAME_IS_MISSING, ADD_CHAT__COLOR_DOES_NOT_EXIST, ADD_CHAT__CHAT_NAME_FORBIDDEN,
	ADD_CHAT__CHAT_NAME_ALREADY_USED, ADD_CHAT__COLOR_ALREADY_USED, ADD_CHAT__CHAT_ADDED(Permission.ALL),

	// Code for command add player
	ADD_PLAYER__EXPLANATION, ADD_PLAYER__CHAT_NAME_IS_MISSING, ADD_PLAYER__CHAT_DOES_NOT_EXIST, ADD_PLAYER__PLAYER_ALREADY_REGISTERED_IN_CHAT,
	ADD_PLAYER__ANY_PLAYER_ADDED(Permission.ALL), ADD_PLAYER__ONE_PLAYER_ADDED(Permission.ALL), ADD_PLAYER__SEVERAL_PLAYERS_ADDED(Permission.ALL);

	private Permission permission;

	private EChatConfigAddMessageCode() {
		this(Permission.OPERATORS);
	}

	private EChatConfigAddMessageCode(Permission permission) {
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
