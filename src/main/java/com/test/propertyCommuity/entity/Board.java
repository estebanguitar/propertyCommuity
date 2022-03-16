package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.BoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id", updatable = false)
    private Long parentId;
    @ManyToOne @JoinColumn(name = "user_id", updatable = false)
    private Member member;
    @Column(length = 4000)
    private String title;
    @Column(length = 4000, nullable = false)
    private String content;
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "likes_id", updatable = false)
//    private Likes likes;
    private Long likes;
    @Column(name = "is_deleted")
    private int isDeleted;

    @Builder
    public Board(
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

    public BoardDto toDto() {
        return BoardDto.builder()
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
