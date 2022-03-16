package com.test.propertyCommuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.entity.Board;
import com.test.propertyCommuity.service.LikesUserBoardService;
import com.test.propertyCommuity.service.LikesUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class LikesControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private LikesUserService likesUserService;
    @Autowired private LikesUserBoardService likesUserBoardService;
    @Autowired private ObjectMapper objectMapper;

    @Test @DisplayName("1. Insert Likes without Auth")
    void postLikeWithoutAuth() throws Exception {
        LikesUserDto dto = LikesUserDto.builder()
                .board(Board.builder().id(1L).build())
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/likes")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(redirectedUrl("/auth/notAllowed"))
                .andDo(print());


    }

    @Test @DisplayName("2. Insert Likes with Auth")
    void postLikeWithAuth() throws Exception{
        LikesUserDto dto = LikesUserDto.builder()
                .board(Board.builder().id(34L).build())
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/likes")
                        .content(cont)
                        .header("Authorization", "Lessee 3")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("3. Insert Likes Already Report")
    void postLikeAlreadyReport() throws Exception{
        LikesUserDto dto = LikesUserDto.builder()
                .board(Board.builder().id(1L).build())
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/likes")
                        .content(cont)
                        .header("Authorization", "Lessee 3")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.ALREADY_REPORTED.value()))
                .andDo(print());

    }
}