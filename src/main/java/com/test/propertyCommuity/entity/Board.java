package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.BoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "user_id")
    private Member member;
    @Column(length = 4000, nullable = false)
    private String title;
    @Column(length = 4000, nullable = false)
    private String content;

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "goodId")
    private Good good;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Board(
            Long id,
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

    public BoardDto toDto() {
        return BoardDto.builder()
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