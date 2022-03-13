package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.LikesReplyDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "likes_user")
@Getter
@NoArgsConstructor
public class LikesUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Reply reply;

    @Builder
    public LikesUser(Member member, Reply reply) {
        this.member = member;
        this.reply = reply;
    }


    public LikesReplyDto toDto() {
        return LikesReplyDto.builder()
                .member(member)
                .reply(reply)
                .build();
    }


}
