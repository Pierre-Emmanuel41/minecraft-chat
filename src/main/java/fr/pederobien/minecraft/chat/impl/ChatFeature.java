package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.game.impl.Feature;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EventListener;

public class ChatFeature extends Feature implements IChatConfig {
	/**
	 * The name of a chat feature.
	 */
	public static final String NAME = "chat";

	private IChat globalChat;
	private IChat operatorsChat;
	private IChatList chats;
	private EventListener chatEventListener;

	/**
	 * Creates a feature that automatically creates a chat associated to each team of the given game when the game is started and
	 * removes the chats when the game is stopped. This feature should be added to the features list of the game. It also
	 * automatically creates a chat for operators only.
	 * 
	 * @param game The game associated to this feature.
	 */
	public ChatFeature(IGame game) {
		super(NAME, game);
		globalChat = new GlobalChat();
		operatorsChat = new OperatorsChat();
		chats = new ChatList(game.getName());
		chatEventListener = new ChatEventListener(this);
	}

	@Override
	public void start() {
		super.start();

		if (getState() == PausableState.STARTED) {
			if (!(getGame() instanceof ITeamConfigurable))
				return;

			chats = new SynchronizedChatList(((ITeamConfigurable) getGame()).getTeams());
			chats.add(globalChat);
			chats.add(operatorsChat);
			chatEventListener.register(getGame().getPlugin());
			chatEventListener.setActivated(true);
		}
	}

	@Override
	public void stop() {
		super.stop();

		chats.clear();
		chatEventListener.setActivated(false);
	}

	@Override
	public void setEnabled(boolean isEnable) {
		super.setEnabled(isEnable);
		if (isEnable && (getGame().getState() == PausableState.STARTED || getGame().getState() == PausableState.PAUSED))
			start();
		else if (!isEnable)
			stop();
	}

	@Override
	public IChat getGlobalChat() {
		return globalChat;
	}

	@Override
	public IChat getOperatorChat() {
		return operatorsChat;
	}

	@Override
	public IChatList getChats() {
		return chats;
	}
}
