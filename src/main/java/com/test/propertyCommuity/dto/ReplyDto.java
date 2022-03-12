package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.Board;
import com.test.propertyCommuity.entity.Good;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.entity.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ReplyDto {
    private Long id;
    private Board boardId;
//    private Long userId;
    private Member member;
    private String content;
    private Good good;

    @Builder
    public ReplyDto(
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

    public Reply toEntity() {
        return Reply.builder()
                .id(id)
                .boardId(boardId)
                .member(member)
                .content(content)
                .good(good)
                .build();
    }

}
