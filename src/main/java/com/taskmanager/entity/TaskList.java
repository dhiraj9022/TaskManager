package com.taskmanager.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "lists")
public class TaskList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "archived")
	private boolean archived;

	@Column(name = "sequence")
	private int sequence;

	@Column(name = "`timestamp`")
	@CreationTimestamp
	private Date timestamp;

	@ManyToOne
	@JoinColumn(name = "board_id", referencedColumnName = "id")
	@JsonBackReference
	private Board board;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskList")
	@JsonManagedReference
	private List<Item> items;

	public TaskList() {

	}

	public TaskList(int id, String name, boolean archived, int sequence, Board board, List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.archived = archived;
		this.sequence = sequence;
		this.board = board;
		this.items = items;
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

	public boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}


}
