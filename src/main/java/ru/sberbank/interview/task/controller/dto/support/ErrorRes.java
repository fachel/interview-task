package ru.sberbank.interview.task.controller.dto.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorRes {

    private String message;
    private String details;

    public ErrorRes(String message, String details)
    {
        this.message = message;
        this.details = details;
    }
}
