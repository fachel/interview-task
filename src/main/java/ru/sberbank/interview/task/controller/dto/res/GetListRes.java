package ru.sberbank.interview.task.controller.dto.res;

import java.util.List;

import lombok.Data;
import ru.sberbank.interview.task.controller.dto.support.EntityDto;

@Data
public class GetListRes {

    private List<EntityDto> items;
    private Integer unread;

}
