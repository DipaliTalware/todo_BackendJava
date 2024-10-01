package com.example.todo.services;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.model.ToDoModel;
import com.example.todo.repository.ToDoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
class ToDoServiceTest {

    private final ToDoRepository toDoRepository = mock(ToDoRepository.class);
    private final ToDoService toDoService = new ToDoService(toDoRepository);


    @Test
    void testGetAll() throws Exception {
        //        GIVEN
        ToDoModel toDo1 = (
                new ToDoModel("1",
                        "Title1",
                        "Description1",
                        false));

        ToDoModel toDo2 = (
                new ToDoModel("2",
                        "Title2",
                        "Description2",
                        false));

        List<ToDoModel> existingToDos = List.of(toDo1, toDo2);

        List<ToDoDTO> convertedDTO = existingToDos.stream().map(toDoModel -> new ToDoDTO
                (toDoModel.id(),
                        toDoModel.title(),
                        toDoModel.description(),
                        toDoModel.completed()
                )
        ).toList();
        //        WHEN

        when(toDoRepository.findAll()).thenReturn(existingToDos);

        List<ToDoDTO> result = toDoService.getAllToDos();

        //        THEN
        verify(toDoRepository).findAll();

        Assertions.assertEquals(convertedDTO, result);

    }


    @Test
    void testCreateToDo() {
        //GIVEN
        ToDoDTO toDoDTO = new ToDoDTO("1",
                "Title1",
                "Description1",
                false);


        ToDoModel toDoModel1 = new ToDoModel("1",
                "Title1",
                "Description1",
                false);
        //WHEN
        ToDoDTO result = toDoService.createToDo(toDoDTO);
        //THEN
        verify(toDoRepository).save(any(ToDoModel.class));

        Assertions.assertEquals(toDoDTO, result);
    }


    @Test
    void testGetToDoById() {
        // GIVEN
        ToDoDTO foundToDo = new ToDoDTO("1",
                "Title1",
                "Description1",
                false);

        ToDoModel foundModelTODo = new ToDoModel("1",
                "Title1",
                "Description1",
                false);

        when(toDoRepository.findById("1")).thenReturn(Optional.of(foundModelTODo));

        ToDoDTO result = toDoService.getToDoById("1");
        verify(toDoRepository).findById("1");
        Assertions.assertEquals(foundToDo, result);
    }


    @Test
    void testDeleteById() {
        ToDoModel foundModelTODo = new ToDoModel("1",
                "Title1",
                "Description1",
                false);

        when(toDoRepository.findById("1")).thenReturn(Optional.of(foundModelTODo));
        boolean result = toDoService.deleteById("1");

        Assertions.assertEquals(true, result);
    }


    @Test
    void testDeleteByIdReturnfalse() {
        when(toDoRepository.findById("1")).thenReturn(Optional.empty());
        boolean result = toDoService.deleteById("1");
        verify(toDoRepository, never()).deleteById("1");
        Assertions.assertEquals(false, result);
    }


    @Test
    void testUpdateById(){
        ToDoModel updateModel = new ToDoModel("1",
                "Title1",
                "Description1",
                false);

        ToDoDTO updateDTO = new ToDoDTO("1",
                "Title1",
                "Description1",
                false);

        when(toDoRepository.findById("1")).thenReturn(Optional.of(updateModel));
        ToDoDTO result = toDoService.updateById("1", updateDTO);
        verify(toDoRepository).findById("1");
        Assertions.assertEquals(updateDTO, result);
    }

}