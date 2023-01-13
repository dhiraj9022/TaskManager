package com.taskmanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.ItemDto;
import com.taskmanager.dto.TaskListDto;
import com.taskmanager.dto.TaskListUpdateDto;
import com.taskmanager.entity.Board;
import com.taskmanager.entity.TaskList;
import com.taskmanager.exception.AlreadyExistsException;
import com.taskmanager.exception.NotFoundException;
import com.taskmanager.repo.BoardRepo;
import com.taskmanager.repo.TaskListRepo;

@Service
public class TaskListService {

	@Autowired
	private TaskListRepo taskListRepo;

	@Autowired
	private BoardRepo boardRepo;

	@Autowired
	private BoardService boardService;

	public TaskList addTaskList(@Valid TaskListDto taskListDto) {

		Board board = boardRepo.findById(taskListDto.getBoard_id())
				.orElseThrow(() -> new NotFoundException("Id not found"));

		TaskList taskList = new TaskList();
		taskList.setName(taskListDto.getName());
		if ((taskListRepo.findBySequenceAndBoard(taskListDto.getSequence(), board) != null)) {
			throw new AlreadyExistsException("Sequence " + taskListDto.getSequence() + " already present ");
		}
		taskList.setSequence(taskListDto.getSequence());
		taskList.setBoard(boardService.findById(taskListDto.getBoard_id()));
		return taskListRepo.save(taskList);
	}

	public Iterable<TaskListDto> getAll() {

		List<TaskListDto> taskListDtos = new ArrayList<>();

		taskListRepo.findByOrderBySequence().forEach(taskList -> {

			TaskListDto taskListDto = new TaskListDto();
			taskListDto.setId(taskList.getId());
			taskListDto.setName(taskList.getName());
			taskListDto.setSequence(taskList.getSequence());
			taskListDto.setBoard_id(taskList.getBoard().getId());
			taskListDto.setArchived(taskList.getArchived());

			List<ItemDto> itemDtos = new ArrayList<>();

			taskList.getItems().forEach(itemList -> {
				ItemDto itemDto = new ItemDto();
				itemDto.setId(itemList.getId());
				itemDto.setSequence(itemList.getSequence());
				itemDto.setList_id(itemList.getTaskList().getId());
				itemDto.setText(itemList.getText());
				itemDtos.add(itemDto);
			});
			taskListDto.setItemDtos(itemDtos);
			taskListDtos.add(taskListDto);
		});
		return taskListDtos;
	}

	public TaskListUpdateDto updateTaskList(@Valid TaskListUpdateDto taskListUpdateDto, int taskId) {
		TaskList newTaskList = findById(taskId);

		newTaskList.setName(taskListUpdateDto.getName());
		newTaskList.setBoard(boardService.findById(taskListUpdateDto.getBoard_id()));
		newTaskList.setArchived(taskListUpdateDto.getArchived());

		return mapToDto(taskListRepo.save(newTaskList));
	}

	public TaskListUpdateDto mapToDto(TaskList taskList) {
		TaskListUpdateDto taskListDto = new TaskListUpdateDto();

		taskListDto.setBoard_id(taskList.getBoard().getId());
		taskListDto.setName(taskList.getName());
		taskListDto.setId(taskList.getId());
		taskListDto.setArchived(taskList.getArchived());
		return taskListDto;
	}

	public TaskList findById(int id) {
		return taskListRepo.findById(id).orElseThrow(() -> new NotFoundException("Id not found"));

	}

	public Iterable<TaskList> reorderList(List<Integer> listIds, int id) {
		List<TaskList> lists = new ArrayList<>();

		for (int i = 0; i < listIds.size(); i++) {

			TaskList taskList = taskListRepo.findById(listIds.get(i)).get();
			taskList.setSequence(i + 1);
			lists.add(taskList);
		}

		return taskListRepo.saveAll(lists);
	}
}
