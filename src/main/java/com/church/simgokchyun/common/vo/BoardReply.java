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
public class BoardReply {
    
    private @NonNull String board_div_cd;
    private @NonNull String board_no;
    private @NonNull String reply_no;
    private String write_dt;
    private String user_id;
    private String reply_cntn;
    private String del_yn;
    private String chg_user_id;
    private String chg_dt;
    private String reg_user_id;
    private String reg_dt;

    // 테이블 외 정보들 
    private String time;
    private String user_kor_nm;
    private String reply_no_id;
    private String re_reply_no;
    private String update_yn;
    private String kind_of_reply;

    // 댓글 사용자 프로필 사용
    private String social_profile_url;
    
}
