package com.example.lab02MPR;

public class Dog {
    private Long id;
    private String name;
    private String breed;

    public Dog(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }

    public Dog(Long id, String name, String breed) {
        this.id = id;
        this.name = name;
        this.breed = breed;
    }


    public Dog() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String makeNoise(){
        return "hau hau";
    }

}
