package com.test.propertyCommuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.propertyCommuity.dto.ReplyDto;
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

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReplyApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void list() throws Exception{
        mockMvc.perform(get("/api/v1/reply/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
        ;
    }
    @Test @Order(2)
    void postReply() throws Exception {
        ReplyDto dto = ReplyDto.builder()
                .content("REPLY CONT")
//                .board(BoardDto.builder().id(13L).build().toEntity())
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/reply")
                        .content(cont)
                        .header("Authorization","Lessor 2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
        ;
    }

    @Test @Order(3)
    void putReply() throws Exception {
        ReplyDto dto = ReplyDto.builder()
                .id(6L)
                .content("REPLY CONT")
//                .board(BoardDto.builder().id(13L).build().toEntity())
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/v1/reply")
                        .content(cont)
                        .header("Authorization","Lessor 2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print())
        ;
    }

    @Test @Order(4)
    void deleteReply() throws Exception {

        mockMvc.perform(delete("/api/v1/reply/6")
                        .header("Authorization","Lessor 2")
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }
}