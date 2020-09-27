package com.church.simgokchyun.biz.sgc_001.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class sgc_001Controller {
    
    @RequestMapping("/SGC_001_01")
    String sgc_001_01(Model model) {
        model.addAttribute("dept_01", "교회소개");
        model.addAttribute("dept_02", "교회비전");
        model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        return "page/page_001/page_001_01";

    }

    @RequestMapping("/SGC_001_02")
    String sgc_001_02(Model model) {
        model.addAttribute("dept_01", "교회소개");
        model.addAttribute("dept_02", "인사말");
        model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        return "page/page_001/page_001_02";
    }

    @RequestMapping("/SGC_001_03")
    String sgc_001_03(Model model) {
        model.addAttribute("dept_01", "교회소개");
        model.addAttribute("dept_02", "예배안내");
        model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        return "page/page_001/page_001_03";
    }

    @RequestMapping("/SGC_001_04")
    String sgc_001_04(Model model) {
        model.addAttribute("dept_01", "교회소개");
        model.addAttribute("dept_02", "오시는길");
        model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");

        return "page/page_001/page_001_04";
    }

}