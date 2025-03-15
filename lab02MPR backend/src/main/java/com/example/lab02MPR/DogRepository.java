package com.example.lab02MPR;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DogRepository  extends CrudRepository<Dog, Long> {
    Dog findByName(String name);
    List<Dog> findAllByName(String name);
    @Query("SELECT d FROM Dog d WHERE d.name LIKE %:name%")
    List<Dog> dogNameContains(@Param("name")String name);

    List<Dog> findAll();
}
