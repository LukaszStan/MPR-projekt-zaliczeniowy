package com.example.lab02MPR;

import com.example.lab02MPR.exceptions.DogAlreadyExistsException;
import com.example.lab02MPR.exceptions.DogExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TestDogController {
    private MockMvc mockMvc;
    @Mock
    private MyRestService service;

    @InjectMocks
    private MyRestController controller;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new DogExceptionHandler(), controller).build();
    }

    @Test
    public void getByIdReturns200WhenDogIsPresent() throws Exception {
        Dog dog = new Dog("coolDog","shiba");
        when(service.findDogById(5L)).thenReturn(Optional.of(dog));

        mockMvc.perform(MockMvcRequestBuilders.get("/dog/5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value("shiba"))
                .andExpect(jsonPath("$.name").value("coolDog"))
                .andExpect(status().isOk());

    }

    @Test
    public void check400IsReturnedWhenDogIsAlreadyThere() throws Exception{
        Dog dog = new Dog("test","test");
        doThrow(new DogAlreadyExistsException()).when(service).addDog(any());
        mockMvc.perform(post("/dog/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"test\", \"breed\" : \"test\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void check404IsReturnedWhenDogIsNotFound() throws Exception {
        when(service.findDogById(100L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/dog/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void replaceDogReturns200WhenDogIsReplaced() throws Exception{
        Long id = 1L;
        Dog newDog = new Dog("newDog", "NewBreed");

        doNothing().when(service).replaceDog(any(Dog.class), eq(id));

        mockMvc.perform(MockMvcRequestBuilders.put("/dog/replace/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"newDog\", \"breed\" : \"newBreed\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDogReturns200WhenDogIsDeleted() throws Exception{
        Long id = 1L;

        doNothing().when(service).deleteDog(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dog/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void addDogReturns200WhenDogIsAdded() throws Exception{
        Dog newDog = new Dog(5L,"newDog", "newBreed");

        doNothing().when(service).addDog(any(Dog.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/dog/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" :  \"5\", \"name\" : \"newDog\", \"breed\" : \"newBreed\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void filterDogReturns200WhenDogsWithLetterBFound() throws Exception{
        String filterName = "bob";
        List<Dog> expectedFilteredDogs = List.of(new Dog(5L, "bob", "pudel"));

        when(service.filterDogByName(filterName)).thenReturn(expectedFilteredDogs);

        mockMvc.perform(MockMvcRequestBuilders.get("/dog/filterByName/bob"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedFilteredDogs.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(expectedFilteredDogs.get(0).getName()))
                .andExpect(jsonPath("$[0].breed").value(expectedFilteredDogs.get(0).getBreed()));
    }
}
