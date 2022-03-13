package com.test.propertyCommuity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_type")
@Getter
@NoArgsConstructor
public class AccountType {

    @Id
    @Column(name = "account_type_eng", length = 100, nullable = false)
    private String accountTypeEng;
    @Column(name = "account_type_kor", length = 100, nullable = false)
    private String accountTypeKor;

    @Builder
    public AccountType(String accountTypeEng, String accountTypeKor) {
        this.accountTypeEng = accountTypeEng;
        this.accountTypeKor = accountTypeKor;
    }

}
