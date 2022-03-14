package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.LikesDto;
import com.test.propertyCommuity.dto.ReplyDto;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.entity.Reply;
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
import java.util.stream.Collectors;

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
            List<ReplyDto> list = replyService.findAll(isDelete, boardId).stream().map(Reply::toDto).collect(Collectors.toList());
            response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), list);
        }catch (Exception e) {
            response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }

    @PostMapping("/reply")
    public ApiResponseUtil<ReplyDto> postReply(@RequestBody Reply entity, HttpServletRequest request) {
        ApiResponseUtil<ReplyDto> response = null;

        try {
            if(entity.getId() != null) throw new Exception();
            entity = this.setReplyMember(entity, request);

            LikesDto likesDto = LikesDto.builder().likesCount(0L).build();
            likesDto = likesService.save(likesDto);

            entity.setCreatedAt(new Date());
            entity.setLikes(likesDto.toEntity());

            ReplyDto newReply = replyService.save(entity);

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
    public ApiResponseUtil<ReplyDto> putReply(@RequestBody Reply entity, HttpServletRequest request) {
        ApiResponseUtil<ReplyDto> response = null;

        try {
            entity = this.setReplyMember(entity, request);
            this.valid(entity);

            entity.setUpdatedAt(new Date());
            ReplyDto newReply = replyService.save(entity);

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
            dto = this.setReplyMember(dto.toEntity(), request).toDto();
            this.valid(dto.toEntity());

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


    private Reply setReplyMember(Reply entity, HttpServletRequest request) throws Exception{
        Optional opt = Optional.ofNullable(request.getAttribute("userId"));
        if(!opt.isPresent()) throw new Exception();
        entity.setMember(Member.builder().id(Long.parseLong(opt.get().toString())).build());

        return entity;
    }
    private void valid(Reply entity) throws Exception{
        if(!replyService.isUsersReply(entity.getMember().getId(), entity.getId())) throw new Exception("Reply Not Found");
    }
}
