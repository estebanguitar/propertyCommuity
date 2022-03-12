package com.test.propertyCommuity.repository;

import com.test.propertyCommuity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
