package fr.pederobien.minecraftchat.persistence.loaders;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pederobien.minecraftchat.interfaces.IChat;
import fr.pederobien.minecraftchat.interfaces.IChatConfiguration;
import fr.pederobien.minecraftchat.persistence.ChatXmlTag;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.persistence.interfaces.xml.IXmlPersistenceLoader;

public class ChatLoaderV10 extends AbstractChatLoader {

	public ChatLoaderV10() {
		super(1.0);
	}

	@Override
	public IXmlPersistenceLoader<IChatConfiguration> load(Element root) {
		super.load(root);
		createNewElement();

		// Getting configuration's name
		Node name = getElementsByTagName(root, ChatXmlTag.NAME).item(0);
		get().setName(name.getChildNodes().item(0).getNodeValue());

		// Getting configuration's synchronization state
		Node isSynchronized = getElementsByTagName(root, ChatXmlTag.IS_SYNCHRONIZED).item(0);
		get().setIsSynchronized(getBooleanNodeValue(isSynchronized.getChildNodes().item(0)));

		// Do nothing if synchronized
		if (get().isSynchronized())
			return this;

		// Getting configuration's chats
		NodeList chats = getElementsByTagName(root, ChatXmlTag.CHAT);
		for (int i = 0; i < chats.getLength(); i++) {
			Element c = (Element) chats.item(i);
			IChat chat = get().register(getStringAttribute(c, ChatXmlTag.NAME), EColor.getByColorName(getStringAttribute(c, ChatXmlTag.COLOR)));
			NodeList players = getElementsByTagName(c, ChatXmlTag.PLAYER);
			for (int j = 0; j < players.getLength(); j++)
				addPlayer(chat, getStringAttribute((Element) players.item(j), ChatXmlTag.NAME));
		}
		return this;
	}
}
