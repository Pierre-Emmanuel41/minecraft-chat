package fr.pederobien.minecraft.chat.impl;

import java.util.StringJoiner;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.event.ChatNameChangePostEvent;
import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.impl.PlayerList;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.utils.event.EventManager;

public class Chat implements IChat, ICodeSender {
	private String name;
	private EColor color;
	private IPlayerList players;

	/**
	 * Creates a chat associated to the given name.
	 * 
	 * @param name The chat name.
	 */
	public Chat(String name) {
		this.name = name;
		players = new PlayerList(name);
		color = EColor.RESET;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (this.name.equals(name))
			return;

		String oldName = this.name;
		this.name = name;
		EventManager.callEvent(new ChatNameChangePostEvent(this, oldName));
	}

	@Override
	public String getColoredName() {
		return getColor().getInColor(getName());
	}

	@Override
	public String getColoredName(EColor next) {
		return getColor().getInColor(getName(), next);
	}

	@Override
	public EColor getColor() {
		return color;
	}

	@Override
	public void setColor(EColor color) {
		this.color = color;
	}

	@Override
	public IPlayerList getPlayers() {
		return players;
	}

	@Override
	public void sendMessage(CommandSender sender, String message) {
		checkPlayer(sender);
		for (Player player : players.toList())
			MessageManager.sendMessage(player, getPrefix(sender, player) + message);
	}

	@Override
	public void sendMessage(CommandSender sender, IMinecraftCode code, Object... args) {
		checkPlayer(sender);
		for (Player player : players)
			MessageManager.sendMessage(player, getPrefix(sender, player) + getMessage(player, code, args));
	}

	@Override
	public String toString() {
		StringJoiner players = new StringJoiner(" ", "[", "]");
		for (Player player : getPlayers())
			players.add(player.getName());
		return getColor().getInColor(getName() + " " + players.toString());
	}

	private void checkPlayer(CommandSender sender) {
		if (!(sender instanceof Player))
			return;

		if (!players.toList().contains(sender))
			throw new PlayerNotRegisteredInChatException(this, (Player) sender);
	}

	private String getPrefix(CommandSender sender, CommandSender player) {
		String senderName = null;
		if (!(sender instanceof Player))
			senderName = getMessage(sender, EChatCode.CHAT__OPERATOR);
		else
			senderName = player.equals(sender) ? getMessage(sender, EChatCode.CHAT__ME) : sender.getName();
		return color.getInColor(String.format("[%s -> %s] ", senderName, getName()));
	}
}
