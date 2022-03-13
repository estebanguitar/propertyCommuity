package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.util.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/notAllowed")
    public ApiResponseUtil notAllowed() {
        return new ApiResponseUtil(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
    }


    @GetMapping("/notExistUser")
    public ApiResponseUtil notExistUser() {
        return new ApiResponseUtil(HttpStatus.NO_CONTENT.value(), "User Not Found", null);
    }

    @GetMapping("/error")
    public ApiResponseUtil error() {
        return new ApiResponseUtil(HttpStatus.BAD_REQUEST.value(), "Unknown Error", null);
    }
}
