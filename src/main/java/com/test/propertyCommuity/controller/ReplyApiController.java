package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.LikesDto;
import com.test.propertyCommuity.dto.ReplyDto;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.service.LikesService;
import com.test.propertyCommuity.service.ReplyService;
import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ReplyApiController {

    private ReplyService replyService;
    private LikesService likesService;
    @Autowired
    public ReplyApiController(ReplyService replyService, LikesService likesService) {
        this.replyService = replyService;
        this.likesService = likesService;
    }

    @GetMapping("/reply/{boardId}")
    public ApiResponseUtil<List<ReplyDto>> list(@PathVariable(name = "boardId") Long boardId) {
        ApiResponseUtil<List<ReplyDto>> response = null;

        try {
            int isDelete = 0;

            response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), replyService.findAll(isDelete, boardId));
        }catch (Exception e) {
            response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }
//    @GetMapping("/reply/{id}")
//    public ApiResponseUtil<ReplyDto> detail(@PathVariable(name = "id") Long id) {
//        ApiResponseUtil<ReplyDto> response = null;
//
//        try {
//            int isDeleted = 0;
//            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), replyService.findById(id, isDeleted));
//        }catch (NoSuchElementException nee) {
//            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
//        }catch (Exception e) {
//            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
//        } finally {
//            if(response == null)
//                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
//        }
//
//        return response;
//    }

    @PostMapping("/reply")
    public ApiResponseUtil<ReplyDto> postReply(@RequestBody ReplyDto dto, HttpServletRequest request) {
        ApiResponseUtil<ReplyDto> response = null;

        try {
            if(dto.getId() != null) throw new Exception();
            dto = this.setReplyMember(dto, request);

            LikesDto likesDto = LikesDto.builder().likesCount(0L).build();
            likesDto = likesService.save(likesDto);

            dto.setCreatedAt(new Date());
            dto.setLikes(likesDto.toEntity());

            ReplyDto newReply = replyService.save(dto);
            dto.setId(newReply.getId());

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
    @PutMapping("/reply")
    public ApiResponseUtil<ReplyDto> putReply(@RequestBody ReplyDto dto, HttpServletRequest request) {
        ApiResponseUtil<ReplyDto> response = null;

        try {
            dto = this.setReplyMember(dto, request);
            this.valid(dto);

            dto.setUpdatedAt(new Date());
            ReplyDto newReply = replyService.save(dto);

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
    public ApiResponseUtil<ReplyDto> deleteReply(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        ApiResponseUtil<ReplyDto> response = null;
        try {
            ReplyDto dto = ReplyDto.builder().id(id).build();
            dto = this.setReplyMember(dto, request);
            this.valid(dto);

            replyService.delete(id);
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


    private ReplyDto setReplyMember(ReplyDto dto, HttpServletRequest request) throws Exception{
        String userId = String.valueOf(request.getAttribute("userId"));
        if(!Optional.ofNullable(userId).isPresent()) throw new Exception();
        dto.setMember(Member.builder().id(Long.parseLong(userId)).build());

        return dto;
    }
    private void valid(ReplyDto dto) throws Exception{
        if(!replyService.isUsersReply(dto.getMember().getId(), dto.getId())) throw new Exception("Reply Not Found");
    }
}
