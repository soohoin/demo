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
public class Comcode {

    private @NonNull String cd_grp_enm;
    private String code;
    private String cd_grp_knm;
    private String code_knm;
    private String code_enm;
    private String order;
    private String use_yn;
    private String chg_user_id;
    private String chg_dt;
    private String reg_user_id;
    private String reg_dt;


    private String selected;
}
