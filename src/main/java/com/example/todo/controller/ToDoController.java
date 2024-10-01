package com.example.todo.controller;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.services.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping
    public List<ToDoDTO> getAll(){
        return toDoService.getAllToDos();
    }

    @PostMapping
    public ToDoDTO createToDo(@RequestBody ToDoDTO toDoDTO){
        return toDoService.createToDo(toDoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoDTO> getById(@PathVariable String id){
        ToDoDTO foundToDo = toDoService.getToDoById(id);
        if (null != foundToDo){
           return ResponseEntity.ok(foundToDo);
        }
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
       boolean isDeleted = toDoService.deleteById(id);
        if(isDeleted){
            return ResponseEntity.noContent().build();
        }
        else {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ToDoDTO updateById(@PathVariable String id, @RequestBody ToDoDTO updateToDo){
       return toDoService.updateById(id, updateToDo);
    }

}
