package com.udacity.jwdnd.course1.cloudstorage.entity;

public class DeleteItem {

	private int itemId;
	private String itemType;
	
	public DeleteItem() {
	}

	public DeleteItem(int itemId, String itemType) {
		this.itemId = itemId;
		this.itemType = itemType;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return "DeleteItem [itemId=" + itemId + ", itemType=" + itemType + "]";
	}
}
