package ru.sberbank.interview.task.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sberbank.interview.task.controller.dto.support.ErrorRes;
import ru.sberbank.interview.task.exception.MyException;

@ControllerAdvice
@Slf4j
public class ValidationHandler {

    @ExceptionHandler(MyException.class)
    @ResponseBody
    protected ErrorRes handleException(MyException ex){
        return new ErrorRes(ex.getMessage(), ex.getDetails());
    }

}
