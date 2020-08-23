package fr.pederobien.minecraftchat.commands;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EChatConfigLabel implements ILabel {
	ADD("add"), IS_SYNCHRONIZED("isSynchronized"), REMOVE("remove");

	private String label;

	private EChatConfigLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
