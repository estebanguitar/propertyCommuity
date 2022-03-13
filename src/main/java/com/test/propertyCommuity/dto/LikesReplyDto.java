package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikesReplyDto {

    private Long id;
    private Member member;
    private Reply reply;

    @Builder
    public LikesReplyDto(Member member, Reply reply) {
        this.member = member;
        this.reply = reply;
    }


    public LikesUser toEntity() {
        return LikesUser.builder()
                .member(member)
                .reply(reply)
                .build();
    }


}
