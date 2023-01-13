package com.taskmanager.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.TaskListDto;
import com.taskmanager.dto.TaskListUpdateDto;
import com.taskmanager.entity.TaskList;
import com.taskmanager.service.TaskListService;

@RestController
@RequestMapping("/api/v1/lists")
public class TaskListController {

	@Autowired
	private TaskListService taskListService;

	@PostMapping
	public TaskList addTaskList(@Valid @RequestBody TaskListDto taskList) {

		return taskListService.addTaskList(taskList);
	}

	@GetMapping
	public Iterable<TaskListDto> getAll() {
		return taskListService.getAll();
	}

	@PutMapping("/{taskList_id}")
	public TaskListUpdateDto updateTaskList(@Valid @RequestBody TaskListUpdateDto taskList,
			@PathVariable int taskList_id) {
		return taskListService.updateTaskList(taskList, taskList_id);
	}


	@PutMapping("/reorder/{board_id}")
	public Iterable<TaskList> reorderTaskList(@RequestBody Map<String, List<Integer>> map,
			@PathVariable(name = "board_id") int id) {
		return taskListService.reorderList(map.get("list"), id);
	}


}
