package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Likes;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.entity.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class ReplyDto {
    private Long id;
//    private Board board;
    private Member member;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private int isDeleted;
    private Likes likes;

    @Builder
    public ReplyDto(
            Long id,
//            Board board,
            Member member,
            String content,
            Date createdAt,
            Date updatedAt,
            Date deletedAt,
            int isDeleted,
            Likes likes
    ) {
        this.id = id;
//        this.board = board;
        this.member = member;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.likes = likes;
    }

    public Reply toEntity() {
        return Reply.builder()
                .id(id)
                .member(member)
//                .board(board)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .isDeleted(isDeleted)
                .likes(likes)
                .build();
    }

}
