package com.archtiger.exam.contract;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateProductStockCountResponse {

    private long inventoryId;

    private int stockCount;

    public UpdateProductStockCountResponse(long inventoryId, int stockCount) {
        this.inventoryId = inventoryId;
        this.stockCount = stockCount;
    }

}
