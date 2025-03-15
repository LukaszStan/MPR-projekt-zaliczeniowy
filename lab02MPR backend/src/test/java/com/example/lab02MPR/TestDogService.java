package com.example.lab02MPR;

import com.example.lab02MPR.exceptions.DogAlreadyExistsException;
import com.example.lab02MPR.exceptions.DogNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDogService {
    @InjectMocks
    private MyRestService service;

    @Mock
    private DogRepository repository;

    @Test
    public void getDogIdReturnsDogWhenDogIsPresent(){
    Dog dog = new Dog("Test","Test");
    dog.setId(5L);

    when(repository.findById(5L)).thenReturn(Optional.of(dog));

    Optional<Dog> result = service.findDogById(5L);

    assertThat(result).isPresent().contains(dog);
    }

    @Test
    public void getDogIdReturnsEmptyOptionalWhenDogIsNotPresent(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DogNotFoundException.class, () -> service.findDogById(1L));
    }

    @Test
    public void dogAddAddsDogWhenDogIsNotPresent(){
        Dog dog = new Dog("Test", "Test");
        dog.setId(5L);

        service.addDog(dog);

        verify(repository).save(dog);
    }

    @Test
    public void addDogThrowsExceptionWhenDogIsPresent() {
        Dog dog = new Dog("Test", "Test");
        dog.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);

        assertThrows(DogAlreadyExistsException.class, () -> service.addDog(dog));
    }

    @Test
    public void replaceDogReplacesDogWhenDogIsPresent() {
        Dog existingDog = new Dog("ExistingDog", "Breed");
        existingDog.setId(1L);

        Dog newDog = new Dog("NewDog", "NewBreed");

        when(repository.findById(1L)).thenReturn(Optional.of(existingDog));

        service.replaceDog(newDog, 1L);

        verify(repository).save(newDog);
    }

    @Test
    public void replaceDogAddsDogWhenDogIsNotPresent() {
        Dog newDog = new Dog("NewDog", "NewBreed");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        service.replaceDog(newDog, 1L);

        verify(repository).save(newDog);
    }

    @Test
    public void deleteDogDeletesDogWhenDogIsPresent() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteDog(id));

        verify(repository).deleteById(id);
    }

    @Test
    public void deleteDogThrowsExceptionWhenDogIsNotPresent() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(DogNotFoundException.class, () -> service.deleteDog(1L));

        verify(repository, never()).deleteById(1L);
    }

    @Test
    public void filterDogReturnsAllDogsContainingName(){
        String filterName = "bob";
        List<Dog> expectedFilteredDogs = List.of(new Dog(5L, "bob", "pudel"));

        when(repository.findAllByName(filterName)).thenReturn(expectedFilteredDogs);

        List<Dog> filteredDogs = service.filterDogByName(filterName);

        assertEquals(expectedFilteredDogs, filteredDogs);
    }
}
