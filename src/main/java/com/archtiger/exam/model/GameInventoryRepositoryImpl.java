package com.archtiger.exam.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class GameInventoryRepositoryImpl implements GameInventoryRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public void detach(GameInventory inventory) {
        entityManager.detach(inventory);
    }

}
