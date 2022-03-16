package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.service.BoardService;
import com.test.propertyCommuity.service.LikesUserService;
import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class BoardApiController {

    private BoardService boardService;
    private LikesUserService likesUserService;
    @Autowired
    public BoardApiController(BoardService boardService, LikesUserService likesUserService) {
        this.boardService = boardService;
        this.likesUserService = likesUserService;
    }

    @GetMapping("/board")
    public ApiResponseUtil<Map<String, Object>> list(HttpServletRequest request) {
        ApiResponseUtil<Map<String, Object>> response = null;
        Map<String, Object> map = new HashMap<>();
        List<Long> likesBoardIdList = new ArrayList<>();
        try {

            if(Optional.ofNullable(request.getAttribute("userId")).isPresent()) {
                BoardDto dto = this.setBoardMember(BoardDto.builder().build(), request);
                List<LikesUserDto> likesUserDtoList = likesUserService.findByUserId(dto.getMember().getId());

                likesUserDtoList.forEach(elem -> {
                    likesBoardIdList.add(elem.getBoard().getId());
                });

            }
            int isDeleted = 0;
            List<BoardDto> boardList = boardService.findAll(isDeleted);
            map.put("boardList", boardList);
            map.put("likesList", likesBoardIdList);
            response = new ApiResponseUtil<Map<String, Object>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
        }catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<Map<String, Object>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }
    @GetMapping("/board/{id}")
    public ApiResponseUtil<BoardDto> detail(@PathVariable(name = "id") Long id) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            int isDeleted = 0;
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), boardService.findById(id, isDeleted));
        }catch (NoSuchElementException nee) {
            nee.printStackTrace();
            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
        }catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }

    @PostMapping(value = "/board")
    public ApiResponseUtil<BoardDto> postBoard(@RequestBody BoardDto dto, HttpServletRequest request) {
        ApiResponseUtil<BoardDto> response = null;
        try {
            if(dto.getTitle() == null || "".equals(dto.getTitle()))
                throw new Exception("Title cannot be null");

            dto = this.setBoardMember(dto, request);

            dto.setCreatedAt(new Date());
            dto.setLikes(0L);

            BoardDto newBoard = boardService.saveBoard(dto);

            dto.setId(newBoard.getId());

            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (Exception e) {
            String message = e.getMessage();
            if(message == null) message = HttpStatus.BAD_REQUEST.getReasonPhrase();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), message, null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @PutMapping(value = "/board")
    public ApiResponseUtil<BoardDto> postPutBoard(@RequestBody BoardDto dto, HttpServletRequest request) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            dto = this.setBoardMember(dto, request);
            this.valid(dto);
            int isDeleted = 0;
            BoardDto orgDto = boardService.findById(dto.getId(), isDeleted);
            orgDto.setUpdatedAt(new Date());
            orgDto.setTitle(dto.getTitle());
            orgDto.setContent(dto.getContent());

            boardService.saveBoard(orgDto);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (Exception e) {
            String message = e.getMessage();
            if(message == null) message = HttpStatus.BAD_REQUEST.getReasonPhrase();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), message, null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @DeleteMapping("/board/{id}")
    public ApiResponseUtil<BoardDto> deleteBoard(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        ApiResponseUtil<BoardDto> response = null;
        try {
            BoardDto dto = BoardDto.builder().id(id).build();
            dto = this.setBoardMember(dto, request);
            this.valid(dto);

            boardService.deleteBoard(id);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (NoSuchElementException nee) {
            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
        }catch (Exception e) {
            String message = e.getMessage();
            if(message == null) message = HttpStatus.BAD_REQUEST.getReasonPhrase();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), message, null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @GetMapping("/reply/{boardId}")
    public ApiResponseUtil<List<BoardDto>> list(@PathVariable(name = "boardId") Long boardId) {
        ApiResponseUtil<List<BoardDto>> response = null;

        try {
            int isDelete = 0;
            List<BoardDto> list = boardService.findAllReply(isDelete, boardId);
            response = new ApiResponseUtil<List<BoardDto>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), list);
        }catch (Exception e) {
            response = new ApiResponseUtil<List<BoardDto>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<List<BoardDto>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }

    @PostMapping("/reply")
    public ApiResponseUtil<BoardDto> postReply(@RequestBody BoardDto dto, HttpServletRequest request) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            int isDeleted = 0;
            Long id = dto.getParentId();
            BoardDto board = boardService.findById(id, isDeleted);
            dto = this.setBoardMember(dto, request);
            dto.setCreatedAt(new Date());
            dto.setLikes(0L);

            BoardDto newReply = boardService.saveBoard(dto);

            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (Exception e) {
            e.printStackTrace();

            String message = e.getMessage();
            if(message == null) message = HttpStatus.BAD_REQUEST.getReasonPhrase();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), message, null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }
    @PutMapping("/reply")
    public ApiResponseUtil<BoardDto> putReply(@RequestBody BoardDto dto, HttpServletRequest request) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            dto = this.setBoardMember(dto, request);
            this.valid(dto);

            BoardDto orgDto = boardService.findByIdAndMemberIdAndParentId(dto.getId(), dto.getMember().getId(), dto.getParentId());
            orgDto.setUpdatedAt(new Date());
            orgDto.setContent(dto.getContent());

            boardService.saveBoard(orgDto);

            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (Exception e) {
            String message = e.getMessage();
            if(message == null) message = HttpStatus.BAD_REQUEST.getReasonPhrase();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), message, null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @DeleteMapping("/reply/{id}")
    public ApiResponseUtil<BoardDto> deleteReply(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        ApiResponseUtil<BoardDto> response = null;
        try {
            BoardDto dto = BoardDto.builder().id(id).build();
            dto = this.setBoardMember(dto, request);
            this.valid(dto);

            boardService.deleteBoard(id);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (NoSuchElementException nee) {
            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
        }catch (Exception e) {
            String message = e.getMessage();
            if(message == null) message = HttpStatus.BAD_REQUEST.getReasonPhrase();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), message, null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }




    private BoardDto setBoardMember(BoardDto dto, HttpServletRequest request) throws Exception{
        Optional opt = Optional.ofNullable(request.getAttribute("userId"));
        if(!opt.isPresent()) throw new Exception();
        dto.setMember(Member.builder().id(Long.parseLong(opt.get().toString())).build());
        return dto;
    }
    private void valid(BoardDto dto) throws Exception{
        if(!boardService.isUsersBoard(dto.getMember().getId(), dto.getId())) throw new Exception("Board Not Found");
    }


}
