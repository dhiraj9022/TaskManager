package com.taskmanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.ItemDto;
import com.taskmanager.dto.ItemUpdateDto;
import com.taskmanager.entity.Item;
import com.taskmanager.entity.TaskList;
import com.taskmanager.exception.AlreadyExistsException;
import com.taskmanager.exception.NotFoundException;
import com.taskmanager.repo.ItemRepo;
import com.taskmanager.repo.TaskListRepo;

@Service
public class ItemService {

	@Autowired
	private ItemRepo itemRepo;

	@Autowired
	private TaskListRepo taskListRepo;

	@Autowired
	private TaskListService taskListService;

	public Item addItem(@Valid ItemDto itemDto) {
//		TaskList taskList = taskListRepo.findById(itemDto.getList_id())
//				.orElseThrow(() -> new NotFoundException("Id not found"));
		TaskList taskList = taskListService.findById(itemDto.getList_id());

		Item items = new Item();
//		items.setId(itemDto.getId());

		items.setText(itemDto.getText());

		if (itemRepo.findBySequenceAndTaskList(itemDto.getSequence(), taskList) != null) {
			throw new AlreadyExistsException("Sequence " + itemDto.getSequence() + " already present");
		}

		items.setSequence(itemDto.getSequence());
		items.setTaskList(taskListService.findById(itemDto.getList_id()));

		return itemRepo.save(items);

	}

	public List<ItemDto> getAll() {
		List<ItemDto> itemDtos = new ArrayList<>();

		itemRepo.findByOrderBySequence().forEach(itemList -> {

			ItemDto itemDto = new ItemDto();
			itemDto.setId(itemList.getId());
			itemDto.setText(itemList.getText());
			itemDto.setList_id(itemList.getTaskList().getId());
			itemDto.setSequence(itemList.getSequence());
			itemDtos.add(itemDto);
		});

		return itemDtos;
	}

	public ItemUpdateDto updateItem(@Valid ItemUpdateDto itemUpdateDto, int itemId) {
		Item item = findById(itemId);

		item.setText(itemUpdateDto.getText());
		item.setTaskList(taskListService.findById(itemUpdateDto.getList_id()));

		return mapToDto(itemRepo.save(item));
	}

	public ItemUpdateDto mapToDto(Item item) {
		ItemUpdateDto itemUpdateDto = new ItemUpdateDto();

		itemUpdateDto.setList_id(item.getTaskList().getId());
		itemUpdateDto.setText(item.getText());
		itemUpdateDto.setId(item.getId());
		return itemUpdateDto;
	}

	public Item findById(int id) {
		return itemRepo.findById(id).orElseThrow(() -> new NotFoundException("Id not found"));

	}

	public void deleteById(int id) {

		// you don't know whether deleted by id or not
//		itemRepo.deleteById(id);

		// first find by id then delete
		itemRepo.delete(findById(id));
	}

	public Iterable<Item> itemReOrder(List<Integer> itemIds, int listId) {
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < itemIds.size(); i++) {
			Item item = findById(itemIds.get(i));
			item.setSequence(i + 1);
			item.setTaskList(taskListService.findById(listId));
			items.add(item);
		}
		return itemRepo.saveAll(items);
	}

}
