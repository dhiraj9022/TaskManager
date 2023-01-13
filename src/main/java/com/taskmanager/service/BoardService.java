package com.taskmanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.BoardDto;
import com.taskmanager.dto.ItemDto;
import com.taskmanager.dto.TaskListDto;
import com.taskmanager.entity.Board;
import com.taskmanager.exception.NotFoundException;
import com.taskmanager.repo.BoardRepo;

@Service
public class BoardService {

	@Autowired
	private BoardRepo boardRepo;

	public Board addBoard(@Valid BoardDto board) {
		Board boards = new Board();

		// don't send board id while create when auto increment id
		// boards.setId(board.getId());

		boards.setName(board.getName());
		boards.setDescription(board.getDescription());
		boards.setArchived(board.getArchived());
		return boardRepo.save(boards);
	}

	public Iterable<BoardDto> getAll() {

		List<BoardDto> boardDtos = new ArrayList<>();

		boardRepo.findAll().forEach(newboard -> {
			BoardDto boardDto = new BoardDto();
			boardDto.setId(newboard.getId());
			boardDto.setName(newboard.getName());
			boardDto.setDescription(newboard.getDescription());
			boardDto.setArchived(newboard.getArchived());

			List<TaskListDto> dtos = new ArrayList<>();

			newboard.getTaskLists().forEach(taskList -> {
				TaskListDto dto = new TaskListDto();
				dto.setBoard_id(taskList.getBoard().getId());
				dto.setName(taskList.getName());
				dto.setId(taskList.getId());
				dto.setSequence(taskList.getSequence());
				dto.setArchived(taskList.getArchived());

				List<ItemDto> items = new ArrayList<>();

				taskList.getItems().forEach(itemList -> {
					ItemDto itemDto = new ItemDto();
					itemDto.setId(itemList.getId());
					itemDto.setList_id(itemList.getTaskList().getId());
					itemDto.setText(itemList.getText());
					itemDto.setSequence(itemList.getSequence());
					items.add(itemDto);
				});

				dto.setItemDtos(items);
				dtos.add(dto);
			});

			boardDto.setLists(dtos);
			boardDtos.add(boardDto);
		});
		return boardDtos;
	}

	public Board updateBoard(@Valid BoardDto boardDto, int boardId) {
		// getting board from DB
		Board board = findById(boardId);

		// updating new data
		board.setName(boardDto.getName());
		board.setDescription(boardDto.getDescription());
		board.setArchived(boardDto.getArchived());

		// saving
		return boardRepo.save(board);
	}

	public Board findById(int id) {
		return boardRepo.findById(id).orElseThrow(() -> new NotFoundException("Id not found"));
	}

	public Board getBoard(Integer id) {
		// reusing code
		return findById(id);
	}
}
