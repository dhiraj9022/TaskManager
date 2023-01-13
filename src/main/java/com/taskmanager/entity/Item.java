package com.taskmanager.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "text")
	private String text;

	@Column(name = "sequence")
	private int sequence;

	@Column(name = "`timestamp`")
	@CreationTimestamp
	private Date timestamp;

	@ManyToOne
	@JoinColumn(name = "list_id", referencedColumnName = "id")
	@JsonBackReference
	private TaskList taskList;

	public Item() {

	}

	public Item(int id, String text, int sequence, TaskList taskList) {
		super();
		this.id = id;
		this.text = text;
		this.sequence = sequence;
		this.taskList = taskList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

}
