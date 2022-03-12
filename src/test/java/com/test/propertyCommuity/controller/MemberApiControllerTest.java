package com.test.propertyCommuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.entity.AccountType;
import com.test.propertyCommuity.repository.AccountTypeRepository;
import com.test.propertyCommuity.service.BoardService;
import com.test.propertyCommuity.service.MemberService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


//@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountTypeRepository repository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;


    @Test
    @Order(1)
    void setup() throws Exception {

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

        repository.save(at);
        repository.save(at2);
        repository.save(at3);

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

        memberService.saveMember(realtor);
        memberService.saveMember(lessor);
        memberService.saveMember(lessee);

        BoardDto dto = BoardDto.builder()
                .content("testCont")
//                .userId(1L)
//                .member()
                .title("testTitle")
                .build();

        BoardDto dto2 = BoardDto.builder()
                .content("testCont2")
//                .userId(2L)
                .title("testTitle2")
                .build();

        BoardDto dto3 = BoardDto.builder()
                .content("testCont3")
                .title("testTitle3")
                .build();


        boardService.saveBoard(dto);
        boardService.saveBoard(dto2);
        boardService.saveBoard(dto3);

    }


    @Test
    @Order(2)
    void getMember() throws Exception{
        mockMvc.perform(get("/api/v1/member/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
                ;
    }


    @Test
    @Order(3)
    void postMember() throws Exception{
        MemberDto dto = MemberDto.builder()
                .accountId("testId")
//                .accountType(AccountType.builder().accountTypeEng("LESSOR").build())
                .nickName("testNickname")
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/member")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
                ;

    }

    @Test
    @Order(4)
    void putMember() throws Exception{
        MemberDto dto = MemberDto.builder()
                .accountId("testId2")
//                .accountType(AccountType.builder().accountTypeEng("LESSEE").build())
                .nickName("testNickname2")
                .id(1L)
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/member")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
        ;

    }


    @Test
    @Order(5)
    void deleteMember() throws Exception{
        mockMvc.perform(delete("/api/v1/member/2"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
                ;
    }
}