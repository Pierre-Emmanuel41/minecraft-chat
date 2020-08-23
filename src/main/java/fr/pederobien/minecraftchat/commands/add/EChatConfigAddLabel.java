package fr.pederobien.minecraftchat.commands.add;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EChatConfigAddLabel implements ILabel {
	CHAT("chat"), PLAYER("player");

	private String label;

	private EChatConfigAddLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
