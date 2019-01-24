package com.archtiger.exam.web;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.UpdateProductStockCountRequest;
import com.archtiger.exam.contract.UpdateProductStockCountResponse;
import com.archtiger.exam.service.GameInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
public class GameInventoryController {

    private GameInventoryService gameInventoryService;

    @Autowired
    public GameInventoryController(GameInventoryService gameInventoryService) {
        this.gameInventoryService = gameInventoryService;
    }

    @PatchMapping(path = "/inventory/{id}")
    public UpdateProductStockCountResponse updateInventoryStockCount(@PathVariable(name = "id") @Min(1) @Max(9999) long id,
                                                                     @Valid @RequestBody UpdateProductStockCountRequest request) throws ExamException {
        return gameInventoryService.updateStockCount(id, request);
    }

}
