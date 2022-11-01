package fr.pederobien.minecraft.chat.impl;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EventListener;
import fr.pederobien.minecraft.platform.Platform;

public class ChatEventListener extends EventListener {
	private IFeature chatFeature;
	private IChat global;

	/**
	 * Creates an event listener in order to dispatch properly a message to the right chat.
	 */
	public ChatEventListener(IFeature chatFeature) {
		this.chatFeature = chatFeature;
		global = new GlobalChat();
	}

	/**
	 * @return The global chat.
	 */
	public IChat getGlobal() {
		return global;
	}

	@org.bukkit.event.EventHandler
	private void onPlayerChat(AsyncPlayerChatEvent event) {
		if (!chatFeature.isEnable() || !isActivated())
			return;

		if (event.getMessage().startsWith("!"))
			global.sendMessage(event.getPlayer(), event.getMessage().substring(1));
		else {
			Platform platform = Platform.get(event.getPlayer());
			if (platform != null)
				((ITeamConfigurable) platform.getGame()).getTeams().getTeam(event.getPlayer()).get().sendMessage(event.getPlayer(), event.getMessage());
			else
				global.sendMessage(event.getPlayer(), event.getMessage());
		}

		event.setCancelled(true);
	}
}
