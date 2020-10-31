package com.church.simgokchyun.biz.sgc_004.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sgc_004Controller {
    
    @RequestMapping("/sgc_004_01")
    String sgc_004_01(Model model) {

        model.addAttribute("dept_01", "교회학교");
        model.addAttribute("dept_02", "어린이부");
        model.addAttribute("img_path", "imgs/page/page_004_bg.jpg");
        return "page/page_004/page_004_01";

    }

    @RequestMapping("/sgc_004_02")
    String sgc_004_02(Model model) {
        model.addAttribute("dept_01", "교회학교");
        model.addAttribute("dept_02", "중고등부");
        model.addAttribute("img_path", "imgs/page/page_004_bg.jpg");
        return "page/page_004/page_004_02";
    }

    @RequestMapping("/sgc_004_03")
    String sgc_004_03(Model model) {
        model.addAttribute("dept_01", "교회학교");
        model.addAttribute("dept_02", "대학청년부");
        model.addAttribute("img_path", "imgs/page/page_004_bg.jpg");
        return "page/page_004/page_004_03";
    }

}