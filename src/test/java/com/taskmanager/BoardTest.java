package com.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.dto.BoardDto;
import com.taskmanager.entity.Board;
import com.taskmanager.repo.BoardRepo;
import com.taskmanager.service.BoardService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class BoardTest {

	@Autowired
	private MockMvc mockMvc;

	private Board board;

	@Autowired
	private BoardRepo boardRepo;

	@Autowired
	private BoardService boardService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void beforeEach() {
		boardRepo.deleteAll();

		BoardDto boardDto = new BoardDto();
		boardDto.setName("Sample Board");
		boardDto.setDescription("no");
		this.board = boardService.addBoard(boardDto);
	}

//	@AfterAll
//	public void afterAll() {
//		boardRepo.deleteAll();
//	}

	@Test
	@DisplayName("Board create test")
	public void createBoardTest() throws JsonProcessingException, Exception {

		BoardDto boardDto = new BoardDto();
		boardDto.setName("My tasks");
		boardDto.setDescription("my task workings");

		mockMvc.perform(post("/boards").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(boardDto))).andExpect(status().isOk());

		assertEquals(boardRepo.count(), 2);
	}

	@Test
	@DisplayName("Board create test without req body")
	public void createBoardTestWithoutBody() throws JsonProcessingException, Exception {

		mockMvc.perform(post("/boards").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Board create test without name")
	public void createBoardTestWithoutName() throws JsonProcessingException, Exception {

		mockMvc.perform(post("/boards").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(status().isBadRequest()).andDo(print())
				.andExpect(jsonPath("$.name").value("Name can't be blank"))
				.andExpect(jsonPath("$.description").value("Desc can't be blank"));
	}

	@Test
	@DisplayName("Getting Board Test Details")
	public void getAllBoardTest() throws JsonProcessingException, Exception {
		mockMvc.perform(get("/boards").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$.[0].name").value(board.getName()))
				.andExpect(jsonPath("$.[0].description").value(board.getDescription()))
				.andExpect(jsonPath("$.[0].archived").value(board.getArchived())).andDo(print());
	}

	@Test
	@DisplayName("Update Board Details")
	public void updateBoardTest() throws JsonProcessingException, Exception {

		BoardDto boardUpdateDto = new BoardDto();

		boardUpdateDto.setName("new name");
		boardUpdateDto.setDescription("new desc");
		boardUpdateDto.setArchived(false);

		mockMvc.perform(put("/boards/" + board.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardUpdateDto)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value(boardUpdateDto.getName()))
				.andExpect(jsonPath("$.archived").value(boardUpdateDto.getArchived()))
				.andExpect(jsonPath("$.description").value(boardUpdateDto.getDescription())).andDo(print());
	}
}
