package fr.pederobien.minecraft.chat.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.chat.interfaces.IChatList;
import fr.pederobien.minecraft.chat.interfaces.ISuperChatList;

public class SuperChatListListAddPostEvent extends SuperChatListEvent {
	private IChatList list;

	/**
	 * Creates an event thrown when a chats list has been added to a super chats list.
	 * 
	 * @param superList The list to which a chats list has been added.
	 * @param list      The added list.
	 */
	public SuperChatListListAddPostEvent(ISuperChatList superList, IChatList list) {
		super(superList);
		this.list = list;
	}

	/**
	 * @return The added list.
	 */
	public IChatList getList() {
		return list;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("superList=" + getSuperList().getName());
		joiner.add("list=" + getList().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
