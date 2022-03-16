package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Board;
import com.test.propertyCommuity.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private Long parentId;
    private Member member;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Long likes;
    private int isDeleted;

    @Builder
    public BoardDto(
            Long id,
            Long parentId,
            Member member,
            String title,
            String content,
            int isDeleted,
            Date createdAt,
            Date updatedAt,
            Date deletedAt,
            Long likes
    ) {
        this.id = id;
        this.parentId = parentId;
        this.member = member;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.likes = likes;
    }

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .parentId(parentId)
                .member(member)
                .title(title)
                .content(content)
                .isDeleted(isDeleted)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .likes(likes)
                .build();
    }

}
