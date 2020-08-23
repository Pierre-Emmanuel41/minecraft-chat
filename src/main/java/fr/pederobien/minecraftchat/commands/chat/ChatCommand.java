package fr.pederobien.minecraftchat.commands.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraftchat.EChatMessageCode;
import fr.pederobien.minecraftchat.commands.chatconfig.ChatConfigCommand;
import fr.pederobien.minecraftchat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;
import fr.pederobien.minecraftgameplateform.commands.AbstractCommand;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecraftmanagers.BukkitManager;

public class ChatCommand extends AbstractCommand {
	private ChatConfigCommand chatConfigCommand;

	public ChatCommand(JavaPlugin plugin, ChatConfigCommand chatConfigCommand) {
		super(plugin, "chat");
		getPlugin().getCommand(getLabel()).setExecutor(this);
		getPlugin().getCommand(getLabel()).setTabCompleter(getTabCompleter());
		this.chatConfigCommand = chatConfigCommand;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			IChatConfiguration configuration = getConfiguration();
			if (configuration == null) {
				sendMessage((Player) sender, EChatMessageCode.CHAT__NO_EXISTING_CONFIGURATION);
				return true;
			}
			try {
				Optional<IChat> optChat = configuration.getChat(args[0]);
				if (!optChat.isPresent()) {
					sendMessage((Player) sender, EChatMessageCode.CHAT__CHAT_NOT_REGISTERED, args[0], configuration.getName());
					return false;
				}
				try {
					optChat.get().sendMessage((Player) sender, concat(args));
				} catch (PlayerNotRegisteredInChatException e) {
					sendMessage((Player) sender, EChatMessageCode.CHAT__PLAYER_NOT_REGISTERED, optChat.get().getName());
					return false;
				}
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		} else
			BukkitManager.broadcastMessage(concat(args));
		return true;
	}

	@Override
	public TabCompleter getTabCompleter() {
		return new Completer();
	}

	private String concat(String[] args) {
		StringJoiner joiner = new StringJoiner(" ");
		for (int i = 1; i < args.length; i++)
			joiner.add(args[i]);
		return joiner.toString();
	}

	private IChatConfiguration getConfiguration() {
		return chatConfigCommand.getParent().get();
	}

	private void sendMessage(Player sender, IMinecraftMessageCode code, Object... args) {
		Plateform.getNotificationCenter().sendMessage(new MinecraftMessageEvent(sender, code, args));
	}

	private class Completer implements TabCompleter {

		@Override
		public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
			IChatConfiguration conf = getConfiguration();
			if (conf == null || args.length > 1)
				return new ArrayList<String>();

			List<String> chatNames = new ArrayList<String>();
			for (IChat chat : conf.getChats())
				if (chat.getPlayers().contains((Player) sender))
					chatNames.add(chat.getName());
			return chatNames.stream().filter(str -> str.contains(args[args.length - 1])).collect(Collectors.toList());
		}
	}
}
