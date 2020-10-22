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
public class BoardReReply {
    
    private @NonNull String board_div_cd;
    private @NonNull String board_no;
    private @NonNull String reply_no;
    private @NonNull String reReply_no;
    private String write_dt;
    private String user_id;
    private String reply_cntn;
    private String del_yn;
    private String chg_user_id;
    private String chg_dt;
    private String reg_user_id;
    private String reg_dt;

    
}
