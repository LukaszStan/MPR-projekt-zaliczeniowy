package com.example.lab02MPR;

import com.example.lab02MPR.exceptions.DogAlreadyExistsException;
import com.example.lab02MPR.exceptions.DogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MyRestService {
    private DogRepository repository;

    @Autowired
    public MyRestService(DogRepository repository){
        this.repository = repository;
        this.repository.save(new Dog("jacek","pudel"));
        this.repository.save(new Dog("bob","pudel"));
        this.repository.save(new Dog("aaa","bulldog"));
        this.repository.save(new Dog("kasztan","jamnik"));
    }

    public Optional<Dog> findDogById(Long id){
        Optional<Dog> repoDog = this.repository.findById(id);
        if(repoDog.isPresent()){return repoDog;}
        else throw new DogNotFoundException();
    }

    public Dog addDog(Dog dog) {
        return this.repository.save(dog);
    }

    public Dog replaceDog(Dog newDog, Long id) {
        return this.repository.findById(id)
                .map(dog -> {
                    dog.setName(newDog.getName());
                    dog.setBreed(newDog.getBreed());
                    return this.repository.save(dog);
                })
                .orElseGet(() -> {
                    newDog.setId(id);
                    return this.repository.save(newDog);
                });
    }

    public void deleteDog(Long id) {
        if(repository.existsById(id)) this.repository.deleteById(id);
        else throw new DogNotFoundException();
    }

    public List<Dog> filterDogByName(String name){
        List<Dog> filteredDogs = new ArrayList<>(repository.findAllByName(name));
        return filteredDogs;
    }

    public List<Dog> findAll(){
        return repository.findAll();
    }
}
