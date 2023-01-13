package com.taskmanager.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.Item;
import com.taskmanager.entity.TaskList;

@Repository
public interface ItemRepo extends CrudRepository<Item, Integer> {

	Item findBySequenceAndTaskList(int sequence, TaskList taskList);

	Iterable<Item> findByOrderBySequence();

}
