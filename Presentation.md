# Presentation

### 1) Model

A chat is modelled by the <code>IChat</code> interface whose the properties are :

```java
public interface IChat extends INominable {

	/**
	 * @return The name of this chat using {@link EColor#getInColor(String)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName();

	/**
	 * Get the name of this team, in the team color and specify which color to use after?
	 * 
	 * @param next The color after to used after.
	 * 
	 * @return The name of this team using {@link EColor#getInColor(String, EColor)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName(EColor next);

	/**
	 * @return The chat color
	 */
	EColor getColor();

	/**
	 * Set the chat color.
	 * 
	 * @param color The new chat color.
	 */
	void setColor(EColor color);

	/**
	 * @return The list of players associated to this chat.
	 */
	IPlayerList getPlayers();

	/**
	 * Send the given message to each player registered in this room.
	 * 
	 * @param sender     The player who send the message.
	 * @param isOperator True if the sender should be considered as an operator, false otherwise.
	 * @param message    The message to send to the chat.
	 * 
	 * @throws PlayerNotRegisteredInChatException If the sender is not registered in this chat.
	 */
	void sendMessage(CommandSender sender, boolean isOperator, String message);

	/**
	 * For each player in this team, send the message associated to the given code.
	 * 
	 * @param sender     The player who send the message to the team.
	 * @param isOperator True if the sender should be considered as an operator, false otherwise.
	 * @param code       Used as key to get the right message in the right dictionary.
	 * @param args       Some arguments (optional) used for dynamic messages.
	 * 
	 * @throws PlayerNotRegisteredInChatException If the sender is not registered in this chat.
	 */
	void sendMessage(CommandSender sender, boolean isOperator, IMinecraftCode code, Object... args);
}
```

A chat is generally associated to a chats list :

```java
public interface IChatList extends Iterable<IChat> {

	/**
	 * @return The name of this list
	 */
	String getName();

	/**
	 * Adds the given chat to this list.
	 * 
	 * @param chat The chat to add.
	 * 
	 * @throws ChatAlreadyRegisteredException if a chat with the given name is already registered in this configuration.
	 */
	void add(IChat chat);

	/**
	 * Removes the chat associated to the given name.
	 * 
	 * @param name The chat name to remove.
	 * 
	 * @return The removed chat if registered, null otherwise.
	 */
	IChat remove(String name);

	/**
	 * Removes the given chat from this list.
	 * 
	 * @param chat The chat to remove.
	 * 
	 * @return True if the chat was registered, false otherwise.
	 */
	boolean remove(IChat chat);

	/**
	 * Removes the chat associated to the given color.
	 * 
	 * @param color The color used to remove the associated chat.
	 * 
	 * @return The removed chat if registered, null otherwise.
	 */
	IChat remove(EColor color);

	/**
	 * Removes all registered chats. It also clears the list of players.
	 */
	void clear();

	/**
	 * Get the chat associated to the given name.
	 * 
	 * @param name The name of the chat to retrieve.
	 * 
	 * @return An optional that contains the chat associated to the given name if registered, an empty optional otherwise.
	 */
	Optional<IChat> getChat(String name);

	/**
	 * Get the chat associated to the given color.
	 * 
	 * @param color The color of the chat to retrieve.
	 * 
	 * @return An optional that contains the chat associated to the given color if registered, an empty optional otherwise.
	 */
	Optional<IChat> getChat(EColor color);

	/**
	 * Get the list of chat in which the given player is registered.
	 * 
	 * @param player The player used to get its chats.
	 * 
	 * @return The list of chat in which the given player is registered.
	 */
	List<IChat> getChats(Player player);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<IChat> stream();

	/**
	 * @return The list of registered chats. This list is unmodifiable.
	 */
	List<IChat> toList();

	/**
	 * Appends or moves the specified player in the given chat. If the player was registered in one or several chats, then the players
	 * is removed from them.
	 * 
	 * @param player The player to move.
	 * @param chats  The new player chats.
	 * 
	 * @return The list of chats in which the player was registered, an empty list otherwise.
	 */
	List<IChat> movePlayer(Player player, IChat... chats);
}
```

Finally, because it may be possible that several games are lunched at the same time on the same server, a super list of chats gather all chats list associated to each running game:

