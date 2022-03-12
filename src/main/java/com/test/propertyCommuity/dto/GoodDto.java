package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Good;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoodDto {
    private Long goodId;
    private int goodCnt;
//    private

    @Builder
    public GoodDto(Long goodId, int goodCnt) {
        this.goodId = goodId;
        this.goodCnt = goodCnt;

    }

    public Good toEntity() {
        return Good.builder()
                .goodId(goodId)
                .goodCnt(goodCnt)
                .build();
    }


}
