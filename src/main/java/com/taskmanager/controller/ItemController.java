package com.taskmanager.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.ItemDto;
import com.taskmanager.dto.ItemUpdateDto;
import com.taskmanager.entity.Item;
import com.taskmanager.service.ItemService;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@PostMapping
	public Item addItem(@Valid @RequestBody ItemDto item) {
		return itemService.addItem(item);
	}

	@GetMapping
	public Iterable<ItemDto> getAll() {
		return itemService.getAll();
	}

	@PutMapping("/{item_id}")
	public ItemUpdateDto updateItem(@Valid @RequestBody ItemUpdateDto item, @PathVariable int item_id) {
		return itemService.updateItem(item, item_id);
	}

	@DeleteMapping("/{id}")
	public void deleteItemById(@PathVariable int id) {
		itemService.deleteById(id);
	}

	@PutMapping("/reorder/{list_id}")
	public Iterable<Item> reorderItem(@RequestBody Map<String, List<Integer>> map,
			@PathVariable(name = "list_id") int id) {
		return itemService.itemReOrder(map.get("item"), id);
	}
}
