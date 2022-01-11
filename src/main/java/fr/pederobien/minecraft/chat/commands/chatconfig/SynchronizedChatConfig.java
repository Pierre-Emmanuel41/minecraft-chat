package fr.pederobien.minecraft.chat.commands.chatconfig;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.EChatMessageCode;
import fr.pederobien.minecraft.chat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractLabelEdition;

public class SynchronizedChatConfig<T extends IChatConfiguration> extends AbstractLabelEdition<T> {

	protected SynchronizedChatConfig() {
		super(EChatConfigLabel.IS_SYNCHRONIZED, EChatMessageCode.IS_CHAT_CONFIG_SYNCHRONIZED__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			String value = args[0];
			if (value.equals("true")) {
				get().setIsSynchronized(true);
				sendSynchro(sender, EChatMessageCode.IS_CHAT_CONFIG_SYNCHRONIZED__TRUE, get().getName());
			} else if (value.equals("false")) {
				get().setIsSynchronized(false);
				sendSynchro(sender, EChatMessageCode.IS_CHAT_CONFIG_SYNCHRONIZED__FALSE, get().getName());
			} else {
				sendSynchro(sender, ECommonMessageCode.COMMON_BAD_BOOLEAN_FORMAT);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EChatMessageCode.IS_CHAT_CONFIG_SYNCHRONIZED__VALUE_IS_MISSING);
			return false;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(asList("true", "false").stream(), args);
		default:
			return emptyList();
		}
	}
}
