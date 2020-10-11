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
public class User {
    
    private @NonNull String user_id;
    private String email_addr;
    private String user_nm;
    private String user_nic_nm;
    private String password;
    private String phone_num;
    private String role_cd;
    private String church_role_cd;
    private String auth_yn;
    private String auth_key;
    private String use_yn;
    private String chg_user_id;
    private String chg_dt;
    private String reg_user_id;
    private String reg_dt;


    private String role_nm;
}


