package fr.pederobien.minecraft.chat.commands.chatconfig.modify;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.chatconfig.AbstractChatEdition;
import fr.pederobien.minecraft.chat.interfaces.IChat;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;

public class ModifyName<T extends IChatConfiguration> extends AbstractChatEdition<T> {

	protected ModifyName() {
		super(EChatConfigModifyLabel.NAME, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String oldName = "";
		try {
			oldName = args[0];
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__OLD_NAME_IS_MISSING);
			return false;
		}
		String newName = "";
		try {
			newName = args[1];
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__NEW_NAME_IS_MISSING, oldName);
			return false;
		}

		Optional<IChat> optChat = get().getChat(oldName);
		if (!optChat.isPresent()) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__CHAT_DOES_NOT_EXIST, oldName, get().getName());
			return false;
		}

		IChat chat = optChat.get();

		if (newName.equals(IGameConfigurationHelper.ALL)) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__CHAT_NAME_FORBIDDEN, oldName, IGameConfigurationHelper.ALL);
			return false;
		}

		if (get().getChat(newName).isPresent()) {
			sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__CHAT_NAME_ALREADY_USED, oldName, newName);
			return false;
		}
		chat.setName(newName);
		sendSynchro(sender, EChatConfigModifyMessageCode.CHAT_MODIFY_NAME__CHAT_RENAMED, chat.getColor().getInColor(oldName), chat.getColoredName());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getChatNames(get().getChats(), false).stream(), args);
		case 2:
			return asList(getMessage(sender, ECommonMessageCode.COMMON_RENAME_TAB_COMPLETE));
		default:
			return emptyList();
		}
	}
}
