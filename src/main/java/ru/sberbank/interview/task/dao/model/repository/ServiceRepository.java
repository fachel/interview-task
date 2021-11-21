package ru.sberbank.interview.task.dao.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.interview.task.dao.model.EntityDao;

import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<EntityDao, Long>{

    List<EntityDao> findAllByIdIn(List<Long> ids);
    List<EntityDao> findAllBySysname(String sysname);
    List<EntityDao> findAllBySysnameOrCode(String sysname, Integer code);
    List<EntityDao> findAllByCode(Integer code);
    List<EntityDao> findAll();
}
