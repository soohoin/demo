package com.church.simgokchyun.biz.sgc_003.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sgc_003Controller {
    
    @RequestMapping("/sgc_003_01")
    String sgc_003_01(Model model) {

        model.addAttribute("dept_01", "양육/교육");
        model.addAttribute("dept_02", "양육과정");
        model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        return "page/page_003/page_003_01";

    }

    @RequestMapping("/sgc_003_02")
    String sgc_003_02(Model model) {
        model.addAttribute("dept_01", "양육/교육");
        model.addAttribute("dept_02", "교육사역");
        model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        return "page/page_003/page_003_02";
    }

    @RequestMapping("/sgc_003_03")
    String sgc_003_03(Model model) {
        model.addAttribute("dept_01", "양육/교육");
        model.addAttribute("dept_02", "일대일양육");
        model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        return "page/page_003/page_003_03";
    }

    @RequestMapping("/sgc_003_04")
    String sgc_003_04(Model model) {
        model.addAttribute("dept_01", "교회소개");
        model.addAttribute("dept_02", "오시는길");
        model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        return "page/page_001/page_001_04";
    }

}