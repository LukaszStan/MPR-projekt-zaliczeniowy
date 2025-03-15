package com.example.lab02MPR.exceptions;

public class DogAlreadyExistsException extends RuntimeException{
    public DogAlreadyExistsException() {super("Dog already exists!");}
}
