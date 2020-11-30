package com.church.simgokchyun.biz.sgc_003.controller;


import com.church.simgokchyun.common.common.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sgc_003Controller {

    @Autowired
    CommonService comService;
    
    @RequestMapping("/sgc_003_01")
    String sgc_003_01(Model model) {

        try {
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU03"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU03","01"));
            model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_003/page_003_01";

    }

    @RequestMapping("/sgc_003_02")
    String sgc_003_02(Model model) {
        try {
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU03"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU03","02"));
            model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_003/page_003_02";
    }

    @RequestMapping("/sgc_003_03")
    String sgc_003_03(Model model) {
        try {
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU03"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU03","03"));
            model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_003/page_003_03";
    }

    @RequestMapping("/sgc_003_04")
    String sgc_003_04(Model model) {
        try {
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU03"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU03","04"));
            model.addAttribute("img_path", "imgs/page/page_003_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_001/page_001_04";
    }

}