package com.test.propertyCommuity.entity;

import com.test.propertyCommuity.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id", nullable = false)
    private String accountId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_type")
    private AccountType accountType;
    @Column(nullable = false)
    private String nickName;
    private int quit;

    @Builder
    public Member(
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

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(id)
                .nickName(nickName)
                .accountType(accountType)
                .accountId(accountId)
                .quit(quit)
                .build();
    }


}
