package com.taskmanager.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.Board;

@Repository
public interface BoardRepo extends CrudRepository<Board, Integer> {

}
