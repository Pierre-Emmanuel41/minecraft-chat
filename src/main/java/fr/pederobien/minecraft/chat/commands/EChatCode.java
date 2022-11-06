package fr.pederobien.minecraft.chat.commands;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;

public enum EChatCode implements IMinecraftCode {
	// Common codes -------------------------------------------------------------------------------

	// Code for the "Me" translation
	CHAT__ME,

	// Code for the message completion
	CHAT__MESSAGE_COMPLETION,

	// Code for the name completion
	CHAT__NAME_COMPLETION,

	// Code for the "chat" command ----------------------------------------------------------------
	CHAT__EXPLANATION,

	// Code when there is not chat configuration associated to a player
	CHAT__NO_CHAT_AVAILABLE,

	// Code when the chat configuration is disabled
	CHAT__CHAT_DISABLED,

	// Code when the chat name is missing
	CHAT__CHAT_NAME_IS_MISSING,

	// Code when the message to send is null or empty
	CHAT__NO_MESSAGE_TO_SEND,

	// Code when the player is not registered in a chat
	CHAT__PLAYER_NOT_REGISTERED,

	// Code when a chat is not available for a player
	CHAT__CHAT_NOT_FOUND,

	// Code for the "chatConfig" command ----------------------------------------------------------
	CHAT_CONFIG__EXPLANATION,

	// Code when the chat configuration does not exist
	CHAT_CONFIG__CONFIG_NOT_FOUND,

	// Code when the chat configuration is disabled
	CHAT_CONFIG__CONFIG_DISABLED,

	// Code for the "chatConfig add" command ------------------------------------------------------
	CHAT_CONFIG__ADD__EXPLANATION,

	// Code for the "chatConfig add chat" command -------------------------------------------------
	CHAT_CONFIG__ADD__CHAT__EXPLANATION,

	// Code when the name of the chat to add is missing
	CHAT_CONFIG__ADD__CHAT__CHAT_NAME_IS_MISSING,

	// Code when the chat already exists
	CHAT_CONFIG__ADD__CHAT__CHAT_ALREADY_EXISTS,

	// Code when the chat color name is missing
	CHAT_CONFIG__ADD__CHAT__COLOR_NAME_IS_MISSING,

	// Code when the chat color does not exist
	CHAT_CONFIG__ADD__CHAT__COLOR_NOT_FOUND,

	// Code when the chat color is already used
	CHAT_CONFIG__ADD__CHAT__COLOR_ALREADY_USED,

	// Code when a chat has been added
	CHAT_CONFIG__ADD__CHAT__CHAT_ADDED,

	// Code for the "chatConfig add player" command -----------------------------------------------
	CHAT_CONFIG__ADD__PLAYER__EXPLANATION,

	// Code when chat name is missing
	CHAT_CONFIG__ADD__PLAYER__CHAT_NAME_IS_MISSING,

	// Code when the chat does not exist
	CHAT_CONFIG__ADD__PLAYER__CHAT_NOT_FOUND,

	// Code when the chat players list cannot be modifiable
	CHAT_CONFIG__ADD__PLAYER__PLAYERS_CANNOT_BE_ADDED,

	// Code when the player does not exist
	CHAT_CONFIG__ADD__PLAYER__PLAYER_NOT_FOUND,

	// Code when the player is already registered
	CHAT_CONFIG__ADD__PLAYER__PLAYER_ALREADY_REGISTERED,

	// Code when the player is not an operators
	CHAT_CONFIG__ADD__PLAYER__PLAYER_NOT_OPERATOR,

	// Code when no player has been added to a chat
	CHAT_CONFIG__ADD__PLAYER__NO_PLAYER_ADDED,

	// Code when one player has been added to a chat
	CHAT_CONFIG__ADD__PLAYER__ONE_PLAYER_ADDED,

	// Code when several players have been added to a chat
	CHAT_CONFIG__ADD__PLAYER__SEVERAL_PLAYERS_ADDED,

	// Code for the "chatConfig modify" command ---------------------------------------------------
	CHAT_CONFIG__MODIFY__EXPLANATION,

	// Code for the "chatConfig modify name" command ----------------------------------------------
	CHAT_CONFIG__MODIFY__NAME__EXPLANATION,

	// Code when the chat name is missing
	CHAT_CONFIG__MODIFY__NAME__NAME_IS_MISSING,

	// Code when the chat does not exist
	CHAT_CONFIG__MODIFY__NAME__CHAT_NOT_FOUND,

	// Code when the new chat name is missing
	CHAT_CONFIG__MODIFY__NAME__NEW_NAME_IS_MISSING,

	// Code when the chat associated to the new name already exists
	CHAT_CONFIG__MODIFY__NAME__NEW_NAME_ALREADY_USED,

