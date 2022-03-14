package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Likes;
import com.test.propertyCommuity.entity.LikesUser;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class LikesDto {

    private Long id;
    private Long likesCount;
//    private List<LikesUser> likesUserList;

    @Builder
    public LikesDto(Long id, Long likesCount,List<LikesUser> likesUserList) {
        this.id = id;
        this.likesCount = likesCount;
//        this.likesUserList = likesUserList;
    }


    public Likes toEntity() {
        return Likes.builder()
                .id(id)
                .likesCount(likesCount)
//                .likesUserList(likesUserList)
                .build();
    }


}
