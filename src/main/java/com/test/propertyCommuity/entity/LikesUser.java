package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.LikesUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "likes_user")
@Getter
@NoArgsConstructor
@ToString
public class LikesUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;


    @Builder
    public LikesUser(Long id, Board board, Member member) {
        this.id = id;
        this.board = board;
        this.member = member;
    }


    public LikesUserDto toDto() {
        return LikesUserDto.builder()
                .id(id)
                .board(board)
                .member(member)
                .build();
    }


}
