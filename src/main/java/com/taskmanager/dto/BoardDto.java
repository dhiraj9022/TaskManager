package com.taskmanager.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BoardDto {

	private int id;

	@NotBlank(message = "Name can't be blank")
	private String name;

	@NotBlank(message = "Desc can't be blank")
	@Size(min = 1, max = 50)
	private String description;

	private boolean archived;
	List<TaskListDto> lists = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TaskListDto> getLists() {
		return lists;
	}

	public void setLists(List<TaskListDto> lists) {
		this.lists = lists;
	}

	public boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}


}
