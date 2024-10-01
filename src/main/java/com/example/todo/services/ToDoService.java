package com.example.todo.services;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.model.ToDoModel;
import com.example.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoService {

    public final ToDoRepository toDoRepository;

    public List<ToDoDTO> getAllToDos() {
        List<ToDoModel> allToDos = toDoRepository.findAll();
        return allToDos.stream().map(toDoModel ->
                        new ToDoDTO(toDoModel.id(),
                                toDoModel.title(),
                                toDoModel.description(),
                                toDoModel.completed()))
                .toList();
    }

    public ToDoDTO createToDo(ToDoDTO toDoDTO) {
        toDoRepository.save(new
                ToDoModel(toDoDTO.id(),
                toDoDTO.title(),
                toDoDTO.description(),
                toDoDTO.completed()));
        return toDoDTO;
    }

    public ToDoDTO getToDoById(String id) {
        Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
        return foundToDo.map(toDoModel ->
                        new ToDoDTO(toDoModel.id(),
                                toDoModel.title(),
                                toDoModel.description(),
                                toDoModel.completed()))
                .orElse(null);
    }

    public boolean deleteById(String id) {
        Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
        if (foundToDo.isPresent()) {
            toDoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public ToDoDTO updateById(String id, ToDoDTO updateDTO) {
        Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
        if (foundToDo.isPresent()) {
            toDoRepository.save(
                    new ToDoModel(id,
                            updateDTO.title(),
                            updateDTO.description(),
                            updateDTO.completed()));
            return updateDTO;
        } else {
            return null;
        }
    }
}

