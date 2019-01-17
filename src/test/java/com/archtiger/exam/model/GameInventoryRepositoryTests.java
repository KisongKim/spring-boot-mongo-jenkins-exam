package com.archtiger.exam.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class GameInventoryRepositoryTests {

    @Autowired
    private GameInventoryRepository gameInventoryRepository;

    private static GameInventory populate() {
        LocalDateTime now = LocalDateTime.now();
        GameInventory entity = new GameInventory();
        entity.setGameId(5L);
        entity.setStock(5);
        entity.setUpdateDateTime(now);
        entity.setRegisterDateTime(now);
        return entity;
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void optimisticRockTest() {
        GameInventory entity = populate();
        gameInventoryRepository.saveAndFlush(entity);
        gameInventoryRepository.detach(entity);

        Optional<GameInventory> opt = gameInventoryRepository.findById(entity.getId());
        final GameInventory newEntity = opt.get();
        newEntity.setStock(10);
        gameInventoryRepository.saveAndFlush(newEntity);

        entity.setStock(0);
        entity = gameInventoryRepository.save(entity);
        gameInventoryRepository.save(entity);
    }

    @Test
    public void findByGameId() {
        GameInventory inventory = populate();
        gameInventoryRepository.save(inventory);

        Optional<GameInventory> optionalFound = gameInventoryRepository.findByGameId(inventory.getGameId());

        Assert.assertTrue(optionalFound.isPresent());
        Assert.assertEquals(inventory, optionalFound.get());
    }

}
