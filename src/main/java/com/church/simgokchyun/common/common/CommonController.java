package com.church.simgokchyun.common.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.common.vo.BoardLike;
import com.church.simgokchyun.common.vo.CallPageInfo;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CommonController {

    @Autowired
    CommonService comService;

    @Autowired
    CommonMapper mapper;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**************************************************************************************/
    /************************************  common START *******************************/
    /**************************************************************************************/


    /**
     * 자유게시판 게시글 수정 조회
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/control_like", method = RequestMethod.POST)
    String control_like(Board board, Model model, CallPageInfo pageInfo, HttpServletRequest request) {
        logger.info("call Controller : control_like");
        try {
            
            String like_yn = "";
            BoardLike vo = new BoardLike();

            vo.setBoard_div_cd(board.getBoard_div_cd());
            vo.setClick_div_cd("02");
            vo.setBoard_no(board.getBoard_no());
            vo.setClick_user_id(board.getUser_id());
            board.setClick_user_id(board.getUser_id());

            // 1. 좋아요를 클릭했는지 확인한다.
            like_yn = comService.getLikeYn(vo);

            //    1-1. 현재 좋아요ON 상태  => 삭제 여부를 Y 로 UPDATE
            if("Y".equals(like_yn)) {
                comService.offBoardLike(vo);

            //    1-2. 현재 종아요OFF 상태 => ON DUPLICATE KEY 사용해서 update or insert (삭제여부 = N)    
            } else {
                comService.onBoardLike(vo);
            }

            // 좋아요 총 개수, 좋아요 Y/N 을 model에 담아준다.

            // pageInfo.setBind_id("boardDetail_bind");
            // // model.addAttribute("like_yn", mapper.select_boardDetail(board));
            // model.addAttribute("like_yn", "Y");
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }

        return comService.getReturnUrl(pageInfo);
    }

    /**
     * 공통 게시글 부분조회 - likeIcon
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/common_likeInfo_search", method = RequestMethod.POST)
    String common_likeInfo_search(Board board, Model model, CallPageInfo pageInfo, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest  request) {
        logger.info("call Controller : common_likeInfo_search");
        try {
            model.addAttribute("boardDetail", comService.selectLikeInfo(board, userDetails, request));
            pageInfo.setBind_id("likeIcon");
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return comService.getReturnUrl(pageInfo);
    }

    /**
     * 공통 게시글 부분조회 - 댓글/답글 
     * @param model
     * @param board
     * @return
     */  
    @RequestMapping(value = "/common_boardReply_search", method = RequestMethod.POST)
    String common_boardReply_search(Board board, Model model, CallPageInfo pageInfo, @AuthenticationPrincipal PrincipalDetails userDetails, HttpServletRequest  request) {
        logger.info("call Controller : common_boardReply_search");
        try {
            model.addAttribute("boardReplyList", comService.select_boardReply(board));
            pageInfo.setBind_id("boardReplyList");
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return comService.getReturnUrl(pageInfo);
    }


    /**
     * 공통 이미지 업로드 (CKEDITOR 이미지업로드)
     * @param model
     * @param board
     * @return
     */
    @ResponseBody  
    @RequestMapping(value = "/common_img_upload", method = RequestMethod.POST)
    Map<String,Object> common_img_upload(@RequestParam Map<String,MultipartFile> MapFiles, Board board, Model model, @AuthenticationPrincipal PrincipalDetails userDetails) {
        logger.info("call Controller : common_img_upload");
        Map<String,Object> rtnMap = new HashMap<String,Object>();
        Map<String,Object> uplodFileInfo = new HashMap<String,Object>();

        try {
            uplodFileInfo = comService.fileSave(MapFiles.get("upload"),"01");

            rtnMap.put("uploaded", 1);
            rtnMap.put("fileName", uplodFileInfo.get("real_file_nm"));
            // rtnMap.put("url", uplodFileInfo.get("file_path"));
            rtnMap.put("url", "/uploadImg/"+uplodFileInfo.get("real_file_nm").toString());
            model.addAttribute("errYn", "N");
        } catch(Exception e) {
            model.addAttribute("errYn", "Y");
            logger.error(e.getMessage(), e);
        }
        return rtnMap;
    }


    /**************************************************************************************/
    /************************************  common END *********************************/
    /**************************************************************************************/

}