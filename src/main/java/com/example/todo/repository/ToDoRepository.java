package com.example.todo.repository;

import com.example.todo.model.ToDoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends MongoRepository<ToDoModel, String> {
}
