package ru.sberbank.interview.task.dao.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.interview.task.dao.model.EntityDao;

import java.util.List;

public interface ServiceRepository extends CrudRepository<EntityDao, Long>{

    List<EntityDao> findAllByIdIn(List<Long> ids);
    List<EntityDao> findBySysname(String sysname);
}
