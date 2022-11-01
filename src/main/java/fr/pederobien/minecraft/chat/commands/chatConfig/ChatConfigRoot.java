package fr.pederobien.minecraft.chat.commands.chatConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.chat.commands.EChatCode;
import fr.pederobien.minecraft.chat.impl.ChatFeature;
import fr.pederobien.minecraft.chat.interfaces.IChatConfig;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class ChatConfigRoot extends MinecraftCodeRootNode implements IEventListener {
	private ChatConfigCommandTree tree;
	private Map<String, IChatConfig> configs;

	/**
	 * Creates a chat configuration root for a {@link ChatConfigCommandTree}
	 */
	public ChatConfigRoot(ChatConfigCommandTree tree) {
		super("chatConfig", EChatCode.CHAT_CONFIG__EXPLANATION, () -> true);
		this.tree = tree;

		configs = new HashMap<String, IChatConfig>();

		EventManager.registerListener(this);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(configs.keySet().stream(), args);
		default:
			IChatConfig config = configs.get(args[0]);
			if (config == null)
				return emptyList();

			tree.setConfig(config);
			return super.onTabComplete(sender, command, alias, extract(args, 1));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		IChatConfig config = configs.get(name);
		if (config == null) {
			send(eventBuilder(sender, EChatCode.CHAT_CONFIG__CONFIG_NOT_FOUND, name));
			return false;
		}

		tree.setConfig(config);
		return super.onCommand(sender, command, label, extract(args, 1));
	}

	@EventHandler
	private void onGameStart(GameStartPostEvent event) {
		Platform platform = Platform.get(event.getGame());
		if (platform == null)
			return;

		if (!(platform.getGame() instanceof IFeatureConfigurable))
			return;

		Optional<IFeature> optFeature = ((IFeatureConfigurable) platform.getGame()).getFeatures().getFeature(ChatFeature.NAME);
		if (!optFeature.isPresent() || !(optFeature.get() instanceof IChatConfig))
			return;

		configs.put(event.getGame().getName(), (IChatConfig) optFeature.get());
	}

	@EventHandler
	private void onGameStop(GameStopPostEvent event) {
		configs.remove(event.getName());
	}
}
