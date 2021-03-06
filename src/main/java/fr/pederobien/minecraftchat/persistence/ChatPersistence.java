package fr.pederobien.minecraftchat.persistence;

import org.bukkit.entity.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.pederobien.minecraftchat.impl.ChatConfiguration;
import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftchat.persistence.loaders.ChatLoaderV10;
import fr.pederobien.minecraftgameplateform.impl.element.persistence.AbstractMinecraftPersistence;
import fr.pederobien.minecraftgameplateform.interfaces.element.persistence.IMinecraftPersistence;
import fr.pederobien.minecraftgameplateform.utils.Plateform;

public class ChatPersistence extends AbstractMinecraftPersistence<IChatConfiguration> {
	private static final String ROOT_XML_DOCUMENT = "chatconf";

	private ChatPersistence() {
		super(Plateform.ROOT.resolve("chats"), "DefaultChatConfiguration");
		register(new ChatLoaderV10());
	}

	public static IMinecraftPersistence<IChatConfiguration> getInstance() {
		return SingletonHolder.PERSISTENCE;
	}

	private static class SingletonHolder {
		private static final IMinecraftPersistence<IChatConfiguration> PERSISTENCE = new ChatPersistence();
	}

	@Override
	public void saveDefault() {
		set(new ChatConfiguration("DefaultChatConfiguration"));
		save();
	}

	@Override
	public boolean save() {
		if (get() == null)
			return false;
		Document doc = newDocument();
		doc.setXmlStandalone(true);

		Element root = createElement(doc, ROOT_XML_DOCUMENT);
		doc.appendChild(root);

		Element version = createElement(doc, VERSION);
		version.appendChild(doc.createTextNode(getVersion().toString()));
		root.appendChild(version);

		Element name = createElement(doc, ChatXmlTag.NAME);
		name.appendChild(doc.createTextNode(get().getName()));
		root.appendChild(name);

		Element isSynchronized = createElement(doc, ChatXmlTag.IS_SYNCHRONIZED);
		isSynchronized.appendChild(doc.createTextNode("" + get().isSynchronized()));
		root.appendChild(isSynchronized);

		Element chats = createElement(doc, ChatXmlTag.CHATS);
		if (!get().isSynchronized())
			for (IChat c : get().getChats()) {
				Element chat = createElement(doc, ChatXmlTag.CHAT);
				setAttribute(chat, ChatXmlTag.NAME, c.getName());
				setAttribute(chat, ChatXmlTag.COLOR, c.getColor());
				Element players = createElement(doc, ChatXmlTag.PLAYERS);
				for (Player p : c.getPlayers()) {
					Element player = createElement(doc, ChatXmlTag.PLAYER);
					setAttribute(player, ChatXmlTag.NAME, p.getName());
					players.appendChild(player);
				}
				chat.appendChild(players);
				chats.appendChild(chat);
			}
		root.appendChild(chats);

		saveDocument(doc, get().getName());
		return true;
	}
}
