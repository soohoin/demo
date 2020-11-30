package com.church.simgokchyun.biz.sgc_001.controller;

import com.church.simgokchyun.common.common.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sgc_001Controller {

    @Autowired
    CommonService comService;
    
    @RequestMapping("/sgc_001_01")
    String sgc_001_01(Model model) {

        try {

            model.addAttribute("dept_01", comService.getMenu_lv01("MENU01"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU01","01"));
            model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        } catch(Exception e) {

        }
        return "page/page_001/page_001_01";

    }

    @RequestMapping("/sgc_001_02")
    String sgc_001_02(Model model) {

        try {
            
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU01"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU01","02"));
            model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_001/page_001_02";
    }

    @RequestMapping("/sgc_001_03")
    String sgc_001_03(Model model) {

        try {
            
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU01"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU01","03"));
            model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_001/page_001_03";
    }

    @RequestMapping("/sgc_001_04")
    String sgc_001_04(Model model) {
        try {
            
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU01"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU01","04"));
            model.addAttribute("img_path", "imgs/page/page_001_bg.jpg");
        }catch(Exception e) {

        }
        return "page/page_001/page_001_04";
    }

}