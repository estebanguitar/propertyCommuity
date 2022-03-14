package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.LikesDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
@ToString
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "likes_count")
    private Long likesCount;

//    @OneToMany(mappedBy = "likes")
//    private List<LikesUser> likesUserList;

    @Builder
    public Likes(Long id, Long likesCount,List<LikesUser> likesUserList) {
        this.id = id;
        this.likesCount = likesCount;
//        this.likesUserList = likesUserList;
    }


    public LikesDto toDto() {
        return LikesDto.builder()
                .id(id)
                .likesCount(likesCount)
//                .likesUserList(likesUserList)
                .build();
    }


}
