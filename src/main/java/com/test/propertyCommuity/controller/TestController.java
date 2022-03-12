package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.entity.AccountType;
import com.test.propertyCommuity.repository.AccountTypeRepository;
import com.test.propertyCommuity.repository.BoardRepository;
import com.test.propertyCommuity.repository.MemberRepository;
import com.test.propertyCommuity.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class TestController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;


    @GetMapping("/")
    public String test() throws Exception{




        return "index";
    }

    @PostConstruct
    void init() throws Exception {
        AccountType at = AccountType.builder()
                .accountTypeEng("REALTOR")
                .accountTypeKor("공인중개사")
                .build();
        AccountType at2 = AccountType.builder()
                .accountTypeEng("LESSOR")
                .accountTypeKor("임대인")
                .build();
        AccountType at3 = AccountType.builder()
                .accountTypeEng("LESSEE")
                .accountTypeKor("임차인")
                .build();

        accountTypeRepository.save(at);
        accountTypeRepository.save(at2);
        accountTypeRepository.save(at3);

        MemberDto realtor = MemberDto.builder()
                .nickName("realtorNickname")
                .accountType(at)
                .accountId("realtorId")
                .build();
        MemberDto lessor = MemberDto.builder()
                .nickName("lessorNickname")
                .accountType(at2)
                .accountId("lessorId")
                .build();
        MemberDto lessee = MemberDto.builder()
                .nickName("lesseeNickname")
                .accountType(at3)
                .accountId("lesseeId")
                .build();

        realtor = memberRepository.save(realtor.toEntity()).toDto();
        lessor = memberRepository.save(lessor.toEntity()).toDto();
        lessor = memberRepository.save(lessee.toEntity()).toDto();

//        BoardDto dto = BoardDto.builder()
//                .content("testCont")
//                .userId(1L)
//                .createdAt(new Date())
//                .title("testTitle")
//                .build();
//
//        BoardDto dto2 = BoardDto.builder()
//                .content("testCont2")
//                .userId(2L)
//                .createdAt(new Date())
//                .title("testTitle2")
//                .build();
//
//        BoardDto dto3 = BoardDto.builder()
//                .content("testCont3")
//                .userId(3L)
//                .createdAt(new Date())
//                .title("testTitle3")
//                .build();
//
//
//        dto = boardRepository.save(dto.toEntity()).toDto();
//        dto2 = boardRepository.save(dto2.toEntity()).toDto();
//        dto3 = boardRepository.save(dto3.toEntity()).toDto();


//        ReplyDto replyDto = ReplyDto.builder()
//                .boardId(dto.getId())
//                .content("reply")
//                .userId(realtor.getId())
//                .build();
//        ReplyDto replyDto2 = ReplyDto.builder()
//                .boardId(dto2.getId())
//                .content("reply")
//                .userId(lessor.getId())
//                .build();
//        ReplyDto replyDto3 = ReplyDto.builder()
//                .boardId(dto3.getId())
//                .content("reply")
//                .userId(lessee.getId())
//                .build();
//
//        replyRepository.save(replyDto.toEntity());
//        replyRepository.save(replyDto2.toEntity());
//        replyRepository.save(replyDto3.toEntity());
    }

}
