package com.church.simgokchyun.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.church.simgokchyun.common.vo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만드어준다.(Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User 오브젝트타입 => UserDetails 타입 객체

// Security Sesstion => Authentication => UserDetails


public class PrincipalDetails implements UserDetails{

    private User user; //콤포지션? 

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public PrincipalDetails(User user) {
        this.user = user;
    }
    
    public User getUser() {
		return user;
	}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUser_nic_nm();
    }


    // 계정이 만료되지 않았는지 리턴한다. (true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지 리턴한다. (true:잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다.(true:만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화가(사용가능)인지 리턴한다.(true:활성화)
    @Override
    public boolean isEnabled() {

        // 우리 사이트!! 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로함.
        // 현재시간 - 로그인시간 => 1년을 초과하면 return false;

        return true;
    }

    // 해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(()->{ 
            logger.info("ROLE_"+user.getRole_nm());
            return "ROLE_"+user.getRole_nm();
        });
        // collect.add(new GrantedAuthority(){

        //     @Override
        //     public String getAuthority() {
        //         return "ROLE_"+user.getRole_nm();
        //     }
        // });
        return collect;
    }

}
