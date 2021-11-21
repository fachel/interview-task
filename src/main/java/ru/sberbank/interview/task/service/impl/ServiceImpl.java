package ru.sberbank.interview.task.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.EntityDto;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.model.repository.ServiceRepository;
import ru.sberbank.interview.task.exception.MyException;
import ru.sberbank.interview.task.service.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceImpl implements Service {

    private final ServiceRepository serviceRepository;

    @Override
    public List<EntityDto> getEntitiesByIds(List<Long> ids) throws MyException {
        List<EntityDto> entities = serviceRepository.findAllByIdIn(ids).stream().map(EntityDao::toDto).collect(Collectors.toList());
        List<Long> entityIds = entities.stream().map(EntityDto::getId).collect(Collectors.toList());
        List<Long> uniqueIds = getUniqueIds(ids, entityIds); // O(nlog(n))
        if (!uniqueIds.isEmpty()) {
            throw new MyException("Ошибка", "Не найдены сущности с id " + uniqueIds);
        }
        return entities;
    }

    @Override
    public List<EntityDto> getEntitiesByCodeAndSysname(Integer code, String sysname) {
        List<EntityDto> list;
        if (code != null && sysname != null){
            list = serviceRepository.findAllBySysnameOrCode(sysname, code).stream().map(EntityDao::toDto).collect(Collectors.toList());
        } else if (code != null){
            list = serviceRepository.findAllByCode(code).stream().map(EntityDao::toDto).collect(Collectors.toList());
        } else if (sysname != null) {
            list = serviceRepository.findAllBySysname(sysname).stream().map(EntityDao::toDto).collect(Collectors.toList());
        } else {
            list = serviceRepository.findAll().stream().map(EntityDao::toDto).collect(Collectors.toList());
        }
        Set<EntityDto> set = new HashSet<>(list);
        list = new ArrayList<>(set);
        return list;
    }

    @Override
    public GetListRes getList(String sysname) throws MyException {
        GetListRes res = new GetListRes();
        List<EntityDto> list = serviceRepository.findAllBySysname(sysname).stream().map(EntityDao::toDto).collect(Collectors.toList());
        if (list.isEmpty()){
            throw new MyException("Ошибка", "Объекты с таким sysname не найдены");
        }
        res.setItems(list);
        res.setUnread((int) list.stream().filter(el -> el.getWatchedDttm() == null).count());
        if (res.getUnread() == 0){
            res.setItems(new ArrayList<>());
            return res;
        }
        return res;
    }

    private List<Long> getUniqueIds(List<Long> list1, List<Long> list2){
        List<Long> newList = new ArrayList<>();
        list1.addAll(list2);
        quickSort(list1, 0, list1.size()-1);
        int i = 0;
        while (true){
            if (i == list1.size()-1){
                newList.add(list1.get(i));
                break;
            }
            if (Objects.equals(list1.get(i), list1.get(i + 1))){
                if (list1.size() - i == 2)
                    break;
                i+=2;
            } else {
                newList.add(list1.get(i));
                i++;
            }
        }
        return newList;
    }

    public void quickSort(List<Long> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private int partition(List<Long> arr, int begin, int end) {
        long pivot = arr.get(end);
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j) <= pivot) {
                i++;

                long swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        long swapTemp = arr.get(i+1);
        arr.set(i+1, arr.get(end));
        arr.set(end, swapTemp);
        return i+1;
    }

}
