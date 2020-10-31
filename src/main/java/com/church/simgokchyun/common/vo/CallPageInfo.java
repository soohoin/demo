package com.church.simgokchyun.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallPageInfo {

    private String page_name;
    private int resources_level;
    private String bind_id;
    private String depth_01_name;
    private String depth_02_name;
    private String depth_03_name;
    private String depth_04_name;
}
