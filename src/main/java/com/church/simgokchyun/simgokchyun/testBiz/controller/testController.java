package com.church.simgokchyun.simgokchyun.testBiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * testController
 */
@Controller
public class testController {

    @RequestMapping("/test")
    String test(Model model) {
        return "test";
    }
    
}