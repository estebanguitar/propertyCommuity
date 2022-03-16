package com.test.propertyCommuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.service.BoardService;
import com.test.propertyCommuity.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class BoardApiControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private BoardService boardService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MemberService memberService;

    @Test @DisplayName("1. Get All Board List without Auth")
    void getBoardListWithoutAuth() throws Exception{
        mockMvc.perform(get("/api/v1/board"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }
    @Test @DisplayName("2. Get All Board List with Auth")
    void getBoardListWithAuth() throws Exception{
        mockMvc.perform(get("/api/v1/board")
                        .header("Authorization", "Lessor 2")
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("3. Get Board Where ID = 1")
    void getBoard() throws Exception {
        mockMvc.perform(get("/api/v1/board/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("4. Insert Board with Auth")
    void postBoardWithAuth() throws Exception{

        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .createdAt(new Date())
                .title("testTitle")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/board")
                        .content(cont)
                        .header("Authorization", "Lessor 2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("5. Insert Board without Auth")
    void postBoardWithoutAuth() throws Exception{

        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .member(Member.builder().id(1L).build())
                .createdAt(new Date())
                .title("testTitle")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/board")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(redirectedUrl("/auth/notAllowed"))
                .andDo(print());
    }

    @Test @DisplayName("6. Update Board with Auth")
    void putBoardWithAuth() throws Exception{
        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .title("testTitle")
                .id(1L)
                .build();

        String cont = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/board")
                        .content(cont)
                        .header("Authorization", "Realtor 1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }
    @Test @DisplayName("7. Update Board without Auth")
    void putBoardWithoutAuth() throws Exception{
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
                .andExpect(redirectedUrl("/auth/notAllowed"))
                .andDo(print());
    }

    @Test @DisplayName("8. Delete Board with Auth")
    void deleteBoardWithAuth() throws Exception{

        mockMvc.perform(delete("/api/v1/board/1")
                        .header("Authorization", "Realtor 1")
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("9. Delete Board without Auth")
    void deleteBoardWithoutAuth() throws Exception{

        mockMvc.perform(delete("/api/v1/board/1"))
                .andExpect(redirectedUrl("/auth/notAllowed"))
                .andDo(print());
    }

    @Test @DisplayName("10. Update Board Not Users")
    void postBoardNotUsers() throws Exception{
        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .title("testTitle")
                .id(1L)
                .build();

        String cont = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/board")
                        .content(cont)
                        .header("Authorization", "Lessor 2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Board Not Found"))
                .andDo(print());
    }

    @Test @DisplayName("11. Get ReplyList Where Board ID = 1")
    void getReplyList() throws Exception {
        mockMvc.perform(get("/api/v1/reply/1"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("12. Insert Reply Where Board ID = 1 with Auth")
    void postReplyWithAuth() throws Exception {

        BoardDto dto = BoardDto.builder()
                .parentId(1L)
                .createdAt(new Date())
                .content("reply cont")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/reply")
                        .content(cont)
                        .header("Authorization", "Lessor 2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test @DisplayName("13. Insert Reply Where Board ID = 1 without Auth")
    void postReplyWithoutAuth() throws Exception {

        BoardDto dto = BoardDto.builder()
                .id(34L)
                .updatedAt(new Date())
                .content("reply cont")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/reply")
                        .content(cont)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(redirectedUrl("/auth/notAllowed"))
                .andDo(print());
    }

    @Test @DisplayName("14. Update Reply Where Board ID = 1 with Auth But Not Users")
    void putReplyNotUsers() throws Exception {
        BoardDto dto = BoardDto.builder()
                .content("testCont")
                .id(34L)
                .updatedAt(new Date())
                .build();

        String cont = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/reply")
                        .content(cont)
                        .header("Authorization", "Lessor 2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Board Not Found"))
                .andDo(print());
    }

    @Test @DisplayName("15. Delete Reply with Auth")
    void deleteReplyWithAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/reply/34")
                        .header("Authorization", "Lessee 3")
                )
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andDo(print());
    }


    @Test @DisplayName("16. Delete Reply without Auth")
    void deleteReplyWithoutAuth() throws Exception{

        mockMvc.perform(delete("/api/v1/reply/34"))
                .andExpect(redirectedUrl("/auth/notAllowed"))
                .andDo(print());
    }
}