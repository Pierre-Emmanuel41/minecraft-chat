package fr.pederobien.minecraft.chat.commands.chatconfig.remove;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EChatConfigRemoveMessageCode implements IMinecraftMessageCode {
	CHAT_REMOVE__EXPLANATION,

	// Code for command remove chat
	CHAT_REMOVE_CHAT__EXPLANATION, CHAT_REMOVE_CHAT__ALL_CHATS_REMOVED, CHAT_REMOVE_CHAT__CHAT_DOES_NOT_EXIST, CHAT_REMOVE_CHAT__ANY_CHAT_REMOVED,
	CHAT_REMOVE_CHAT__ONE_CHAT_REMOVED, CHAT_REMOVE_CHAT__SEVERAL_CHATS_REMOVED,

	// Code for command remove player
	CHAT_REMOVE_PLAYER__EXPLANATION, CHAT_REMOVE_PLAYER__ALL_PLAYERS_REMOVED, CHAT_REMOVE_PLAYER__PLAYER_NOT_REGISTERED, CHAT_REMOVE_PLAYER__ANY_PLAYER_REMOVED,
	CHAT_REMOVE_PLAYER__ONE_PLAYER_REMOVED, CHAT_REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED;

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
