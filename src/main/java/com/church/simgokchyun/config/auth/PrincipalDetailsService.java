package com.church.simgokchyun.config.auth;

import com.church.simgokchyun.common.common.CommonMapper;
import com.church.simgokchyun.common.vo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설절에서 loginProcessingUrl("login");
// login 요청이 오면 자동으로 UserDetailsService 
// 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행

@Service
public class PrincipalDetailsService implements UserDetailsService{
    

    @Autowired
    CommonMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 시큐리티 sesstion = Authentication = UserDatails
    //         sesstion(Authentication(UserDatails))

    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 함.
    // username이 DB에 있는지만 확인해주면 됨. 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mapper.findByUsername(username);
        logger.info("user : " + user);
        
        if(user != null) {
            return new PrincipalDetails(user); // 시큐리티의 세션에 유저 정보가 저장된다.
        } 
        return null;
    }
    
    
}
