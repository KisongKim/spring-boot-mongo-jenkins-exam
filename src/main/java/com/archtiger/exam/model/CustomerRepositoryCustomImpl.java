package com.archtiger.exam.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findCustomersByEmailLike(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> user = query.from(Customer.class);

        Predicate predicate = criteriaBuilder.like(user.get("email"), email + "%");
        query.select(user).where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

}
