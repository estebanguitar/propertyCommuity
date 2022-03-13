package com.test.propertyCommuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.entity.AccountType;
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

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    void getMember() throws Exception{
        mockMvc.perform(get("/api/v1/member/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
                ;
    }


    @Test
    @Order(2)
    void postMember() throws Exception{
        MemberDto dto = MemberDto.builder()
                .accountId("testId")
                .accountType(AccountType.builder().accountTypeEng("LESSOR").build())
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
    @Order(3)
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
    @Order(4)
    void deleteMember() throws Exception{
        mockMvc.perform(delete("/api/v1/member/4"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
                ;
    }
}