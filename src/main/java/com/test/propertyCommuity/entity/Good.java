package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.GoodDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "good")
@Getter
@NoArgsConstructor
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long goodId;
    @Column(name = "good_cnt")
    private int goodCnt;

//    @OneToMany @JoinColumn(name = "memberId")
//    private Member member;

    @Builder
    public Good(Long goodId, int goodCnt, Member member) {
        this.goodId = goodId;
        this.goodCnt = goodCnt;
//        this.member = member;
    }


    public GoodDto toDto() {
        return GoodDto.builder()
                .goodId(goodId)
                .goodCnt(goodCnt)
//                .member(member)
                .build();
    }


}
