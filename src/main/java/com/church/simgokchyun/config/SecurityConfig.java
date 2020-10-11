package com.church.simgokchyun.config;

import com.church.simgokchyun.config.auth.AuthFailureHandler;
import com.church.simgokchyun.config.auth.PrincipalDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 ! , preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    

    @Autowired
    private PrincipalDetailsService PrincipalDetailsService;

    @Autowired
    AuthFailureHandler authFailureHandler;

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
    // @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(PrincipalDetailsService).passwordEncoder(encoderPwd());
    }

    // @Override

    // public void configure(WebSecurity web) throws Exception
    // {
    //     // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
    //     web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .mvcMatchers("/SGC_005_**").authenticated()
            .mvcMatchers("/SGC_006_**").authenticated()
            .mvcMatchers("/SGC_007_**").authenticated()
            .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/loginForm")
            .usernameParameter("email_addr")
            //.passwordParameter("메핑 할 pw 명")
            .failureHandler(authFailureHandler)
            // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/");
    }

}
