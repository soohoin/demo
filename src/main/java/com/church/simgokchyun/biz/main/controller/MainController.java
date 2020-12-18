package com.church.simgokchyun.biz.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    
    // private final Log logger = LogFactory.getLog(MainController.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.datasource.username}")
    String testusername;

    @RequestMapping({"/main","/",""})
    String mainPage(Model model) {
        
        logger.info("mainPage()");
        logger.info("username : " + testusername);
        return "page/main/main";
    }


    @RequestMapping("/testMap")
    String testMap(Model model) {
        logger.info("testMap()");
        return "test";
    }
}