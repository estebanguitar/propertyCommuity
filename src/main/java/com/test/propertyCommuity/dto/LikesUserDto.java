package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Board;
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
    private Board board;
    private Member member;

    @Builder
    public LikesUserDto(Long id, Board board, Member member) {
        this.id = id;
        this.board = board;
        this.member = member;
    }


    public LikesUser toEntity() {
        return LikesUser.builder()
                .id(id)
                .board(board)
                .member(member)
                .build();
    }


}
