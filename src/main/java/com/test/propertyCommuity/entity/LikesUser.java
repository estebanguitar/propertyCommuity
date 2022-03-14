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
    @JoinColumn(name = "likes_id")
    private Likes likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

//    @ManyToOne()
//    private Board board;

    @Builder
    public LikesUser(Likes likes, Member member) {
        this.likes = likes;
        this.member = member;
    }


    public LikesUserDto toDto() {
        return LikesUserDto.builder()
                .likes(likes)
                .member(member)
                .build();
    }


}
