package ru.sberbank.interview.task.service;

import java.util.List;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.EntityDto;
import ru.sberbank.interview.task.exception.MyException;

public interface Service {
    List<EntityDto> getEntitiesByIds(List<Long> ids) throws MyException;
    List<EntityDto> getEntitiesByCodeAndSysname(Integer code, String sysname);
    GetListRes getList(String sysname) throws MyException;
}
