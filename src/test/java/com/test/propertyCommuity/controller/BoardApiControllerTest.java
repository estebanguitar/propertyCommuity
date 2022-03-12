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

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountTypeRepository repository;

    @Autowired
    private MemberService memberService;

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

        realtor = memberService.saveMember(realtor);
        lessor = memberService.saveMember(lessor);
        lessor = memberService.saveMember(lessee);

        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .userId(1L)
                .createdAt(new Date())
                .title("testTitle")
                .build();

        BoardDto dto2 = BoardDto.builder()
                .content("testCont2")
                .userId(2L)
                .createdAt(new Date())
                .title("testTitle2")
                .build();

        BoardDto dto3 = BoardDto.builder()
                .content("testCont3")
                .userId(3L)
                .createdAt(new Date())
                .title("testTitle3")
                .build();


        dto = boardService.saveBoard(dto);
        dto2 = boardService.saveBoard(dto2);
        dto3 = boardService.saveBoard(dto3);


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

    @Test
    @Order(2)
    void getBoardList() throws Exception{
        mockMvc.perform(get("/api/v1/board"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    @Order(3)
    void getBoard() throws Exception {
        mockMvc.perform(get("/api/v1/board/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    @Order(4)
    void postBoard() throws Exception{

        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .userId(1L)
                .title("testTitle")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/board")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    @Order(5)
    void putBoard() throws Exception{
        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .title("testTitle")
                .id(1L)
                .build();

        String cont = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/board")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    @Order(6)
    void deleteBoard() throws Exception{

        mockMvc.perform(delete("/api/v1/board/2"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }
}