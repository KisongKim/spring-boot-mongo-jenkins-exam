package com.archtiger.exam.model;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GameInventoryRepositoryCustomImpl implements GameInventoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void detach(GameInventory inventory) {
        entityManager.detach(inventory);
    }

}
