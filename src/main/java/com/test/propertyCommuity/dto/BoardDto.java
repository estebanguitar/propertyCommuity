package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Board;
import com.test.propertyCommuity.entity.Likes;
import com.test.propertyCommuity.entity.LikesUser;
import com.test.propertyCommuity.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private Member member;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Likes likes;
    private int isDeleted;
    private List<LikesUser> likesUsers;

    @Builder
    public BoardDto(
            Long id,
            Member member,
            String title,
            String content,
            int isDeleted,
            Date createdAt,
            Date updatedAt,
            Date deletedAt,
            Likes likes,
            List<LikesUser> likesUsers
    ) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.likes = likes;
        this.likesUsers = likesUsers;

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
                .likes(likes)
                .likesUsers(likesUsers)
                .build();
    }

}
