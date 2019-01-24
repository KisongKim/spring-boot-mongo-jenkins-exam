package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.ExamExceptionMatcher;
import com.archtiger.exam.contract.UpdateProductStockCountRequest;
import com.archtiger.exam.contract.UpdateProductStockCountResponse;
import com.archtiger.exam.model.GameInventory;
import com.archtiger.exam.model.GameInventoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class GameInventoryServiceTests {

    private GameInventoryService gameInventoryService;

    @MockBean
    private GameInventoryRepository gameInventoryRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static GameInventory gameInventory() {
        LocalDateTime now = LocalDateTime.now();
        GameInventory gameInventory = new GameInventory();
        gameInventory.setId(1L);
        gameInventory.setGameId(5L);
        gameInventory.setStock(5);
        gameInventory.setVersion(1);
        gameInventory.setUpdateDateTime(now);
        gameInventory.setRegisterDateTime(now);
        return gameInventory;
    }

    @Before
    public void setUp() {
        this.gameInventoryService = new GameInventoryServiceImpl(gameInventoryRepository);
    }

    @Test
    public void updateStockCount_expectedException40400() {
        thrown.expect(ExamException.class);
        thrown.expect(ExamExceptionMatcher.is(40400));

        Optional<GameInventory> entity = Optional.empty();
        Mockito.when(gameInventoryRepository.findById(Mockito.anyLong())).thenReturn(entity);

        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(5, 1);
        gameInventoryService.updateStockCount(1, request);

        Mockito.verify(gameInventoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verifyNoMoreInteractions(gameInventoryRepository);
    }

    @Test
    public void updateStockCount_expectedException40001() {
        thrown.expect(ExamException.class);
        thrown.expect(ExamExceptionMatcher.is(40001));

        Optional<GameInventory> entity = Optional.of(gameInventory());
        Mockito.when(gameInventoryRepository.findById(Mockito.anyLong())).thenReturn(entity);

        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(10, 1);
        gameInventoryService.updateStockCount(1, request);

        Mockito.verify(gameInventoryRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verifyNoMoreInteractions(gameInventoryRepository);
    }

    @Test
    public void updateStockCount() {
        GameInventory entity = gameInventory();
        Mockito.when(gameInventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(entity));

        GameInventory updated = gameInventory();
        updated.setStock(1);
        updated.setVersion(2);
        Mockito.when(gameInventoryRepository.save(Mockito.any())).thenReturn(updated);

        UpdateProductStockCountRequest request = new UpdateProductStockCountRequest(5, 1);
        UpdateProductStockCountResponse response = gameInventoryService.updateStockCount(1, request);

        Mockito.verify(gameInventoryRepository, Mockito.times(1)).findById(Mockito.any());
        ArgumentCaptor<GameInventory> entityArgument = ArgumentCaptor.forClass(GameInventory.class);
        Mockito.verify(gameInventoryRepository, Mockito.times(1)).save(entityArgument.capture());
        Mockito.verifyNoMoreInteractions(gameInventoryRepository);

        GameInventory capturedEntity = entityArgument.getValue();
        Assert.assertEquals(1, capturedEntity.getStock().intValue());
        Assert.assertEquals(response.getStockCount(), capturedEntity.getStock().intValue());
    }

}
