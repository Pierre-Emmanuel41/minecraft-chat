package fr.pederobien.minecraft.chat.commands.chatconfig;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EChatConfigLabel implements ILabel {
	ADD("add"), IS_SYNCHRONIZED("isSynchronized"), REMOVE("remove"), MODIFY("modify");

	private String label;

	private EChatConfigLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
