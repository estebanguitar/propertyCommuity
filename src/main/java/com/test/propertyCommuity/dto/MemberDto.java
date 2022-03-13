package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.AccountType;
import com.test.propertyCommuity.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String nickName;
    private AccountType accountType;
    private String accountId;
    private int quit;

    @Builder
    public MemberDto(
            Long id,
            String nickName,
            AccountType accountType,
            String accountId,
            int quit
    ) {
        this.id = id;
        this.nickName = nickName;
        this.accountType = accountType;
        this.accountId = accountId;
        this.quit = quit;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .nickName(nickName)
                .accountType(accountType)
                .accountId(accountId)
                .quit(quit)
                .build();
    }

}
