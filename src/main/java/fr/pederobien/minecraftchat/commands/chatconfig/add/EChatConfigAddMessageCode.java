package fr.pederobien.minecraftchat.commands.chatconfig.add;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EChatConfigAddMessageCode implements IMinecraftMessageCode {
	// Code for command add
	CHAT_ADD__EXPLANATION,

	// Code for command add chat
	CHAT_ADD_CHAT__EXPLANATION, CHAT_ADD_CHAT__CHAT_NAME_IS_MISSING, CHAT_ADD_CHAT__COLOR_NAME_IS_MISSING, CHAT_ADD_CHAT__COLOR_DOES_NOT_EXIST,
	CHAT_ADD_CHAT__CHAT_NAME_FORBIDDEN, CHAT_ADD_CHAT__CHAT_NAME_ALREADY_USED, CHAT_ADD_CHAT__COLOR_ALREADY_USED, CHAT_ADD_CHAT__CHAT_ADDED(Permission.ALL),

	// Code for command add player
	CHAT_ADD_PLAYER__EXPLANATION, CHAT_ADD_PLAYER__CHAT_NAME_IS_MISSING, CHAT_ADD_PLAYER__CHAT_DOES_NOT_EXIST, CHAT_ADD_PLAYER__PLAYER_ALREADY_REGISTERED_IN_CHAT,
	CHAT_ADD_PLAYER__ANY_PLAYER_ADDED(Permission.ALL), CHAT_ADD_PLAYER__ONE_PLAYER_ADDED(Permission.ALL), CHAT_ADD_PLAYER__SEVERAL_PLAYERS_ADDED(Permission.ALL);

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
