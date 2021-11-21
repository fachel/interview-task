package ru.sberbank.interview.task.exception;

import lombok.Getter;

@Getter
public class MyException extends Exception {

    private final String message;
    private final String details;

    public MyException(String message, String details) {
        super(message);
        this.message = message;
        this.details = details;
    }

}

