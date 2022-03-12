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
//    @Column(name = "board_id")
//    private Long boardId;

    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "board_id")
    private Board boardId;

//    @Column(name = "user_id")
//    private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(length = 4000)
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "goodId")
    private Good good;
//    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL)
//    private List<Good> goods = new ArrayList<>();


    @Builder
    public Reply(
            Long id,
            Board boardId,
            Member member,
            String content,
            Good good
    ) {
        this.id = id;
        this.boardId = boardId;
        this.member = member;
        this.content = content;
        this.good = good;
    }

    public ReplyDto toDto() {
        return ReplyDto.builder()
                .id(id)
                .boardId(boardId)
                .member(member)
                .content(content)
                .good(good)
                .build();
    }


}
