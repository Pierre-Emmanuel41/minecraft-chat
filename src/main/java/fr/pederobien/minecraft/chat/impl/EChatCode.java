package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;

public enum EChatCode implements IMinecraftCode {
	// Common codes -------------------------------------------------------------

	// Code for the "Me" translation
	CHAT__ME,

	// Code for the "none" translation
	CHAT_NONE,

	// Code for the message completion
	CHAT__MESSAGE_COMPLETION,

	// Code for the "chatMsg" command -------------------------------------------
	CHAT_MSG__EXPLANATION,

	// Code when a chat is not available for a player
	CHAT_MSG__CHAT_NOT_AVAILABLE,

	// Code when the player is not registered in a chat
	CHAT_MSG__PLAYER_NOT_REGISTERED,

	// Code for the "chatOpMsg" command -----------------------------------------
	OP_MSG__EXPLANATION,

	// Code for the "opMsg toChat" command --------------------------------------
	OP_MSG__TO_CHAT__EXPLANATION,

	// Code when the name of the chats list is missing
	OP_MSG__TO_CHAT__LIST_NAME_IS_MISSING,

	// Code when the list of chats does not exist
	OP_MSG__TO_CHAT__LIST_NOT_FOUND,

	// Code when the name of the chats list is missing
	OP_MSG__TO_CHAT__CHAT_NAME_IS_MISSING,

	// Code when the chat does not exist
	OP_MSG__TO_CHAT__CHAT_NOT_FOUND,

	// Code for the "opMsg toList" command --------------------------------------
	OP_MSG__TO_LIST__EXPLANATION,

	// Code when the list name is missing
	OP_MSG__TO_LIST__NAME_IS_MISSING,

	// Code when the list does not exist
	OP_MSG__TO_LIST__LIST_NOT_FOUND,

	// Code for the "opMsg toAll" command ---------------------------------------
	OP_MSG__TO_ALL__EXPLANATION,

	// Code for the "chat" command ----------------------------------------------
	CHAT__ROOT__EXPLANATION,

	// Code for the "chat new" command ------------------------------------------
	CHAT__NEW__EXPLANATION,

	// Code when the chat name is missing
	CHAT__NEW__NAME_IS_MISSING,

	// Code when the chat name is already used
	CHAT__NEW__NAME_ALREADY_USED,

	// Code when the chat color is missing
	CHAT__NEW__COLOR_IS_MISSING,

	// Code when the color does not exist
	CHAT__NEW__COLOR_NOT_FOUND,

	// Code when the chat color is already used
	CHAT__NEW__COLOR_ALREADY_USED,

	// Code when the chat is created
	CHAT__NEW__CHAT_CREATED,

	// Code for the "chat modify" command ---------------------------------------
	CHAT__MODIFY__EXPLANATION,

	// Code for the "chat modify name" command ----------------------------------
	CHAT__MODIFY_NAME__EXPLANATION,

	// Code when the chat name is missing
	CHAT__MODIFY_NAME__NAME_IS_MISSING,

	// Code when the chat name is already used
	CHAT__MODIFY_NAME__NAME_ALREADY_USED,

	// Code when the chat name is updated
	CHAT__MODIFY_NAME__CHAT_NAME_UPDATED,

	// Code for the "chat modify color" command explanation ---------------------
	CHAT__MODIFY_COLOR__EXPLANATION,

	// Code when the new chat color is missing
	CHAT__MODIFY_COLOR__COLOR_IS_MISSING,

	// Code when the new chat color does not exist
	CHAT__MODIFY_COLOR__COLOR_NOT_FOUND,

	// Code when the new chat color is already used
	CHAT__MODIFY_COLOR__COLOR_IS_ALREADY_USED,

	// Code when the chat color is updated
	CHAT__MODIFY_COLOR__CHAT_COLOR_UPDATED,

	// Code for the "chat add" command explanation ------------------------------
	CHAT__ADD_PLAYER__EXPLANATION,

	// Code when the chat does not exist
	CHAT__ADD_PLAYER__CHAT_NOT_FOUND,

	// Code when the player name refers to no player
	CHAT__ADD_PLAYER__PLAYER_NOT_FOUND,

	// Code when the player name refers to a not registered player
	CHAT__ADD_PLAYER__PLAYER_ALREADY_REGISTERED,

	// Code when the player to add is not an operator
	CHAT__ADD_PLAYER__PLAYER_NOT_OPERATOR,

	// Code when no player has been added to a chat
	CHAT__ADD_PLAYER__NO_PLAYER_ADDED,

	// Code when one player has been added to a chat
	CHAT__ADD_PLAYER__ONE_PLAYER_ADDED,

	// Code when one player has been added to a chat
	CHAT__ADD_PLAYER__SEVERAL_PLAYERS_ADDED,

	// Code for the "chat remove" command explanation ---------------------------
	CHAT__REMOVE_PLAYER__EXPLANATION,

	// Code when removing all players from a chat
	CHAT__REMOVE_PLAYER__ALL_PLAYERS_REMOVED,

	// Code when the player name refers to no player
	CHAT__REMOVE_PLAYER__PLAYER_NOT_FOUND,

	// Code when the player name refers to a not registered player
	CHAT__REMOVE_PLAYER__PLAYER_NOT_REGISTERED,

	// Code when no player has been removed from a chat
	CHAT__REMOVE_PLAYER__NO_PLAYER_REMOVED,

	// Code when one player has been removed from a chat
	CHAT__REMOVE_PLAYER__ONE_PLAYER_REMOVED,

	// Code when one player has been removed from a chat
	CHAT__REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED,

