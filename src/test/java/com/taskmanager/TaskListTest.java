package com.taskmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.taskmanager.dto.TaskListDto;
import com.taskmanager.entity.Board;
import com.taskmanager.entity.TaskList;
import com.taskmanager.repo.TaskListRepo;
import com.taskmanager.service.BoardService;
import com.taskmanager.service.TaskListService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class TaskListTest {

	private Board board;

	@Autowired
	private MockMvc mockMvc;

	private TaskList taskList;
	private TaskList taskList2;

	@Autowired
	private TaskListRepo taskListRepo;

	@Autowired
	private BoardService boardService;

	@Autowired
	private TaskListService taskListService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void beforeEach() {
		taskListRepo.deleteAll();

		BoardDto boardDto = new BoardDto();
		boardDto.setName("Sample Board");
		boardDto.setDescription("no");
		this.board = boardService.addBoard(boardDto);

		TaskListDto taskListDto = new TaskListDto();
		taskListDto.setName("TODO");
		taskListDto.setBoard_id(board.getId());
		taskListDto.setSequence(1);
		this.taskList = taskListService.addTaskList(taskListDto);

		TaskListDto taskListDto2 = new TaskListDto();
		taskListDto2.setName("Done");
		taskListDto2.setBoard_id(board.getId());
		taskListDto2.setSequence(2);
		this.taskList2 = taskListService.addTaskList(taskListDto2);
	}

//	@AfterAll
//	public void afterAll() {
//		taskListRepo.deleteAll();
//	}

	@Test
	@DisplayName("TaskList Test Created")
	public void createTaskListTest() throws JsonProcessingException, Exception {

		TaskListDto taskListDto = new TaskListDto();

		taskListDto.setBoard_id(board.getId());
		taskListDto.setName("Hii");
		taskListDto.setSequence(3);

		mockMvc.perform(post("/lists").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(taskListDto))).andDo(print()).andExpect(status().isOk());

		assertEquals(taskListRepo.count(), 3);
	}

	@Test
	@DisplayName("TaskList create test without req body")
	public void createTaskListTestWithoutBody() throws JsonProcessingException, Exception {

		mockMvc.perform(
				post("/lists").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest()).andDo(print());

	}

	@Test
	@DisplayName("TaskList create test without name")
	public void createTaskListTestWithoutName() throws JsonProcessingException, Exception {

		mockMvc.perform(
				post("/lists").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest()).andDo(print())
				.andExpect(jsonPath("$.name").value("Name can't be blank"));
	}

	@Test
	@DisplayName("TaskList create Test without Sequence")
	public void createTaskListTestwithoutSequence() throws JsonProcessingException, Exception {

		mockMvc.perform(
				post("/lists").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest()).andDo(print())
				.andExpect(jsonPath("$.sequence").value("Sequence minimum value can be 1"));
	}

	@Test
	@DisplayName("Getting TaskList Test Details")
	public void getAllTatskListTest() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/lists").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$.[0].name").value(taskList.getName()))
				.andExpect(jsonPath("$.[0].sequence").value(taskList.getSequence()))
				.andExpect(jsonPath("$.[0].archived").value(taskList.getArchived()))
				.andExpect(jsonPath("$.[0].board_id").value(taskList.getBoard().getId())).andDo(print());
	}

	@Test
	@DisplayName("Update TaskList Test")
	public void updateTaskListTest() throws JsonProcessingException, Exception {

		TaskListDto taskListupdateDto = new TaskListDto();
		taskListupdateDto.setName("new Complete");
		taskListupdateDto.setBoard_id(taskList.getBoard().getId());
		taskListupdateDto.setArchived(true);

		mockMvc.perform(put("/lists/" + taskList.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(taskListupdateDto)))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(taskListupdateDto.getName()))
				.andExpect(jsonPath("$.board_id").value(taskListupdateDto.getBoard_id()))
				.andExpect(jsonPath("$.archived").value(taskListupdateDto.getArchived()));

		taskListupdateDto.setName("new Complete2");
		taskListupdateDto.setBoard_id(taskList.getBoard().getId());
		taskListupdateDto.setArchived(false);

		mockMvc.perform(put("/lists/" + taskList.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(taskListupdateDto)))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(taskListupdateDto.getName()))
				.andExpect(jsonPath("$.board_id").value(taskListupdateDto.getBoard_id()))
				.andExpect(jsonPath("$.archived").value(taskListupdateDto.getArchived()));

	}

	@Test
	public void update_with_wrong_id() throws JsonProcessingException, Exception {
		TaskListDto taskListupdateDto = new TaskListDto();
		taskListupdateDto.setName("new Complete");
		taskListupdateDto.setBoard_id(taskList.getBoard().getId());
		taskListupdateDto.setArchived(true);

		mockMvc.perform(put("/lists/" + (new Random().nextInt(1000))).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(taskListupdateDto)))
				.andDo(print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Id not found")));

	}

	@Test
	@DisplayName("ReOrder taskList test")
	public void reorderTaskListTest() throws JsonProcessingException, Exception {

		List<Integer> list = new ArrayList<>();

		list.add(taskList.getId());
		list.add(taskList2.getId());

		Map<String, Object> map = new HashMap<>();

		map.put("list", list);

		mockMvc.perform(put("/lists/reorder/" + taskList.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[0].sequence").value(1))
				.andExpect(jsonPath("$.[1].sequence").value(2)).andExpect(jsonPath("$.[0].id").value(taskList.getId()))
				.andDo(print()).andExpect(jsonPath("$.[1].id").value(taskList2.getId()));

		list.clear();
		list.add(taskList2.getId());
		list.add(taskList.getId());

		map.clear();
		map.put("list", list);

		mockMvc.perform(put("/lists/reorder/" + taskList.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.[0].sequence").value(1))
				.andExpect(jsonPath("$.[1].sequence").value(2)).andExpect(jsonPath("$.[0].id").value(taskList2.getId()))
				.andExpect(jsonPath("$.[1].id").value(taskList.getId()));

	}

}
