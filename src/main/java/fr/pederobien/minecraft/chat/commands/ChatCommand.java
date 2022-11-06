package fr.pederobien.minecraft.chat.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.chat.exception.PlayerNotRegisteredInChatException;
import fr.pederobien.minecraft.chat.impl.ChatFeature;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.platform.Platform;

public class ChatCommand extends MinecraftCodeNode {

	/**
	 * Creates a node to send a message in a chat.
	 */
	public ChatCommand() {
		super("chat", EChatCode.CHAT__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!(sender instanceof Player))
			return emptyList();

		Player player = (Player) sender;
		IChatConfig config = getChatConfig(player);
		if (config == null || !config.isEnable())
			return emptyList();

		switch (args.length) {
		case 1:
			return filter(config.getChats().getChats(player).stream().map(chat -> chat.getName()), args);
		default:
			Predicate<String> isNameValid = name -> config.getChats().get(name).isPresent();
			return check(args[0], isNameValid, asList(getMessage(sender, EChatCode.CHAT__MESSAGE_COMPLETION)));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;
		IChatConfig config = getChatConfig(player);
		if (config == null) {
			send(eventBuilder(sender, EChatCode.CHAT__NO_CHAT_AVAILABLE).build());
			return false;
		}

		if (!config.isEnable()) {
			send(eventBuilder(sender, EChatCode.CHAT__CHAT_DISABLED).build());
			return false;
		}

		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT__CHAT_NAME_IS_MISSING).build());
			return false;
		}

		String message;
		try {
			message = concat(extract(args, 1), " ");
			if (message == "") {
				send(eventBuilder(sender, EChatCode.CHAT__NO_MESSAGE_TO_SEND).build());
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EChatCode.CHAT__NO_MESSAGE_TO_SEND).build());
			return false;
		}

		if (name.equals(config.getGlobalChat().getName())) {
			config.getGlobalChat().sendMessage(sender, message);
			return true;
		}

		if (name.equals(config.getOperatorChat().getName())) {
			try {
				config.getOperatorChat().sendMessage(sender, message);
				return true;
			} catch (PlayerNotRegisteredInChatException e) {
				send(eventBuilder(sender, EChatCode.CHAT__PLAYER_NOT_REGISTERED, e.getChat().getName()));
				return false;
			}
		}

		Optional<IChat> optChat = config.getChats().get(name);
		if (!optChat.isPresent()) {
			send(eventBuilder(sender, EChatCode.CHAT__CHAT_NOT_FOUND, name));
			return false;
		}

		try {
			optChat.get().sendMessage((Player) sender, message);
		} catch (PlayerNotRegisteredInChatException e) {
			send(eventBuilder(sender, EChatCode.CHAT__PLAYER_NOT_REGISTERED, e.getChat().getName()));
			return false;
		}
		return true;
	}

	/**
	 * Get the chat configuration associated to the given player.
	 * 
	 * @param player The player used to get its chats configuration.
	 * 
	 * @return The chat configuration associated the to given player if registered, null otherwise.
	 */
	private IChatConfig getChatConfig(Player player) {
		Platform platform = Platform.get(player);
		if (platform == null)
			return null;

		if (!(platform.getGame() instanceof IFeatureConfigurable))
			return null;

		IFeatureConfigurable features = (IFeatureConfigurable) platform.getGame();
		Optional<IFeature> optFeature = features.getFeatures().getFeature(ChatFeature.NAME);
		if (!optFeature.isPresent() || !(optFeature.get() instanceof IChatConfig))
			return null;

		return (IChatConfig) optFeature.get();
	}
}
