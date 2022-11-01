package fr.pederobien.minecraft.chat.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.pederobien.minecraft.chat.exception.PlayerNotOperatorException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.managers.PlayerManager;

public class OperatorsChat extends Chat {
	private IPlayerList players;

	/**
	 * Creates a chat for operators only. When an operator runs the command "./op &lt;playerName&gt;" with a valid player name, then
	 * the player associated to the given player name is automatically added to this chat. In the same way, when an operator runs the
	 * command "./deop &lt;playerName&gt;" with a valid player name, then the player associated to the given name is automatically
	 * removed from this chat.
	 */
	public OperatorsChat() {
		super("Operators");

		players = new OperatorPlayers(this);
	}

	@Override
	public IPlayerList getPlayers() {
		return players;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		String[] command = event.getMessage().substring(1).split(" ");
		if (command.length < 2)
			return;

		Player player = PlayerManager.getPlayer(command[1]);
		if (player == null)
			return;

		if (command[0].equals("op"))
			getPlayers().add(player);

		if (command[0].equals("deop"))
			getPlayers().remove(player);
	}

	private class OperatorPlayers extends PlayerList implements IPlayerList {
		private IChat chat;

		/**
		 * Creates a list of players that only accepts operator players.
		 * 
		 * @param chat The chat associated to this players list.
		 */
		private OperatorPlayers(IChat chat) {
			super(chat.getName());
			this.chat = chat;
			PlayerGroup.OPERATORS.toStream().forEach(player -> add(player));
		}

		@Override
		public void add(Player player) {
			if (!player.isOp())
				throw new PlayerNotOperatorException(chat, player);
			super.add(player);
		}
	}
}
