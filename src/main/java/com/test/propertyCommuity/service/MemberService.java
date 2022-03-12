package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto findById(Long id) throws Exception{
        return memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Exist User")).toDto();
    }

    public MemberDto saveMember(MemberDto dto) throws Exception{
        return memberRepository.save(dto.toEntity()).toDto();
    }

    public void deleteMember(Long id) throws Exception {
        MemberDto dto = MemberDto.builder().id(id).build();
        memberRepository.delete(
                memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Exist User"))
        );
    }

}
