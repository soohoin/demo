package com.church.simgokchyun.biz.sgc_002.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.church.simgokchyun.biz.sgc_002.service.Sgc_002Service;
import com.church.simgokchyun.common.common.CommonMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Sgc_002Controller {

    @Autowired
    Sgc_002Service service;

    @Autowired
    CommonService comService;

    @Autowired
    CommonMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 설교영상 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/sgc_002_01")
    String sgc_002_01(Model model) {
        logger.info("call Controller : sgc_002_01");
        try {

            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU02"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU02","01"));
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
     * 설교영상 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/sgc_002_01-S", method = RequestMethod.POST)
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
     * 설교영상 게시글 작성 화면오픈
     * @param model
     * @return String
     */    
    @RequestMapping("/sgc_002_01-CREATE")
    String sgc_002_01_CREATE(Model model) {
        logger.info("call Controller : sgc_002_01_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU02"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU02","01"));
            model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            model.addAttribute("WORSHIP_ORDER_CD", comService.select_com_code("WORSHIP_ORDER_CD"));
            model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

            // 3. 글 작성/수정 구분코드
            model.addAttribute("WRITE_YN", "Y");

            model.addAttribute("board", new Board());

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_01";
    }

    /**
     * 설교영상 게시글 저장 / 수정
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_002_01-SAVE", method = RequestMethod.POST)
    // String sgc_002_01_SAVE(@RequestParam("img_upload") MultipartFile file_01, Board board, Model model) {
    String sgc_002_01_SAVE(@RequestParam Map<String,MultipartFile> MapFiles, Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails) {
        logger.info("call Controller : sgc_002_01_SAVE");
        try {

            // String video_id = "";
            String img_id = "";

            // 1. 유저 정보를 셋팅한다.
            board.setUser_id(userDetails.getUser().getUser_id());
            board.setBoard_div_cd("01");

            logger.info("이미지 파일명 : " + MapFiles.get("img_upload").getOriginalFilename());
            // logger.info("동영상 파일명 : " + MapFiles.get("video_upload").getOriginalFilename());
            // 2. 업로드한 이미지 / 영상을 DB와 서버 경로에 저장하고 업로드 한 id를 board객체에 넣어준다.

            String write_yn = board.getWrite_yn();

            // 2-1. 이미지 처리
            if("Y".equals(write_yn) && MapFiles.get("img_upload") != null) {
                img_id = comService.fileSave(MapFiles.get("img_upload"),"01").get("file_id").toString();
                board.setPhoto_id(img_id);

            } else if("N".equals(write_yn) && MapFiles.get("img_upload") != null && "Y".equals(board.getChang_file_yn())) {
                // 기존 이미지 파일 삭제 && DB정보 삭제

                // 새로운 이미지 생성
                img_id = comService.fileSave(MapFiles.get("img_upload"),"01").get("file_id").toString();
                board.setPhoto_id(img_id);
                // 기존 파일을 삭제한다.
            }
            //     2-2. 동영상 처리
            // if(MapFiles.get("img_upload") != null) {
            //     video_id = comService.fileSave(MapFiles.get("img_upload"),"02");
            //     board.setVideo_id(video_id);
            // }

            // 3. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01";
    }

    /**
     * 설교영상 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/sgc_002_01-DETAIL")
    String sgc_002_01_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_002_01_DETAIL");
        try {

            model.addAttribute("dept_01", comService.getMenu_lv01("MENU02"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU02","01"));
            model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_02";
    }


    /**
     * 설교영상 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_002_01-DETAIL-S", method = RequestMethod.POST)
    String sgc_002_01_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest request) {
        logger.info("call Controller : sgc_002_01_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_02 :: #boardDetail_bind";
    }


    /**
     * 설교영상 게시글 수정 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/sgc_002_01_02-UPDATE")
    String sgc_005_01_02_UPDATE(Board board, Model model) {
        logger.info("call Controller : sgc_002_01_02_UPDATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", comService.getMenu_lv01("MENU02"));
            model.addAttribute("dept_02", comService.getMenu_lv02("MENU02","01"));
            model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
            model.addAttribute("board", board);
            model.addAttribute("page_name", "sgc_002_01_02");

            // 2. 사용 할 공통코드 만들기 
            model.addAttribute("WORSHIP_ORDER_CD", comService.select_com_code("WORSHIP_ORDER_CD"));
            model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

            // 3. 글 작성/수정 구분코드  N == 수정
            model.addAttribute("WRITE_YN", "N"); 

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_01";
    }

    /**
     * 설교영상 게시글 수정 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/sgc_002_01_02-UPDATE-S", method = RequestMethod.POST)
    String sgc_005_01_02_UPDATE_S(Board board, Model model) {
        logger.info("call Controller : sgc_005_01_02_UPDATE_S");
        try {
            model.addAttribute("board", mapper.select_boardDetail(board));
            model.addAttribute("WRITE_YN", "N");
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_002/page_002_01_01 :: #boardDetail_bind";
    }



    @RequestMapping("/sgc_002_02")
    String sgc_002_02(Model model) {
        model.addAttribute("dept_01", "말씀/찬양");
        model.addAttribute("dept_02", "성가대찬양");
        model.addAttribute("img_path", "imgs/page/page_002_bg.jpg");
        return "page/page_002/page_002_02";
    }

}