package ru.sberbank.interview.task.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sberbank.interview.task.dao.model.EntityDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QueryBuilder extends EntityDao {

    private final EntityManager entityManager;

    public List<EntityDao> getEntitiesByCodeAndSysname(Integer code, String sysname){
        List<EntityDao> list;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EntityDao> cq = cb.createQuery(EntityDao.class);
        Root<EntityDao> root = cq.from(EntityDao.class);
        List<Predicate> predicates = new ArrayList<>();
        if (code != null){
            predicates.add(cb.equal(root.get("code"), code));
        }
        if (sysname != null){
            predicates.add(cb.equal(root.get("sysname"), sysname));
        }
        if (code == null && sysname == null){
            cq.select(root);
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<EntityDao> query = entityManager.createQuery(cq);
        list = query.getResultList().stream().distinct().collect(Collectors.toList());
        return list;
    }



}
