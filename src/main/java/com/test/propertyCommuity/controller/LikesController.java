package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.LikesDto;
import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.service.LikesService;
import com.test.propertyCommuity.service.LikesUserService;
import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LikesController {

    private LikesService likesService;
    private LikesUserService likesUserService;

    @Autowired
    public LikesController(LikesService likesService, LikesUserService likesUserService) {
        this.likesService = likesService;
        this.likesUserService = likesUserService;
    }

    @PostMapping("/likes")
    public ApiResponseUtil<LikesUserDto> postLike(@RequestBody LikesUserDto dto) {
        ApiResponseUtil<LikesUserDto> response = null;

        try {
            if(likesUserService.findByUserId(dto.getMember().getId()))
                return response = new ApiResponseUtil<>(HttpStatus.ALREADY_REPORTED.value(), HttpStatus.ALREADY_REPORTED.getReasonPhrase(), null);

            LikesDto likesDto = likesService.findById(dto.getLikes().getId());
            likesDto.setLikesCount(likesDto.getLikesCount()+1);

            likesDto =likesService.save(likesDto);

            dto.setLikes(likesDto.toEntity());
            likesUserService.save(dto);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (DataIntegrityViolationException e) {
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), "User Not Found", null);
        }catch (Exception e) {
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

}
