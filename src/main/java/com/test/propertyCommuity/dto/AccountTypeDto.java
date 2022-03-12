package com.test.propertyCommuity.dto;

import com.test.propertyCommuity.entity.AccountType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountTypeDto {

    private String accountTypeEng;
    private String accountTypeKor;
//    private List<Member> members = new ArrayList<>();

    @Builder
    public AccountTypeDto(
            String accountTypeEng,
            String accountTypeKor
//            List<Member> members
    ) {
        this.accountTypeEng = accountTypeEng;
        this.accountTypeKor = accountTypeKor;
//        this.members = members;
    }

    public AccountType toEntity() {
        return AccountType.builder()
                .accountTypeEng(accountTypeEng)
                .accountTypeKor(accountTypeKor)
                .build();
    }


}
