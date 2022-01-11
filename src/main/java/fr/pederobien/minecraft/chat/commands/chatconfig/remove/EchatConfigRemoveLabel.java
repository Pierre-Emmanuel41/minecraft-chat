package fr.pederobien.minecraft.chat.commands.chatconfig.remove;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EchatConfigRemoveLabel implements ILabel {
	CHAT("chat"), PLAYER("player");

	private String label;

	private EchatConfigRemoveLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
