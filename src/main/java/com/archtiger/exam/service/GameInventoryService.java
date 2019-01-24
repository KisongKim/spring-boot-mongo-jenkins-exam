package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.UpdateProductStockCountRequest;
import com.archtiger.exam.contract.UpdateProductStockCountResponse;

public interface GameInventoryService {

    UpdateProductStockCountResponse updateStockCount(long inventoryId,
                                                     UpdateProductStockCountRequest request) throws ExamException;

}
