package com.example.lab02MPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MyRestController {
    private final MyRestService restService;


    @Autowired
    public MyRestController(MyRestService restService) {
        this.restService = restService;
    }

    @GetMapping("dog/{id}")
    public ResponseEntity<Dog> getDogName(@PathVariable("id") Long id) {
        Optional<Dog> dog = this.restService.findDogById(id);
        return dog.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("dog/add")
    public Dog addDog(@RequestBody Dog dog){
        return this.restService.addDog(dog);
    }

    @PutMapping("dog/replace/{id}")
    public Dog replaceDog(@RequestBody Dog dog, @PathVariable("id") Long id){
        return this.restService.replaceDog(dog, id);
    }

    @DeleteMapping("dog/delete/{id}")
    public void deleteDog(@PathVariable("id") Long id){this.restService.deleteDog(id);}

    @GetMapping("dog/filterByName/{name}")
    public List<Dog> filterByName(@PathVariable("name") String name){
       return this.restService.filterDogByName(name);
    }

    @GetMapping("dog/all")
    public List<Dog> allDogs(){return this.restService.findAll();}
}

