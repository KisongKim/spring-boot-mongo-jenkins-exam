package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonAutoDetect
public class UpdateProductStockCountRequest {

    @NotNull(message = "current stock count must be specified.")
    @Range(min = 0, max = 9999, message = "stock count must be ranged 0-9999")
    @JsonProperty(required = true)
    private Integer currentStockCount;

    @NotNull(message = "applicable stock count must be specified.")
    @Range(min = 0, max = 9999, message = "stock count must be ranged 0-9999")
    @JsonProperty(required = true)
    private Integer applicableStockCount;

    public UpdateProductStockCountRequest(int currentStockCount, int applicableStockCount) {
        this.currentStockCount = currentStockCount;
        this.applicableStockCount = applicableStockCount;
    }

}
