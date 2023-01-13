package com.taskmanager.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ItemDto {

	private int id;
	private int list_id;

	@NotBlank(message = "Text can't be blank")
	@Size(min = 1, max = 50)
	private String text;

	@Min(value = 1, message = "Sequence minimum value must be 1")
	private int sequence;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getList_id() {
		return list_id;
	}

	public void setList_id(int list_id) {
		this.list_id = list_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
