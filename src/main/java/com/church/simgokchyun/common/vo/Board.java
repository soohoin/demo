package com.church.simgokchyun.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    
    private @NonNull String board_div_cd;
    private String board_div_cd2;
    private String board_div_cd3;
    private @NonNull String board_no;
    private String write_dt;
    private String user_id;
    private String board_title;
    private String board_cntn;
    private String bible_index_cntn;
    private String video_id;
    private String photo_id;
    private String video_div_cd;
    private String youtube_ifrm_src;
    private String select_cnt;
    private String favorit_cnt;
    private String worship_order_cd;
    private String del_yn;
    private String chg_user_id;
    private String chg_dt;
    private String reg_user_id;
    private String reg_dt;


    // 테이블 외 정보들 
    private String real_file_nm;
    // private String video_path;
    private int start_index;                    // MYSQL 쿼리에서 페이징을 위한 변수
    private int page_size;                      // MYSQL 쿼리에서 페이징을 위한 변수
    private String search_text;                 // 조회 검색조건 text
    private String strt_write_dt;
    private String end_write_dt;                
    private String board_div_nm;                // 게시판 구분명
    private String worship_order_nm;            // 예배순서명
    private String page_name;                   // call 요청 한 page name
    private String callback_yn;                 // 화면에서 callback 여부
    private String click_user_id;               // 좋아요,글조회 누른 유저 id
    private String favorit_yn_of_click_user;    // 접속유저의 좋아요 클릭 여부
    private String replys_cnt;                  // 댓글,답글 총 개수

    // 조인해서 사용할 유저정보
    private String social_profile_url;
    private String user_kor_nm;
    private String user_nic_nm;

}
