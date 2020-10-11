package com.church.simgokchyun.common.login;

import java.util.HashMap;
import java.util.Map;

import com.church.simgokchyun.common.common.CommonService;
import com.church.simgokchyun.common.vo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  // View 를 return 한다.
public class LoginController {

    @Autowired
    CommonService comService;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/adimn")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    /**
     * 로그인 화면 오픈
     * @param model
     * @return
     */
    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("dept_01", "로그인");
        model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
        return "login/loginForm";
    }

    /**
     * 회원가입 화면 오픈
     * @param model
     * @return
     */
    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("dept_01", "회원가입");
        model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
        return "join/joinForm";
    }


    /**
     * 이메일 중복체크 
     * @param user
     * @return
     */
    @PostMapping("/emailCheck")
    public @ResponseBody Map<String,Object> emailCheck(User user ) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        logger.info(user.getEmail_addr());
        try {
            // 1. 해당 이메일의 중복을 확인한다.
            resMap.put("emailDupYn", comService.emailDubYn(user));
            // resMap.put("emailDupYn", "N"); 테스트용 
            resMap.put("errYn", "N");
        }catch(Exception e) {
            resMap.put("errYn", "Y");
            resMap.put("errMsg", e.getMessage());
        }
        return resMap;
    }


    /**
     * 회원가입 수행
     * @param user
     * @return
     */
    @PostMapping("/join")
    public @ResponseBody Map<String,Object> join(User user) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        // logger.info(user.toString());

        try {
            // 1. 사용자 페스워드를 암호화 한다.
            String password = user.getPassword();
            user.setPassword(pwdEncoder.encode(password));
            logger.info("user : " + user.getPassword());
            
            
            // 2. 이메일 인증에 사용 할 AUTH_KEY 만들기
            String auth_key = comService.getAuthKey(user);
            user.setAuth_key(auth_key);
            user.setAuth_yn("N");

            // 3. 사용자 정보 insert 
            comService.joinUser(user);

            // 4. 사용자 user_id 가져와서 set
            String user_id = comService.getUserId(user);
            user.setUser_id(user_id);

            // 6. 사용자의 이메일로 인증 api 발송
            comService.sendAuthApiUrl(user);

            resMap.put("errYn", "N");
        }catch(Exception e) {
            e.printStackTrace();
            resMap.put("errYn", "Y");
            resMap.put("errMsg", e.getMessage());
        }
        return resMap;
    }



    /**
     * 이메일 인증 API 
     * @param model
     * @return
     */
    @GetMapping("/callMailAuthApi")
    public String callMailAuthApi(Model model, User reqUser) {
        //model.addAttribute("dept_01", "회원가입");
        //model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
        logger.info("ID : " + reqUser.getUser_id());
        logger.info("key : " + reqUser.getAuth_key());

        try {
        // 1. user_id 로 사용자 정보를 가져온다. API로 넘어온 key 와 
        User user = comService.getUser(reqUser);

        // 2. 사용자 정보로 auth_key의 plain_text를 만들고 API로 넘어온 auth_key와 비교를 하고
        //    같으면 유저 정보의 인증여부를 Y 로 변경한다.

        String authPlainText = user.getUser_nic_nm() + user.getEmail_addr();
        boolean isSuccess = pwdEncoder.matches(authPlainText, reqUser.getAuth_key());

        // 유저정보 활성화
        if(isSuccess) {
            comService.successJoin(user);
        } 

        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            model.addAttribute("errMsg", e.getMessage());
        }

        return "login/loginForm";
    }
    


}