	// Code for the "chats" command ---------------------------------------------
	CHATS__ROOT__EXPLANATION,

	// Code for the "chats new" command -----------------------------------------
	CHATS__NEW__EXPLANATION,

	// Code when the chats list name is missing
	CHATS__NEW__NAME_IS_MISSING,

	// Code when the chats list name is already used
	CHATS__NEW__NAME_ALREADY_USED,

	// Code when the chats list is created
	CHATS__NEW__CHATS_CREATED,

	// Code for the "chats add" command -----------------------------------------
	CHATS__ADD__EXPLANATION,

	// Code for the "chats add chat" command ------------------------------------
	CHATS__ADD_CHAT__EXPLANATION,

	// Code when a chat has been added
	CHATS__ADD_CHAT__CHAT_ADDED,

	// Code when the chat does not exist
	CHATS__ADD_PLAYER__CHAT_NOT_FOUND,

	// Code for the "chats remove" command --------------------------------------
	CHATS__REMOVE__EXPLANATION,

	// Code for the "chats remove chat" command ---------------------------------
	CHATS__REMOVE_CHAT__EXPLANATION,

	// Code when all players are removed from a chat
	CHATS__REMOVE_CHAT__ALL_CHATS_REMOVED,

	// Code when the chat does not exist
	CHATS__REMOVE_CHAT__CHAT_NOT_FOUND,

	// Code when no chat has been removed
	CHATS__REMOVE_CHAT__NO_CHAT_REMOVED,

	// Code when one chat has been removed
	CHATS__REMOVE_CHAT__ONE_CHAT_REMOVED,

	// Code when several chats have been removed
	CHATS__REMOVE_CHAT__SEVERAL_CHATS_REMOVED,

	// Code for the "chats remove player" command -------------------------------
	CHATS__REMOVE_PLAYER__EXPLANATION,

	// Code when the player name is missing
	CHATS__REMOVE_PLAYER__NAME_IS_MISSING,

	// Code when the player does not exist
	CHATS__REMOVE_PLAYER__PLAYER_NOT_FOUND,

	// Code when the chat does not exist
	CHATS__REMOVE_PLAYER__CHAT_NOT_FOUND,

	// Code when the player is removed from no chat
	CHATS__REMOVE_PLAYER__PLAYER_NOT_REMOVED,

	// Code when the player is removed from one chat
	CHATS__REMOVE_PLAYER__PLAYER_REMOVED_FROM_ONE_CHAT,

	// Code when the player is removed from one chat
	CHATS__REMOVE_PLAYER__PLAYER_REMOVED_FROM_SEVERAL_CHATS,

	// Code for the "chats modify" command --------------------------------------
	CHATS__MODIFY__EXPLANATION,

	// Code when the chat to modify does not exists
	CHATS__MODIFY__CHAT_NOT_FOUND,

	// Code for the "chats list" command ----------------------------------------
	CHATS__LIST__EXPLANATION,

	// Code when there is no chat registered
	CHATS__LIST__NO_CHAT_REGISTERED,

	// Code when there is one chat registered
	CHATS__LIST__ONE_CHAT_REGISTERED,

	// Code when there is no chat registered
	CHATS__LIST__SEVERAL_CHATS_REGISTERED,

	// Code for the "chats move" command ----------------------------------------
	CHATS__MOVE__EXPLANATION,

	// Code when the name of the player to move is missing
	CHATS__MOVE__PLAYER_NAME_IS_MISSING,

	// Code when the player to move does not exist
	CHATS__MOVE__PLAYER_NOT_FOUND,

	// Code when the player chat name is missing
	CHATS__MOVE__CHAT_NAME_IS_MISSING,

	// Code when the new player chat does not exist
	CHATS__MOVE__CHAT_NOT_FOUND,

	// Code when a player has been move from one chat to another one
	CHATS__MOVE__PLAYER_MOVED_FROM_TO,

	// Code for the "chatConfig" command ----------------------------------------
	CHAT_CONFIG__ROOT__EXPLANATION,

	// Code for the "chatConfig add" command ------------------------------------
	CHAT_CONFIG__ADD__EXPLANATION,

	// Code when a chats list has been added.
	CHAT_CONFIG__ADD__LIST_ADDED,

	// Code for the "chatConfig remove" -----------------------------------------
	CHAT_CONFIG__REMOVE__EXPLANATION,

	// Code when the chats list name to remove is missing
	CHAT_CONFIG__REMOVE__NAME_IS_MISSING,

	// Code when all all chats lists is removed
	CHAT_CONFIG__REMOVE__ALL_CHATS_REMOVED,

	// Code when the chats list does not exist
	CHAT_CONFIG__REMOVE__LIST_NOT_FOUND,

	// Code when no list has been removed
	CHAT_CONFIG__REMOVE__NO_LIST_REMOVED,

	// Code when one list has been removed
	CHAT_CONFIG__REMOVE__ONE_LIST_REMOVED,

	// Code when several lists has been removed
	CHAT_CONFIG__REMOVE__SEVERAL_LISTS_REMOVED,

	// Code for the "chatConfig modify" command ---------------------------------
	CHAT_CONFIG__MODIFY__EXPLANATION,

	// Code when the chats list does not exist
	CHAT_CONFIG__MODIFY__LIST_NOT_FOUND

	;

	private IPlayerGroup group;

	private EChatCode() {
		this(PlayerGroup.OPERATORS);
	}

	private EChatCode(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String value() {
		return name();
	}

	@Override
	public IPlayerGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return String.format("value=%s,group=%s", value(), getGroup());
	}
}
