package fr.pederobien.minecraftchat;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EChatMessageCode implements IMinecraftMessageCode {
	CHAT_CONFIG__EXPLANATION,

	// Code for command new
	NEW_CHAT_CONFIG__EXPLANATION, NEW_CHAT_CONFIG__NAME_IS_MISSING, NEW_CHAT_CONFIG__NAME_ALREADY_TAKEN, NEW_CHAT_CONFIG__CONFIGURATION_CREATED,

	// Code for command rename
	RENAME_CHAT_CONFIG__EXPLANATION, RENAME_CHAT_CONFIG__NAME_IS_MISSING, RENAME_CHAT_CONFIG__NAME_ALREADY_TAKEN, RENAME_CHAT_CONFIG__CONFIGURATION_RENAMED,

	// Code for command save
	SAVE_CHAT_CONFIG__EXPLANATION, SAVE_CHAT_CONFIG__CONFIGURATION_SAVED,

	// Code for command list
	LIST_CHAT_CONFIG__EXPLANATION, LIST_CHAT_CONFIG__NO_REGISTERED_CONFIGURATION, LIST_CHAT_CONFIG__ONE_REGISTERED_CONFIGURATION, LIST_CHAT_CONFIG__SEVERAL_ELEMENTS,

	// Code for command delete
	DELETE_CHAT_CONFIG__EXPLANATION, DELETE_CHAT_CONFIG__NAME_IS_MISSING, DELETE_CHAT_CONFIG__DID_NOT_DELETE, DELETE_CHAT_CONFIG__CONFIGURATION_DELETED,

	// Code for command details
	DETAILS_CHAT_CONFIG__EXPLANATION, DETAILS_CHAT_CONFIG__ON_DETAILS,

	// Code for command load
	LOAD_CHAT_CONFIG__EXPLANATION, LOAD_CHAT_CONFIG__NAME_IS_MISSING, LOAD_CHAT_CONFIG__CONFIGURATION_LOADED,

	// Code for command isSynchronized
	IS_CHAT_CONFIG_SYNCHRONIZED__EXPLANATION, IS_CHAT_CONFIG_SYNCHRONIZED__VALUE_IS_MISSING, IS_CHAT_CONFIG_SYNCHRONIZED__TRUE, IS_CHAT_CONFIG_SYNCHRONIZED__FALSE;

	private Permission permission;

	private EChatMessageCode() {
		this(Permission.OPERATORS);
	}

	private EChatMessageCode(Permission permission) {
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
