package com.example.lab02MPR;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
public class MyRestService {

    RestClient restClient;

    public MyRestService(){
        restClient = RestClient.create();
    }

    public Dog findDogById(Long id){
        Dog dog = restClient.get()
                .uri("http://localhost:8081/dog/" + id)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return dog;
    }

    public void addDog(Dog dog) {
        restClient.post()
                .uri("http://localhost:8081/dog/add")
                .body(dog)
                .retrieve();
    }

    public void replaceDog(Dog newDog, Long id) {
        restClient.put()
                .uri("http://localhost:8081/dog/replace/" + id)
                .body(newDog)
                .retrieve();
    }

    public void deleteDog(Long id) {
        restClient.delete()
                .uri("http://localhost:8081/dog/delete/" + id)
                .retrieve();
    }

    public List<Dog> findAll(){
        List<Dog> dogs = restClient
                .get()
                .uri("http://localhost:8081/dog/all")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return dogs;
    }
}
