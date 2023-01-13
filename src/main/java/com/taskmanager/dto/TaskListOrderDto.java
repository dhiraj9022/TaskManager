package com.taskmanager.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskListOrderDto {

	private List<String> lists = new ArrayList<>();

	public List<String> getTaskLists() {
		return lists;
	}

	public void setTaskLists(List<String> lists) {
		this.lists = lists;
	}

}
