package fr.pederobien.minecraftchat.commands.chatconfig.modify;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EChatConfigModifyLabel implements ILabel {
	NAME("name"), COLOR("color");

	private String label;

	private EChatConfigModifyLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
