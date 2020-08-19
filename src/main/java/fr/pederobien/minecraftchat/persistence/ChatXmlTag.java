package fr.pederobien.minecraftchat.persistence;

public enum ChatXmlTag {
	NAME("name"), CHATS("chats"), CHAT("chat"), COLOR("color"), PLAYERS("players"), PLAYER("player"), IS_SYNCHRONIZED("isSynchronized");

	private String name;

	private ChatXmlTag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
