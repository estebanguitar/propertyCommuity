package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.service.MemberService;
import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class MemberApiController {

    private MemberService memberService;

    @Autowired
    public MemberApiController(MemberService memberService) {this.memberService = memberService;}

    @GetMapping("/member/{id}")
    public ApiResponseUtil<MemberDto> getMember(@PathVariable(name = "id") Long id) {

        Map<String, Object> returnMap = new HashMap<>();
        ApiResponseUtil<MemberDto> response = null;
        try {
//            MemberDto dto = memberService.findById(id);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), memberService.findById(id));
        }catch (NoSuchElementException nee) {
            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }

        return response;
    }

    @RequestMapping(value = "/member", method = {RequestMethod.POST, RequestMethod.PUT})
    public ApiResponseUtil<MemberDto> postPutMember(@RequestBody MemberDto dto) {
        ApiResponseUtil<MemberDto> response = null;
        try {
            MemberDto newMeber = memberService.saveMember(dto);
            dto.setId(newMeber.getId());
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), dto);
        }catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<MemberDto>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<MemberDto>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }

    @DeleteMapping(value = "/member/{id}")
    public ApiResponseUtil<MemberDto> deleteMember(@PathVariable(name = "id") Long id) {
        ApiResponseUtil<MemberDto> response = null;
        try {
            memberService.deleteMember(id);
            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        }catch (NoSuchElementException nee) {
            response = new ApiResponseUtil<>(HttpStatus.NO_CONTENT.value(), nee.getMessage(), null);
        }catch (Exception e) {
            e.printStackTrace();
            response = new ApiResponseUtil<MemberDto>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        } finally {
            if(response == null)
                response = new ApiResponseUtil<MemberDto>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return response;
    }
}
