package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.dto.GoodDto;
import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.service.BoardService;
import com.test.propertyCommuity.service.GoodService;
import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class BoardApiController {

    private BoardService boardService;
    private GoodService goodService;

    @Autowired
    public BoardApiController(BoardService boardService, GoodService goodService) {
        this.boardService = boardService;
        this.goodService = goodService;
    }

    @GetMapping("/board")
    public ApiResponseUtil<List<BoardDto>> list() {
        ApiResponseUtil<List<BoardDto>> response = null;

        try {
            response = new ApiResponseUtil<List<BoardDto>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), boardService.findAll());
        }catch (Exception e) {
            response = new ApiResponseUtil<List<BoardDto>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<List<BoardDto>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }
    @GetMapping("/board/{id}")
    public ApiResponseUtil<BoardDto> detail(@PathVariable(name = "id") Long id) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), boardService.findById(id));
        }catch (NoSuchElementException nee) {
            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
        }catch (Exception e) {
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }

    @PostMapping(value = "/board")
    public ApiResponseUtil<BoardDto> postBoard(@RequestBody BoardDto dto) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            GoodDto good = GoodDto.builder().goodCnt(0).build();
            good = goodService.initGood(good);

            MemberDto member = MemberDto.builder().id(dto.getUserId()).build();

            dto.setMember(member.toEntity());
            dto.setGood(good.toEntity());
            dto.setCreatedAt(new Date());
            BoardDto newBoard = boardService.saveBoard(dto);

            dto.setId(newBoard.getId());

            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), dto);
        }catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @PutMapping(value = "/board")
    public ApiResponseUtil<BoardDto> postPutBoard(@RequestBody BoardDto dto) {
        ApiResponseUtil<BoardDto> response = null;

        try {
            dto.setUpdatedAt(new Date());
            BoardDto newBoard = boardService.saveBoard(dto);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), newBoard);
        }catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @DeleteMapping("/board/{id}")
    public ApiResponseUtil<BoardDto> deleteBoard(@PathVariable(name = "id") Long id) {
        ApiResponseUtil<BoardDto> response = null;
        try {
            boardService.deleteBoard(id);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (NoSuchElementException nee) {
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

    @PutMapping("/board/{id}/good")
    public ApiResponseUtil<BoardDto> postGoodBoard(@PathVariable(name = "id") Long id) {
        ApiResponseUtil<BoardDto> response = null;
        try {


//            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), boardService.saveBoardGood(id));
        }catch (NoSuchElementException nee) {
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

}