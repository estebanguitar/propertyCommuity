package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Likes;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LikesDto {

    private Long id;
    private Long likesCount;

    @Builder
    public LikesDto(Long id, Long likesCount) {
        this.id = id;
        this.likesCount = likesCount;
    }


    public Likes toEntity() {
        return Likes.builder()
                .id(id)
                .likesCount(likesCount)
                .build();
    }


}
