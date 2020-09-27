package com.church.simgokchyun.biz.sgc_006.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class sgc_006Controller {
    
    @RequestMapping("/SGC_006_01")
    String sgc_006_01(Model model) {

        model.addAttribute("dept_01", "포토존");
        model.addAttribute("dept_02", "행사앨범");
        model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");
        return "page/page_006/page_006_01";

    }

    @RequestMapping("/SGC_006_02")
    String sgc_006_02(Model model) {
        model.addAttribute("dept_01", "포토존");
        model.addAttribute("dept_02", "동영상");
        model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");
        return "page/page_006/page_006_02";
    }

}