	// Code when the chat name cannot be modified
	CHAT_CONFIG__MODIFY__NAME__NAME_CANNOT_BE_MODIFIED,

	// Code when the chat has been renamed
	CHAT_CONFIG__MODIFY__NAME__CHAT_RENAMED,

	// Code for the "chatConfig modify color" command ---------------------------------------------
	CHAT_CONFIG__MODIFY__COLOR__EXPLANATION,

	// Code when the chat name is missing
	CHAT_CONFIG__MODIFY__COLOR__NAME_IS_MISSING,

	// Code when the chat does not exist
	CHAT_CONFIG__MODIFY__COLOR__CHAT_NOT_FOUND,

	// Code when the new chat color is missing
	CHAT_CONFIG__MODIFY__COLOR__COLOR_IS_MISSING,

	// Code when the new chat color does not exist
	CHAT_CONFIG__MODIFY__COLOR__COLOR_NOT_FOUND,

	// Code when the new chat color is already used
	CHAT_CONFIG__MODIFY__COLOR__COLOR_ALREADY_USED,

	// Code when the chat color cannot be modified
	CHAT_CONFIG__MODIFY__COLOR__COLOR_CANNOT_BE_MODIFIED,

	// Code when the chat color has been updated
	CHAT_CONFIG__MODIFY__COLOR__COLOR_UPDATED,

	// Code for the "chatConfig list" command -----------------------------------------------------
	CHAT_CONFIG__LIST__EXPLANATION,

	// Code when there is no registered chat
	CHAT_CONFIG__LIST__NO_CHAT,

	// Code when there is one registered chat
	CHAT_CONFIG__LIST__ONE_CHAT,

	// Code when there are several registered chats
	CHAT_CONFIG__LIST__SEVERAL_CHATS,

	// Code for the "chatConfig remove" command ---------------------------------------------------
	CHAT_CONFIG__REMOVE__EXPLANATION,

	// Code for the "chatConfig remove chat" command ----------------------------------------------
	CHAT_CONFIG__REMOVE__CHAT__EXPLANATION,

	// Code when the chat does not exist
	CHAT_CONFIG__REMOVE__CHAT__CHAT_NOT_FOUND,

	// Code when the chat cannot be removed
	CHAT_CONFIG__REMOVE__CHAT__CHAT_CANNOT_BE_REMOVED,

	// Code when no chat has been removed
	CHAT_CONFIG__REMOVE__CHAT__NO_CHAT_REMOVED,

	// Code when one chat has been removed
	CHAT_CONFIG__REMOVE__CHAT__ONE_CHAT_REMOVED,

	// Code when several chats have been removed
	CHAT_CONFIG__REMOVE__CHAT__SEVERAL_CHATS_REMOVED,

	// Code for the "chatConfig remove player" command --------------------------------------------
	CHAT_CONFIG__REMOVE__PLAYER__EXPLANATION,

	// Code when chat name is missing
	CHAT_CONFIG__REMOVE__PLAYER__CHAT_NAME_IS_MISSING,

	// Code when the chat does not exist
	CHAT_CONFIG__REMOVE__PLAYER__CHAT_NOT_FOUND,

	// Code when the chat players list cannot be modifiable
	CHAT_CONFIG__REMOVE__PLAYER__PLAYERS_CANNOT_BE_REMOVED,

	// Code when the player does not exist
	CHAT_CONFIG__REMOVE__PLAYER__PLAYER_NOT_FOUND,

	// Code when the player is not registered
	CHAT_CONFIG__REMOVE__PLAYER__PLAYER_NOT_REGISTERED,

	// Code when no player has been removed to a chat
	CHAT_CONFIG__REMOVE__PLAYER__NO_PLAYER_REMOVED,

	// Code when one player has been removed to a chat
	CHAT_CONFIG__REMOVE__PLAYER__ONE_PLAYER_REMOVED,

	// Code when several players have been removed to a chat
	CHAT_CONFIG__REMOVE__PLAYER__SEVERAL_PLAYERS_REMOVED,

	// Code for the "chatConfig move" command -----------------------------------------------------
	CHAT_CONFIG__MOVE__EXPLANATION,

	// Code when the player name is missing
	CHAT_CONFIG__MOVE__NAME_IS_MISSING,

	// Code when the player does not exist
	CHAT_CONFIG__MOVE__PLAYER_NOT_FOUND,

	// Code when the chat does not exist
	CHAT_CONFIG__MOVE__CHAT_NOT_FOUND,

	// Code when the player is already registered in a chat
	CHAT_CONFIG__MOVE__PLAYER_ALREADY_REGISTERED,

	// Code when the player is not moved from a chat list to another one
	CHAT_CONFIG__MOVE__PLAYER_NOT_MOVED,

	// Code when the player is moved from a chat list to another one
	CHAT_CONFIG__MOVE__PLAYER_MOVED,

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
