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

        logger.info("실패로그  : ");   
        exception.printStackTrace();
        logger.info("로그인 실패 : " +exception.getMessage());   
        response.sendRedirect("/SGC_006_01");
    }
}
