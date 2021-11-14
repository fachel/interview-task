package ru.sberbank.interview.task.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sberbank.interview.task.builder.QueryBuilder;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.EntityDto;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.model.repository.ServiceRepository;
import ru.sberbank.interview.task.exception.MyException;
import ru.sberbank.interview.task.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceImpl implements Service {

    private final ServiceRepository serviceRepository;
    private final QueryBuilder queryBuilder;

    @Override
    public List<EntityDto> getEntitiesByIds(List<Long> ids) {
        List<EntityDto> entities = serviceRepository.findAllByIdIn(ids).stream().map(EntityDao::toDto).collect(Collectors.toList());
        List<Long> entityIds = entities.stream().map(EntityDto::getId).collect(Collectors.toList());
        ids.removeAll(entityIds);
        if (!ids.isEmpty())
            throw new MyException("Ошибка", "Не найдены сущности с id " + ids);
        return entities;
    }

    @Override
    public List<EntityDto> getEntitiesByCodeAndSysname(Integer code, String sysname) {
        return queryBuilder.getEntitiesByCodeAndSysname(code, sysname).stream().map(EntityDao::toDto).collect(Collectors.toList());
    }

    @Override
    public GetListRes getList(String sysname) {
        GetListRes res = new GetListRes();
        List<EntityDto> list = serviceRepository.findBySysname(sysname).stream().map(EntityDao::toDto).collect(Collectors.toList());
        if (list.isEmpty()){
            throw new MyException("Ошибка", "Объекты с таким sysname не найдены");
        }
        res.setItems(list);
        res.setUnread((int) list.stream().filter(el -> el.getWatchedDttm() == null).count());
        if (res.getUnread() == 0 && !list.isEmpty()){
            res.setItems(new ArrayList<>());
            return res;
        }
        return res;
    }
}
