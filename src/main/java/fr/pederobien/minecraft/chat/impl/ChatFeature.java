package fr.pederobien.minecraft.chat.impl;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.ChatPlugin;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.game.impl.Feature;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class ChatFeature extends Feature {

	/**
	 * Creates a feature that automatically creates a chat associated to each team of the given game when the game is started and
	 * removes the chats when the game is stopped. This feature should be added to the features list of the game. It also
	 * automatically creates a chhat for operators only.
	 * 
	 * @param game The game associated to this feature.
	 */
	public ChatFeature(IGame game) {
		super("chat", game);
	}

	@Override
	public void start() {
		super.start();

		if (getState() == PausableState.STARTED) {
			if (!(getGame() instanceof ITeamConfigurable))
				return;

			ITeamList teams = ((ITeamConfigurable) getGame()).getTeams();
			IChatList list = new ChatList(getGame().getName());

			IChat operators = new Chat("operators");
			list.add(operators);

			for (ITeam team : teams) {
				IChat chat = new Chat(team.getName());
				chat.setColor(team.getColor());
				for (Player player : team.getPlayers()) {
					chat.getPlayers().add(player);
					if (player.isOp())
						operators.getPlayers().add(player);
				}
				list.add(chat);
			}

			ChatPlugin.getList().add(list);
		}
	}

	@Override
	public void stop() {
		super.stop();

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
