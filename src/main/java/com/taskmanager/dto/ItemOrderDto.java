package com.taskmanager.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderDto {

	private List<String> itemList = new ArrayList<>();

	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

}
