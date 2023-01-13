package com.taskmanager.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "boards")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "archived")
	private boolean archived;

	@Column(name = "`timestamp`")
	@CreationTimestamp
	private Date timestamp;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
	@JsonManagedReference
	private List<TaskList> taskLists;

	public Board() {

	}

	public Board(int id, String name, String description, boolean archived, List<TaskList> taskLists) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.archived = archived;
		this.taskLists = taskLists;
	}


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

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<TaskList> getTaskLists() {
		return taskLists;
	}

	public void setTaskLists(List<TaskList> taskLists) {
		this.taskLists = taskLists;
	}



}
