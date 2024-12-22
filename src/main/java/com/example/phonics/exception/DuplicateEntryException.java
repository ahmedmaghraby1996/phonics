package com.example.phonics.exception;

public class DuplicateEntryException extends  RuntimeException{
    public DuplicateEntryException(String message) {
        super(message);
    }
}
