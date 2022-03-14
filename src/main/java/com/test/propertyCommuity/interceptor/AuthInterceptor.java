package com.test.propertyCommuity.interceptor;

import com.test.propertyCommuity.dto.MemberDto;
import com.test.propertyCommuity.entity.AccountType;
import com.test.propertyCommuity.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("Authorization");
        String method = request.getMethod();
        String accountType = null;
        String userId = null;

        // Not Auth and Not Allowed Method
        if(!StringUtils.hasText(auth) && !method.equalsIgnoreCase("GET")) {
            response.sendRedirect("/auth/notAllowed");
            return false;
        }else if (StringUtils.hasText(auth)) { // Has Auth

                try {
                    String[] authArr = auth.split(" ");
                    accountType = authArr[0];
                    userId = authArr[1];

                    if(!method.equalsIgnoreCase("GET")) {
                        AccountType accountTypeEntity = AccountType.builder().accountTypeEng(accountType.toUpperCase()).build();
                        MemberDto dto = MemberDto.builder().id(Long.parseLong(userId)).accountType(accountTypeEntity).build();
                        if(!memberService.isExistUser(dto)) {
                            response.sendRedirect("/auth/notExistUser");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("/auth/error");
                }
        }
        request.setAttribute("accountType", accountType);
        request.setAttribute("userId", userId);


        return true;
    }
}
