package com.church.simgokchyun.biz.sgc_002.controller;

import java.util.List;
import java.util.Map;

import com.church.simgokchyun.biz.sgc_002.service.Sgc_002Service;
import com.church.simgokchyun.common.common.CommonService;
import com.church.simgokchyun.common.paging.Pagination;
import com.church.simgokchyun.common.vo.Board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Sgc_002Controller {

    @Autowired
    Sgc_002Service service;

    @Autowired
    CommonService comService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 설교요약 글 목록 화면 오픈 
     * @param model
     * @return
     */
    @RequestMapping("/SGC_002_01")
    String sgc_002_01(Model model) {
        logger.info("call Controller : sgc_002_01");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "말씀/찬양");
            model.addAttribute("dept_02", "설교요약");
            model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

            // 3. 검색 조건에서 사용 할 공통 코드를 가져온다.
            model.addAttribute("WORSHIP_ORDER_CD", comService.makeCombo("WORSHIP_ORDER_CD", new String[]{"1","0"}));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_002/page_002_01";
    }

    /**
     * 설교요약 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/SGC_002_01-S", method = RequestMethod.POST)
    String sgc_002_01_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_002_01_S");

        try {
            
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("01");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 

            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_002/page_002_01 :: #boardList__bind";
    }

    /**
     * 설교요약 게시글 작성 화면오픈
     * @param model
     * @return String
     */    
    @RequestMapping("/SGC_002_01-CREATE")
    String sgc_002_01_CREATE(Model model) {
        logger.info("call Controller : sgc_002_01_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "말씀/찬양");
            model.addAttribute("dept_02", "설교요약");
            model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            model.addAttribute("WORSHIP_ORDER_CD", comService.select_com_code("WORSHIP_ORDER_CD"));
            model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_01";
    }

    /**
     * 설교요약 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_002_01-SAVE", method = RequestMethod.POST)
    String sgc_002_01_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_002_01_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("01");

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01";
    }

    /**
     * 설교요약 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/SGC_002_01-DETAIL")
    String sgc_002_01_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_002_01_DETAIL");
        try {

            model.addAttribute("dept_01", "말씀/찬양");
            model.addAttribute("dept_02", "설교요약");
            model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_02";
    }


    /**
     * 설교요약 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_002_01-DETAIL-S", method = RequestMethod.POST)
    String sgc_002_01_DETAIL_S(Board board, Model model) {
        logger.info("call Controller : sgc_002_01_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_02 :: #boardDetail_bind";
    }



    @RequestMapping("/SGC_002_02")
    String sgc_002_02(Model model) {
        model.addAttribute("dept_01", "말씀/찬양");
        model.addAttribute("dept_02", "성가대찬양");
        model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
        return "page/page_002/page_002_02";
    }

}