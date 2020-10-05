package com.church.simgokchyun.common.login;

import java.util.HashMap;
import java.util.Map;

import com.church.simgokchyun.common.common.CommonService;
import com.church.simgokchyun.common.vo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  // View 를 return 한다.
public class LoginController {

    @Autowired
    CommonService comService;

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
     * 회원가입 수행
     * @return
     */
    @PostMapping("/join")
    public @ResponseBody String join() {
        return "join";
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
            // resMap.put("emailDupYn", comService.emailDubYn(user));
            resMap.put("emailDupYn", "N");
            resMap.put("errYn", "N");
        }catch(Exception e) {
            resMap.put("errYn", "Y");
            resMap.put("errMsg", e.getMessage());
        }
        return resMap;
    }



}
