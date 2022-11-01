package fr.pederobien.minecraft.chat.impl;

import fr.pederobien.minecraft.chat.ChatPlugin;
import fr.pederobien.minecraft.game.impl.Feature;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EventListener;

public class ChatFeature extends Feature {
	private EventListener chatEventListener;

	/**
	 * Creates a feature that automatically creates a chat associated to each team of the given game when the game is started and
	 * removes the chats when the game is stopped. This feature should be added to the features list of the game. It also
	 * automatically creates a chat for operators only.
	 * 
	 * @param game The game associated to this feature.
	 */
	public ChatFeature(IGame game) {
		super("chat", game);
		chatEventListener = new ChatEventListener(this);
	}

	@Override
	public void start() {
		super.start();

		if (getState() == PausableState.STARTED) {
			if (!(getGame() instanceof ITeamConfigurable))
				return;

			chatEventListener.register(getGame().getPlugin());
			chatEventListener.setActivated(true);
			ChatPlugin.getList().add(new SynchronizedChatList(((ITeamConfigurable) getGame()).getTeams()));
		}
	}

	@Override
	public void stop() {
		super.stop();

		chatEventListener.setActivated(false);
		ChatPlugin.getList().remove(getGame().getName());
	}

	@Override
	public void setEnabled(boolean isEnable) {
		super.setEnabled(isEnable);
		if (isEnable && (getGame().getState() == PausableState.STARTED || getGame().getState() == PausableState.PAUSED))
			start();
		else if (!isEnable)
			stop();
	}
}
