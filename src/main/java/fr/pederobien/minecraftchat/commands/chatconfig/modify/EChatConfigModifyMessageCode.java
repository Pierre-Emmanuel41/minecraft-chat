package fr.pederobien.minecraftchat.commands.chatconfig.modify;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EChatConfigModifyMessageCode implements IMinecraftMessageCode {
	// Code for command modify
	CHAT_MODIFY__EXPLANATION,

	// Code for command modify name
	CHAT_MODIFY_NAME__EXPLANATION, CHAT_MODIFY_NAME__OLD_NAME_IS_MISSING, CHAT_MODIFY_NAME__NEW_NAME_IS_MISSING, CHAT_MODIFY_NAME__CHAT_DOES_NOT_EXIST,
	CHAT_MODIFY_NAME__CHAT_NAME_FORBIDDEN, CHAT_MODIFY_NAME__CHAT_NAME_ALREADY_USED, CHAT_MODIFY_NAME__CHAT_RENAMED,

	// Code for command modify color
	CHAT_MODIFY_COLOR__EXPLANATION, CHAT_MODIFY_COLOR__CHAT_NAME_IS_MISSING, CHAT_MODIFY_COLOR__COLOR_NAME_IS_MISSING, CHAT_MODIFY_COLOR__CHAT_DOES_NOT_EXIST,
	CHAT_MODIFY_COLOR__COLOR_DOES_NOT_EXIST, CHAT_MODIFY_COLOR__COLOR_ALREADY_USED, CHAT_MODIFY_COLOR__COLOR_UPDATED;

	private Permission permission;

	private EChatConfigModifyMessageCode() {
		this(Permission.OPERATORS);
	}

	private EChatConfigModifyMessageCode(Permission permission) {
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
