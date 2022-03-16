package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.entity.Member;
import com.test.propertyCommuity.service.LikesUserBoardService;
import com.test.propertyCommuity.service.LikesUserService;
import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LikesController {

    private LikesUserService likesUserService;
    private LikesUserBoardService likesUserBoardService;

    @Autowired
    public LikesController(LikesUserService likesUserService, LikesUserBoardService likesUserBoardService) {
        this.likesUserService = likesUserService;
        this.likesUserBoardService = likesUserBoardService;
    }

//    @GetMapping("/likes")
//    public ApiResponseUtil<List<LikesUserDto>> getLike() {
//        ApiResponseUtil<List<LikesUserDto>> response = null;
//
//        try {
//            response = new ApiResponseUtil<List<LikesUserDto>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), likesUserService.findAll());
//        }catch (DataIntegrityViolationException e) {
//            e.printStackTrace();
//            response = new ApiResponseUtil<List<LikesUserDto>>(HttpStatus.BAD_REQUEST.value(), "User Not Found", null);
//        }catch (Exception e) {
//            e.printStackTrace();
//            response = new ApiResponseUtil<List<LikesUserDto>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
//        } finally {
//            if(response == null)
//                response = new ApiResponseUtil<List<LikesUserDto>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
//        }
//        return response;
//    }

    @PostMapping("/likes")
    public ApiResponseUtil<LikesUserDto> postLike(@RequestBody LikesUserDto dto, HttpServletRequest request) {
        ApiResponseUtil<LikesUserDto> response = null;

        try {
            dto = this.setLikesUserMember(dto, request);
            if(likesUserService.findByUserIdAndBoardId(dto.getMember().getId(), dto.getBoard().getId()))
                return response = new ApiResponseUtil<>(HttpStatus.ALREADY_REPORTED.value(), HttpStatus.ALREADY_REPORTED.getReasonPhrase(), null);

            likesUserBoardService.save(dto);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), "User Not Found", null);
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


    private LikesUserDto setLikesUserMember(LikesUserDto dto, HttpServletRequest request) throws Exception{
        Optional opt = Optional.ofNullable(request.getAttribute("userId"));
        if(!opt.isPresent()) throw new Exception();
        dto.setMember(Member.builder().id(Long.parseLong(opt.get().toString())).build());
        return dto;
    }
}
