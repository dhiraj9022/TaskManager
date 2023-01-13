package com.taskmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.taskmanager.dto.ItemDto;
import com.taskmanager.dto.TaskListDto;
import com.taskmanager.entity.Board;
import com.taskmanager.entity.Item;
import com.taskmanager.entity.TaskList;
import com.taskmanager.repo.ItemRepo;
import com.taskmanager.service.BoardService;
import com.taskmanager.service.ItemService;
import com.taskmanager.service.TaskListService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ItemTest {

	private Board board;
	private TaskList taskList;
	private Item item;
	private Item item2;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TaskListService taskListService;

	@Autowired
	private ItemRepo itemRepo;

	@Autowired
	private BoardService boardService;

	@Autowired
	private ItemService itemService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void beforeEach() {
		itemRepo.deleteAll();

		BoardDto boardDto = new BoardDto();
		boardDto.setName("Sample Board");
		boardDto.setDescription("no");
		this.board = boardService.addBoard(boardDto);

		TaskListDto taskListDto = new TaskListDto();
		taskListDto.setName("TODO");
		taskListDto.setBoard_id(board.getId());
		taskListDto.setSequence(1);
		this.taskList = taskListService.addTaskList(taskListDto);

		ItemDto itemDto = new ItemDto();
		itemDto.setText("frontend");
		itemDto.setList_id(taskList.getId());
		itemDto.setSequence(1);
		this.item = itemService.addItem(itemDto);

		ItemDto itemDto2 = new ItemDto();
		itemDto2.setText("backend");
		itemDto2.setList_id(taskList.getId());
		itemDto2.setSequence(2);
		this.item2 = itemService.addItem(itemDto2);
	}

//	@AfterAll
//	public void afterAll() {
//		itemRepo.deleteAll();
//	}

	@Test
	@DisplayName("Test Item created")
	public void createItemTest() throws JsonProcessingException, Exception {

		ItemDto itemDto = new ItemDto();

		itemDto.setText("Item created");
		itemDto.setList_id(taskList.getId());
		itemDto.setSequence(3);

		mockMvc.perform(post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto))).andDo(print()).andExpect(status().isOk());

		assertEquals(itemRepo.count(), 3);
	}

	@Test
	@DisplayName("Item create test without req body")
	public void createItemTestWithoutBody() throws JsonProcessingException, Exception {

		mockMvc.perform(
				post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest()).andDo(print());

		assertEquals(itemRepo.count(), 2);

	}

	@Test
	@DisplayName("Item create test without text")
	public void createItemTestWithoutText() throws JsonProcessingException, Exception {

		mockMvc.perform(
				post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest()).andDo(print())
				.andExpect(jsonPath("$.text").value("Text can't be blank"));

		assertEquals(itemRepo.count(), 2);

	}

	@Test
	@DisplayName("Item create Test without Sequence")
	public void createItemTestWithoutSequence() throws JsonProcessingException, Exception {

		mockMvc.perform(
				post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest()).andDo(print())
				.andExpect(jsonPath("$.sequence").value("Sequence minimum value must be 1"));

		assertEquals(itemRepo.count(), 2);

	}

	@Test
	@DisplayName("Test Item created with already exists sequence")
	public void createItemTestWithAlreadyExistsSequence() throws JsonProcessingException, Exception {

		ItemDto itemDto = new ItemDto();

		itemDto.setText("Item created");
		itemDto.setList_id(taskList.getId());
		itemDto.setSequence(2);

		mockMvc.perform(post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto))).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Sequence " + 2 + " already present")));

		assertEquals(itemRepo.count(), 2);
	}

	@Test
	@DisplayName("Getting Item Test Details")
	public void getAllItemTest() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$.[0].text").value(item.getText()))
				.andExpect(jsonPath("$.[0].sequence").value(item.getSequence()))
				.andExpect(jsonPath("$.[0].list_id").value(item.getTaskList().getId())).andDo(print());

	}

	@Test
	@DisplayName("Update Item Test")
	public void updateItemTest() throws JsonProcessingException, Exception {

		ItemDto itemUpdateDto = new ItemDto();
		itemUpdateDto.setText("both backend and frontend");
		itemUpdateDto.setList_id(item.getTaskList().getId());

		mockMvc.perform(put("/items/" + item.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemUpdateDto)))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.text").value(itemUpdateDto.getText()))
				.andExpect(jsonPath("$.list_id").value(itemUpdateDto.getList_id()));

	}

	@Test
	@DisplayName("Update Item Test with invalid id")
	public void updateItemTestInvalidId() throws JsonProcessingException, Exception {

		ItemDto itemUpdateDto = new ItemDto();
		itemUpdateDto.setText("both backend and frontend");
		itemUpdateDto.setList_id(item.getTaskList().getId());

		mockMvc.perform(put("/items/" + (new Random().nextInt(1000))).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemUpdateDto)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("ReOrder taskList test")
	public void reorderItemTest() throws JsonProcessingException, Exception {

		List<Integer> list = new ArrayList<>();

		list.add(item.getId());
		list.add(item2.getId());

		Map<String, Object> map = new HashMap<>();

		map.put("item", list);

		mockMvc.perform(put("/items/reorder/" + item.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[0].sequence").value(1))
				.andExpect(jsonPath("$.[1].sequence").value(2)).andExpect(jsonPath("$.[0].id").value(item.getId()))
				.andDo(print()).andExpect(jsonPath("$.[1].id").value(item2.getId()));

		list.clear();
		list.add(item2.getId());
		list.add(item.getId());

		map.clear();
		map.put("item", list);

		mockMvc.perform(put("/items/reorder/" + item.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.[0].sequence").value(1))
				.andExpect(jsonPath("$.[1].sequence").value(2)).andExpect(jsonPath("$.[0].id").value(item2.getId()))
				.andExpect(jsonPath("$.[1].id").value(item.getId()));

		Random random = new Random();

		list.clear();
		list.add(random.nextInt(1000));
		list.add(random.nextInt(2000));

		map.clear();
		map.put("item", list);

		// test with wrong id
		mockMvc.perform(put("/items/reorder/" + (random.nextInt(1000))).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map))).andDo(print())
				.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("Delete Item Test")
	public void deleteItemTest() throws JsonProcessingException, Exception {

		assertEquals(itemRepo.count(), 2);
		mockMvc.perform(delete("/items/" + item.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		// after delete reduce by 1
		assertEquals(itemRepo.count(), 1);

		mockMvc.perform(delete("/items/" + (new Random().nextInt(1000))).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Id not found")));

		assertEquals(itemRepo.count(), 1);

	}
}