```java
public interface ISuperChatList extends Iterable<IChatList> {

	/**
	 * @return The name of this list
	 */
	String getName();

	/**
	 * Adds the given chats list to this list.
	 * 
	 * @param list The list of chats to add.
	 * 
	 * @throws ChatListAlreadyRegisteredException If a list is already registered for the list name.
	 */
	void add(IChatList list);

	/**
	 * Removes the chats list associated to the given name.
	 * 
	 * @param name The chats list name to remove.
	 * 
	 * @return The removed list if registered, null otherwise.
	 */
	IChatList remove(String name);

	/**
	 * Removes the given chats list from this list.
	 * 
	 * @param list The chats list to remove.
	 * 
	 * @return True if the list was registered, false otherwise.
	 */
	boolean remove(IChatList list);

	/**
	 * Removes all registered chats list.
	 */
	void clear();

	/**
	 * Get the chats list associated to the given name.
	 * 
	 * @param name The name of the chats list to retrieve.
	 * 
	 * @return An optional that contains the list associated to the given name if registered, an empty optional otherwise.
	 */
	Optional<IChatList> getChats(String name);

	/**
	 * Get the list of chats in which a player is registered.
	 * 
	 * @param player The player used to get the list of chat in which it is registered.
	 * 
	 * @return The list of chat in which the player is registered.
	 */
	List<IChat> getChats(Player player);

	/**
	 * Get the chat associated to the given name and whose the player list contains the specified player.
	 * 
	 * @param name   The chat name.
	 * @param player The player the chat should contains.
	 * 
	 * @return The chat associated to the given name and that contains a the specified player, or null.
	 */
	Optional<IChat> getChat(String name, Player player);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<IChatList> stream();

	/**
	 * @return A copy of the underlying list.
	 */
	List<IChatList> toList();

}
```

Moreover, a [ChatFeature](https://github.com/Pierre-Emmanuel41/minecraft-chat/blob/1.0_MC_1.13.2-SNAPSHOT/src/main/java/fr/pederobien/minecraft/chat/impl/ChatFeature.java) has been implemented. When a game starts, this feature, when enabled, creates automatically a chats list corresponding to the teams list. It also creates a chat for operators only. When the game is stopped, all chats are removed including the operators chat.

### 2) Minecraft features

As a minecraft player, there is several commands proposed by this plugin : "./chatconfig", "./opMsg" for operators and"./chatMsg" for all players (including operators). The first two commands allows server operators to create several chats according to their needs whereas the last one is used mainly to send a message to team mates.

<code>chatconfig</code> (minecraft command)</br>
&ensp;<code>add</code> - To add a chats list to the super list of chats.</br>
&ensp;<code>modify</code> - To modify a list of chats.</br>
&ensp;&ensp;<code>add</code> - To add chats to a list of chats or players to a chat.</br>
&ensp;&ensp;&ensp;<code>chat</code> - To add chats to a list.</br>
&ensp;&ensp;&ensp;<code>player</code> - To add players to a chat.</br>
&ensp;&ensp;<code>modify</code> - To modify the characteristics of a chat.</br>
&ensp;&ensp;&ensp;<code>color</code> - To modify the color of a chat.</br>
&ensp;&ensp;&ensp;<code>name</code> - To modify the name of a chat.</br>
&ensp;&ensp;<code>remove</code> - To remove chats from a list of chats or players from a chat.</br>
&ensp;&ensp;&ensp;<code>chat</code> - To remove chats from a list of chats.</br>
&ensp;&ensp;&ensp;<code>player</code> - To remove players from a chat.</br>
&ensp;<code>remove</code> - To remove a chats list from the super list of chats.</br>
</br>
<code>opMsg</code> (minecraft command)</br>
&ensp;<code>toChat</code> - To send a message to a chat.</br>
&ensp;<code>toList</code> - To send a message to each chat from a chats list.</br>
&ensp;<code>all</code> - To send a message to each chat from each chats list.</br>

The <code>chatMsg</code> command does not have pre registered arguments. To run this command, players only run the following argument line:

```
./chatmsg <chatName> <message>
```

### 3) Developers features

The <code>chatConfig</code> command is modelled by the <code>SuperChatsCommandTree</code> class and is available from the <code>ChatPlugin</code> class as static attribute. The root of this tree is set as TabCompleter and CommandExecutor for the minecraft command of the same name.  

The <code>opMsg</code> command is modelled by the <code>OpMsgCommandTree</code> class and is available from the <code>ChatPlugin</code> class as static attribute. The root of this tree is set as TabCompleter and CommandExecutor for the minecraft command of the same name.  

The <code>chatMsg</code> command is modelled by the <code>ChatsMsgNode</code> class and is available from the <code>ChatPlugin</code> class as static attribute. The root of this tree is set as TabCompleter and CommandExecutor for the minecraft command of the same name.

The package <code>chat.commands</code> provides several classes in order to create/modify a simple chat, to create/modify a chat list and to create/modify a super list of chats.