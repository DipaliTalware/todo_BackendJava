package com.example.todo.model;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ToDo")
@Builder
public record ToDoModel(String id, String title, String description, boolean completed) {
}
