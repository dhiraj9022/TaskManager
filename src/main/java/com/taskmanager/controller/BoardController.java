package com.taskmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.BoardDto;
import com.taskmanager.entity.Board;
import com.taskmanager.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@PostMapping
	public Board addBoard(@Valid @RequestBody BoardDto board) {
		return boardService.addBoard(board);
	}

	@GetMapping
	public Iterable<BoardDto> getAll() {
		return boardService.getAll();
	}

	@PutMapping("/{board_id}")
	public Board updateBoard(@Valid @RequestBody BoardDto board, @PathVariable int board_id) {
		return boardService.updateBoard(board, board_id);
	}
}
