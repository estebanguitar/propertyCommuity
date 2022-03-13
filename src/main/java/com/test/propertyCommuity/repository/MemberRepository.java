package com.test.propertyCommuity.repository;

import com.test.propertyCommuity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT c.id FROM Member c WHERE c.id=:id AND c.accountType.accountTypeEng=:accountTypeEng")
    Optional<Member> findByIdAndAccountTypeAccountTypeEng(Long id, String accountTypeEng);
}
