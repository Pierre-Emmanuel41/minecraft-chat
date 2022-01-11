package fr.pederobien.minecraft.chat.persistence.loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.w3c.dom.Element;

import fr.pederobien.minecraft.chat.impl.ChatConfiguration;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.observer.IObsPlayerQuitOrJoinEventListener;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecraftmanagers.BukkitManager;
import fr.pederobien.minecraftmanagers.PlayerManager;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistenceLoader;
import fr.pederobien.persistence.interfaces.xml.IXmlPersistenceLoader;

public abstract class AbstractChatLoader extends AbstractXmlPersistenceLoader<IChatConfiguration> implements IObsPlayerQuitOrJoinEventListener {
	private Map<IChat, List<OfflinePlayer>> offlinePlayers;

	protected AbstractChatLoader(Double version) {
		super(version);
		offlinePlayers = new HashMap<IChat, List<OfflinePlayer>>();
	}

	@Override
	public IXmlPersistenceLoader<IChatConfiguration> load(Element root) {
		// At each load, the list of offline player should be reset in order to get the offline players associated to
		// the current configuration.
		offlinePlayers.clear();

		// When a new configuration is loaded, all chat of the old configuration are no more interested by players
		if (get() != null && !get().isSynchronized())
			for (IChat chat : get().getChats())
				Plateform.getPlayerQuitOrJoinEventListener().removeObserver(chat);
		return this;
	}

	@Override
	protected IChatConfiguration create() {
		return new ChatConfiguration("DefaultChatConfiguration");
	}

	@Override
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Iterator<Map.Entry<IChat, List<OfflinePlayer>>> iterator0 = offlinePlayers.entrySet().iterator();
		while (iterator0.hasNext()) {
			Map.Entry<IChat, List<OfflinePlayer>> entry = iterator0.next();
			Iterator<OfflinePlayer> iterator1 = entry.getValue().iterator();
			while (iterator1.hasNext()) {
				OfflinePlayer player = iterator1.next();
				if (player.getName().equals(event.getPlayer().getName())) {
					entry.getKey().add(event.getPlayer());
					iterator1.remove();
				}
			}
			if (entry.getValue().isEmpty())
				iterator0.remove();
		}
		if (offlinePlayers.isEmpty())
			Plateform.getPlayerQuitOrJoinEventListener().removeObserver(this);
	}

	@Override
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

	}

	/**
	 * Appends the player associated to the given name. If the player is offline then it is registered in a pending list in order to
	 * be added later when it connects.
	 * 
	 * @param chat       The chat in which the player should be added.
	 * @param playerName The name of the player to add.
	 */
	protected void addPlayer(IChat chat, String playerName) {
		Player player = PlayerManager.getPlayer(playerName);
		if (player == null) {
			if (offlinePlayers.isEmpty())
				Plateform.getPlayerQuitOrJoinEventListener().addObserver(this);
			for (OfflinePlayer offlinePlayer : BukkitManager.getOfflinePlayers())
				if (offlinePlayer.getName().equals(playerName)) {
					List<OfflinePlayer> chatOfflinePlayers = offlinePlayers.get(chat);
					if (chatOfflinePlayers == null) {
						chatOfflinePlayers = new ArrayList<OfflinePlayer>();
						offlinePlayers.put(chat, chatOfflinePlayers);
					}
					chatOfflinePlayers.add(offlinePlayer);
					return;
				}
		}
		chat.add(player);
	}
}
