package com.taskmanager.repo;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.Board;
import com.taskmanager.entity.TaskList;

@Repository
public interface TaskListRepo extends CrudRepository<TaskList, TaskList> {

	List<TaskList> findByOrderBySequence();

	Optional<TaskList> findById(int id);

	List<TaskList> findAllByIdIn(@Valid List<Integer> listIds);

	TaskList findBySequenceAndBoard(int sequence, Board board);

}
