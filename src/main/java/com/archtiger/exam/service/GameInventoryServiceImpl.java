package com.archtiger.exam.service;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.UpdateProductStockCountRequest;
import com.archtiger.exam.contract.UpdateProductStockCountResponse;
import com.archtiger.exam.model.GameInventory;
import com.archtiger.exam.model.GameInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GameInventoryServiceImpl implements GameInventoryService {

    private GameInventoryRepository gameInventoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(GameInventoryServiceImpl.class);

    @Autowired
    public GameInventoryServiceImpl(GameInventoryRepository gameInventoryRepository) {
        this.gameInventoryRepository = gameInventoryRepository;
    }

    @Override
    @Transactional
    public UpdateProductStockCountResponse updateStockCount(long inventoryId, UpdateProductStockCountRequest request)
            throws ExamException {
        Optional<GameInventory> optionalGameInventory = gameInventoryRepository.findById(inventoryId);
        if (optionalGameInventory.isEmpty()) {
            logger.error("[updateStockCount] Inventory item with id={} not found", inventoryId);
            throw new ExamException(ExamError.NOT_FOUND, "Inventory item not found.");
        }

        GameInventory gameInventory = optionalGameInventory.get();
        if (gameInventory.getStock() != request.getCurrentStockCount()) {
            logger.error("[updateStockCount] Your stock count={} and server count={} mismatch",
                    request.getCurrentStockCount(), gameInventory.getStock());
            throw new ExamException(ExamError.STOCK_COUNT_MISMATCH, "Stock count mismatch.");
        }

        gameInventory.setStock(request.getApplicableStockCount());
        gameInventory.setUpdateDateTime(LocalDateTime.now());
        gameInventory = gameInventoryRepository.save(gameInventory);
        return new UpdateProductStockCountResponse(inventoryId, gameInventory.getStock());
    }

}
