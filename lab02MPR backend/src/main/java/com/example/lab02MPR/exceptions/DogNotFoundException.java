package com.example.lab02MPR.exceptions;

public class DogNotFoundException extends RuntimeException{
    public DogNotFoundException() {super("Dog not found!");}
}
