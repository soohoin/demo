package com.church.simgokchyun.biz.sgc_006.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.church.simgokchyun.common.common.CommonService;
import com.church.simgokchyun.common.paging.Pagination;
import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.config.auth.PrincipalDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Sgc_006Controller {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommonService comService;



    /**************************************************************************************/
    /************************************  SGC_006_01 START *******************************/
    /**************************************************************************************/
    /**
     * 행사앨범 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/sgc_006_01")
    String sgc_006_01(Model model) {
        logger.info("call Controller : sgc_006_01");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "포토존");
            model.addAttribute("dept_02", "행사앨범");
            model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

        }  catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_01";
    }

    /**
     * 행사앨범 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/sgc_006_01-S", method = RequestMethod.POST)
    String sgc_006_01_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_006_01_S");

        try {
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("01");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 


            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_006/page_006_01 :: #boardList__bind";
    }


    /**
     * 행사앨범 게시글 작성 화면오픈
     * @param model
     * @return String
     */
    @RequestMapping("/sgc_006_01-CREATE")
    String sgc_006_01_CREATE(Model model) {
        logger.info("call Controller : sgc_006_01_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "포토존");
            model.addAttribute("dept_02", "행사앨범");
            model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            // model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_01_01";
    }

    /**
     * 행사앨범 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_006_01-SAVE", method = RequestMethod.POST)
    String sgc_006_01_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_006_01_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("12");  // 변경 필요함 , 화면에서 받아서 글 생성

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_01";
    }

    /**
     * 행사앨범 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/sgc_006_01-DETAIL")
    String sgc_006_01_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_006_01_DETAIL");
        try {

            model.addAttribute("dept_01", "포토존");
            model.addAttribute("dept_02", "행사앨범");
            model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_01_02";
    }

    /**
     * 행사앨범 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_006_01-DETAIL-S", method = RequestMethod.POST)
    String sgc_006_01_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest  request) {
        logger.info("call Controller : sgc_006_01_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_01_02 :: #boardDetail_bind";
    }
    /**************************************************************************************/
    /************************************  SGC_005_01 END *********************************/
    /**************************************************************************************/






    /**************************************************************************************/
    /************************************  SGC_006_02 START *******************************/
    /**************************************************************************************/
    /**
     * 행사앨범 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/sgc_006_02")
    String sgc_006_02(Model model) {
        logger.info("call Controller : sgc_006_02");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "포토존");
            model.addAttribute("dept_02", "동영상");
            model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

        }  catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_02";
    }

    /**
     * 행사앨범 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/sgc_006_02-S", method = RequestMethod.POST)
    String sgc_006_02_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_006_02_S");

        try {
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("01");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 


            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_006/page_006_02 :: #boardList__bind";
    }


    /**
     * 행사앨범 게시글 작성 화면오픈
     * @param model
     * @return String
     */
    @RequestMapping("/sgc_006_02-CREATE")
    String sgc_006_02_CREATE(Model model) {
        logger.info("call Controller : sgc_006_02_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "포토존");
            model.addAttribute("dept_02", "동영상");
            model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            // model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_02_01";
    }

    /**
     * 행사앨범 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_006_02-SAVE", method = RequestMethod.POST)
    String sgc_006_02_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_006_02_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("12");  // 변경 필요함 , 화면에서 받아서 글 생성

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_02";
    }

    /**
     * 행사앨범 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/sgc_006_02-DETAIL")
    String sgc_006_02_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_006_02_DETAIL");
        try {

            model.addAttribute("dept_01", "포토존");
            model.addAttribute("dept_02", "동영상");
            model.addAttribute("img_path", "imgs/page/page_006_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_02_02";
    }

    /**
     * 행사앨범 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_006_02-DETAIL-S", method = RequestMethod.POST)
    String sgc_006_02_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest request) {
        logger.info("call Controller : sgc_006_02_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_006/page_006_02_02 :: #boardDetail_bind";
    }
    /**************************************************************************************/
    /************************************  SGC_005_01 END *********************************/
    /**************************************************************************************/
    


}