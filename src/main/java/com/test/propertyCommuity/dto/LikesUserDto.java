package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Likes;
import com.test.propertyCommuity.entity.LikesUser;
import com.test.propertyCommuity.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LikesUserDto {

    private Long id;
    private Likes likes;
    private Member member;

    @Builder
    public LikesUserDto(Likes likes, Member member) {
        this.likes = likes;
        this.member = member;
    }


    public LikesUser toEntity() {
        return LikesUser.builder()
                .likes(likes)
                .member(member)
                .build();
    }


}
