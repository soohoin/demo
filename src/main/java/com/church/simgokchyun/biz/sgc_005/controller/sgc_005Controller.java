package com.church.simgokchyun.biz.sgc_005.controller;

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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class sgc_005Controller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommonService comService;
    


    /**************************************************************************************/
    /************************************  SGC_005_01 START *******************************/
    /**************************************************************************************/
    /**
     * 남전도회 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/SGC_005_01")
    String sgc_005_01(Model model) {
        logger.info("call Controller : sgc_005_01");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "남전도회");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

        }  catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_01";
    }

    /**
     * 남전도회 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/SGC_005_01-S", method = RequestMethod.POST)
    String sgc_005_01_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_005_01_S");

        try {
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("04");
            board.setBoard_div_cd2("05");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 


            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_005/page_005_01 :: #boardList__bind";
    }


    /**
     * 남전도회 게시글 작성 화면오픈
     * @param model
     * @return String
     */
    @RequestMapping("/SGC_005_01-CREATE")
    String sgc_005_01_CREATE(Model model) {
        logger.info("call Controller : sgc_005_01_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "남전도회");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            // model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_01_01";
    }

    /**
     * 남전도회 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_005_01-SAVE", method = RequestMethod.POST)
    String sgc_005_01_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_005_01_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("04");  // 변경 필요함 , 화면에서 받아서 글 생성

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_01";
    }

    /**
     * 남전도회 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/SGC_005_01-DETAIL")
    String sgc_005_01_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_005_01_DETAIL");
        try {

            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "남전도회");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_01_02";
    }

    /**
     * 남전도회 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_005_01-DETAIL-S", method = RequestMethod.POST)
    String sgc_005_01_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest request) {
        logger.info("call Controller : sgc_005_01_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_01_02 :: #boardDetail_bind";
    }
    /**************************************************************************************/
    /************************************  SGC_005_01 END *********************************/
    /**************************************************************************************/





    /**************************************************************************************/
    /************************************  SGC_005_02 START *******************************/
    /**************************************************************************************/
    /**
     * 여전도회 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/SGC_005_02")
    String sgc_005_02(Model model) {
        logger.info("call Controller : sgc_005_02");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "여전도회");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

        }  catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_02";
    }

    /**
     * 여전도회 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/SGC_005_02-S", method = RequestMethod.POST)
    String sgc_005_02_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_005_02_S");

        try {
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("06");
            board.setBoard_div_cd2("07");
            board.setBoard_div_cd3("08");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 

            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_005/page_005_02 :: #boardList__bind";
    }


    /**
     * 여전도회 게시글 작성 화면오픈
     * @param model
     * @return String
     */
    @RequestMapping("/SGC_005_02-CREATE")
    String sgc_005_02_CREATE(Model model) {
        logger.info("call Controller : sgc_005_02_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "여전도회");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            // model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_02_01";
    }

    /**
     * 여전도회 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_005_02-SAVE", method = RequestMethod.POST)
    String sgc_005_02_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_005_02_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("06");  // 변경 필요함 , 화면에서 받아서 글 생성

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_02";
    }

    /**
     * 여전도회 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/SGC_005_02-DETAIL")
    String sgc_005_02_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_005_02_DETAIL");
        try {

            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "여전도회");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_01_02";
    }

    /**
     * 여전도회 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_005_02-DETAIL-S", method = RequestMethod.POST)
    String sgc_005_02_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest request) {
        logger.info("call Controller : sgc_005_02_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_02_02 :: #boardDetail_bind";
    }
    /**************************************************************************************/
    /************************************  SGC_005_02 END *********************************/
    /**************************************************************************************/




    /**************************************************************************************/
    /************************************  SGC_005_03 START *******************************/
    /**************************************************************************************/
    /**
     * 선교부 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/SGC_005_03")
    String sgc_005_03(Model model) {
        logger.info("call Controller : sgc_005_03");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "선교부");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

        }  catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_03";
    }



    /**
     * 선교부 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/SGC_005_03-S", method = RequestMethod.POST)
    String sgc_005_03_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_005_03_S");

        try {
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("09");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 

            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_005/page_005_03 :: #boardList__bind";
    }


    /**
     * 선교부 게시글 작성 화면오픈
     * @param model
     * @return String
     */
    @RequestMapping("/SGC_005_03-CREATE")
    String sgc_005_03_CREATE(Model model) {
        logger.info("call Controller : sgc_005_03_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "선교부");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            // model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_03_01";
    }

    /**
     * 선교부 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_005_03-SAVE", method = RequestMethod.POST)
    String sgc_005_03_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_005_03_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("09");  // 변경 필요함 , 화면에서 받아서 글 생성

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_03";
    }

    /**
     * 선교부 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/SGC_005_03-DETAIL")
    String sgc_005_03_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_005_03_DETAIL");
        try {

            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "선교부");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_03_02";
    }

    /**
     * 선교부 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */
    @RequestMapping(value = "/SGC_005_03-DETAIL-S", method = RequestMethod.POST)
    String sgc_005_03_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest request) {
        logger.info("call Controller : sgc_005_03_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_03_02 :: #boardDetail_bind";
    }
    /**************************************************************************************/
    /************************************  SGC_005_03 END  ********************************/
    /**************************************************************************************/




    /**************************************************************************************/
    /************************************  SGC_005_04 START *******************************/
    /**************************************************************************************/
    /**
     * 찬양단 화면 오픈
     * @param model
     * @return
     */
    @RequestMapping("/SGC_005_04")
    String sgc_005_04(Model model) {
        logger.info("call Controller : sgc_005_04");
        try {
            // 1. 메뉴 depth 명과 bg_img를 가져온다. - 공통서비스 호출  - 추후 메뉴매핑 테이블 완성 후 변경하기 현재는 hard 코딩
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "찬양단");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 검색 조건의 날짜를 가져온다.
            List<Map<String,Object>> years = comService.getYears(5);
            model.addAttribute("years", years);

        }  catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_04";
    }



    /**
     * 찬양단 글 목록 조회
     * @param model
     * @param reqPagination
     * @param board
     * @return String
     */
    @RequestMapping(value = "/SGC_005_04-S", method = RequestMethod.POST)
    String sgc_005_04_S(Model model, Pagination reqPagination, Board board ) {
        logger.info("call Controller : sgc_005_04_S");

        try {
            // 1. 공통 paging 서비스 호출
            board.setBoard_div_cd("10");
            comService.getPaginationInfo(comService.getTotalCnt(board), reqPagination, model, board); 

            model.addAttribute("boardList", comService.select_boardList(board));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
            return "page/page_005/page_005_04 :: #boardList__bind";
    }


    /**
     * 찬양단 게시글 작성 화면오픈
     * @param model
     * @return String
     */
    @RequestMapping("/SGC_005_04-CREATE")
    String sgc_005_04_CREATE(Model model) {
        logger.info("call Controller : sgc_005_04_CREATE");
        try {

            // 1. 메뉴명, 배경이미지 셋팅
            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "찬양단");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");

            // 2. 사용 할 공통코드 만들기 
            // model.addAttribute("VIDEO_DIV_CD", comService.select_com_code("VIDEO_DIV_CD"));

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_04_01";
    }

    /**
     * 찬양단 게시글 저장
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/SGC_005_04-SAVE", method = RequestMethod.POST)
    String sgc_005_04_SAVE(Board board, Model model) {
        logger.info("call Controller : sgc_005_04_SAVE");
        try {
            
            // 1. 유저 정보를 셋팅한다.
            board.setUser_id("100001");
            board.setBoard_div_cd("10");  // 변경 필요함 , 화면에서 받아서 글 생성

            // 2. 새 글을 INSERT 한다.
            comService.insertBoard(board);
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_04";
    }

    /**
     * 찬양단 게시글 상세 화면오픈
     * @param model
     * @param board
     * @return String
     */  
    @RequestMapping("/SGC_005_04-DETAIL")
    String sgc_005_04_DETAIL(Board board, Model model) {
        logger.info("call Controller : sgc_005_04_DETAIL");
        try {

            model.addAttribute("dept_01", "자치기관");
            model.addAttribute("dept_02", "찬양단");
            model.addAttribute("img_path", "imgs/page/page_005_bg.jpg");
            model.addAttribute("board", board);

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_04_02";
    }

    /**
     * 찬양단 게시글 상세 조회
     * @param model
     * @param board
     * @return
     */
    @RequestMapping(value = "/SGC_005_04-DETAIL-S", method = RequestMethod.POST)
    String sgc_005_04_DETAIL_S(Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest request) {
        logger.info("call Controller : sgc_005_04_DETAIL_S");
        try {
            model.addAttribute("boardDetail", comService.select_boardDetail(board, userDetails, request));
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "page/page_005/page_005_04_02 :: #boardDetail_bind";
    }
    /**************************************************************************************/
    /************************************  SGC_005_04 END *********************************/
    /**************************************************************************************/


}