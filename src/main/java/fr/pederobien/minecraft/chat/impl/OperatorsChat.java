package fr.pederobien.minecraft.chat.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.pederobien.minecraft.chat.exception.PlayerNotOperatorException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class OperatorsChat extends Chat implements IEventListener {
	private OperatorPlayers players;

	/**
	 * Creates a chat for operators only. When an operator runs the command "./op &lt;playerName&gt;" with a valid player name, then
	 * the player associated to the given player name is automatically added to this chat. In the same way, when an operator runs the
	 * command "./deop &lt;playerName&gt;" with a valid player name, then the player associated to the given name is automatically
	 * removed from this chat.
	 */
	public OperatorsChat() {
		super("operators");

		players = new OperatorPlayers(this);
		EventManager.registerListener(this);
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
			players.getSource().add(player);

		if (command[0].equals("deop"))
			getPlayers().remove(player);
	}

	private class OperatorPlayers implements IPlayerList {
		private IPlayerList players;
		private IChat chat;

		/**
		 * Creates a list of players that only accepts operator players.
		 * 
		 * @param chat The chat associated to this players list.
		 */
		private OperatorPlayers(OperatorsChat chat) {
			this.chat = chat;
			players = new PlayerList(chat.getName());
		}

		@Override
		public Iterator<Player> iterator() {
			return players.iterator();
		}

		@Override
		public String getName() {
			return players.getName();
		}

		@Override
		public void add(Player player) {
			if (!player.isOp())
				throw new PlayerNotOperatorException(chat, player);
			players.add(player);
		}

		@Override
		public Player remove(String name) {
			return players.remove(name);
		}

		@Override
		public boolean remove(Player player) {
			return players.remove(player);
		}

		@Override
		public void clear() {
			players.clear();
		}

		@Override
		public Optional<Player> getPlayer(String name) {
			return players.getPlayer(name);
		}

		@Override
		public Stream<Player> stream() {
			return players.stream();
		}

		@Override
		public List<Player> toList() {
			return players.toList();
		}

		private IPlayerList getSource() {
			return players;
		}
	}
}
