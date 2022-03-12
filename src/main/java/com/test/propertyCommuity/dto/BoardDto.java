package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Board;
import com.test.propertyCommuity.entity.Good;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.entity.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private Long userId;
    private Member member;
    private String title;
    private String content;
    private int isDeleted;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Good good;
    private List<Reply> replies = new ArrayList<>();
    @Builder
    public BoardDto(
            Long id,
            Long userId,
            Member member,
            String title,
            String content,
            int isDeleted,
            Date createdAt,
            Date updatedAt,
            Date deletedAt,
            Good good,
            List<Reply> replies
    ) {
        this.id = id;
        this.userId = userId;
        this.member = member;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.good = good;
        this.replies = replies;
    }

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .member(member)
                .title(title)
                .content(content)
                .isDeleted(isDeleted)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .good(good)
                .replies(replies)
                .build();
    }

}
