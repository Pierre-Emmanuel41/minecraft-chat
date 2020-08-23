package fr.pederobien.minecraftchat.commands.remove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftchat.commands.AbstractChatEdition;
import fr.pederobien.minecraftchat.exception.ChatNotRegisteredException;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;

public class RemoveChat<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected RemoveChat() {
		super(EchatConfigRemoveLabel.CHAT, EChatConfigRemoveMessageCode.REMOVE_CHAT__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<IChat> chats = new ArrayList<IChat>();

		if (args[0].equals(IGameConfigurationHelper.ALL)) {
			List<String> chatNames = getChatNames(get().clearChats(), true);
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_CHAT__ALL_CHATS_REMOVED, get().getName(), concat(chatNames, ", "));
			return true;
		}

		String chatNamesConcatenated = null;
		try {
			chats = getChats(args);
			chatNamesConcatenated = concat(getChatNames(chats, true));
			for (IChat chat : chats)
				get().unRegister(chat.getName());
		} catch (ChatNotRegisteredException e) {
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_CHAT__CHAT_DOES_NOT_EXIST, e.getName(), get().getName());
			return false;
		}

		switch (chats.size()) {
		case 0:
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_CHAT__ANY_CHAT_REMOVED, get().getName());
			break;
		case 1:
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_CHAT__ONE_CHAT_REMOVED, chatNamesConcatenated, get().getName());
			break;
		default:
			sendMessageToSender(sender, EChatConfigRemoveMessageCode.REMOVE_CHAT__SEVERAL_CHATS_REMOVED, chatNamesConcatenated, get().getName());
			break;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Stream<String> teams = getFreeChats(Arrays.asList(args)).map(chat -> chat.getName());

		// Adding all to delete all registered teams
		if (args.length == 1)
			return filter(Stream.concat(teams, Stream.of(IGameConfigurationHelper.ALL)), args[0]);

		// If the first argument is all -> any team is proposed
		// Else propose not already mentioned teams
		return filter(args[0].equals(IGameConfigurationHelper.ALL) ? emptyStream() : teams, args[args.length - 1]);
	}
}
