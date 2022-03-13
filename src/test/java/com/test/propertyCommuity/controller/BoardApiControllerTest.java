package com.test.propertyCommuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.entity.Member;
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
    private MemberService memberService;

    @Test
    @Order(1)
    void getBoardList() throws Exception{
        mockMvc.perform(get("/api/v1/board"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    @Order(2)
    void getBoard() throws Exception {
        mockMvc.perform(get("/api/v1/board/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    @Order(3)
    void postBoard() throws Exception{

        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .member(Member.builder().id(1L).build())
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
    @Order(4)
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
    @Order(5)
    void deleteBoard() throws Exception{

        mockMvc.perform(delete("/api/v1/board/2"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }
}