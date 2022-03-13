package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.ReplyDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reply")
@Getter
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;
    @Column(length = 4000)
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
    @Column(name = "is_deleted")
    private int isDeleted;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "good_id")
//    private LikesBoard likesBoard;

    @Builder
    public Reply(
            Long id,
            Board board,
            Member member,
            String content,
            Date createdAt,
            Date updatedAt,
            Date deletedAt,
            int isDeleted
//            LikesBoard likesBoard
    ) {
        this.id = id;
        this.board = board;
        this.member = member;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
//        this.likesBoard = likesBoard;
    }

    public ReplyDto toDto() {
        return ReplyDto.builder()
                .id(id)
                .member(member)
                .board(board)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .isDeleted(isDeleted)
//                .good(likesBoard)
                .build();
    }


}
