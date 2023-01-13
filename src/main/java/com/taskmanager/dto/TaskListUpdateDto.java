package com.taskmanager.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class TaskListUpdateDto {

	private int id;

	private int board_id;

	@NotBlank(message = "Name can't be blank")
	private String name;
	private boolean archived;

	private List<ItemDto> items;

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

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItemDtos(List<ItemDto> items) {
		this.items = items;
	}

}
