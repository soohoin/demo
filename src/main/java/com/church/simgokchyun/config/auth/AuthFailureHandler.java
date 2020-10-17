package com.church.simgokchyun.config.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        request.setAttribute("email_addr",request.getParameter("email_addr"));
        request.setAttribute("errMsg","이메일 또는 비밀번호가 일치하지 않습니다.");
        request.setAttribute("getMsg",exception.getMessage());
        logger.info("실패로그  : ");
        logger.info("로그인 실패 : " +exception.getMessage());   
        // authorize
        request.getRequestDispatcher("/loginForm").forward(request, response);
        
        // response.sendRedirect("/loginForm");
    }
}
