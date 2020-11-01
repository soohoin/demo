'use strict';

$(document).ready(function (){
    
    doAction("search");
});

// action (CRUD 등등...) 을 수행한다.
function doAction(acNm, pageNo) {
    let reqUrl, formName, callBack, bindId, data;
    
    switch(acNm) {
        case "search":

            // 1. url 
            reqUrl = "sgc_006_01-S";

            // 2. form data 일 경우 form id 지정
            formName = "";

            // 3. bind id 
            bindId = "boardList__bind";

            // 4. 조회 조건 & 조회 페이지 데이터 셋팅
            
            // 4-1. page 버튼이 없을 시 첫 페이지를 기본으로 한다.
            if(isNull(pageNo)) pageNo = 1;    

            // 4-2. 조회조건  write_dt, worship_order_cd,  

            //$("#video_div_cd option:selected").val();
            let year = $("#year_select_box option:selected").val();
            let month = $("#month_select_box option:selected").val(); 
            let strt_write_dt;
            let end_write_dt;

            if(isNull(month)) {
                strt_write_dt = year + "0101";
                end_write_dt  = year + "1231"
            } else {    
                strt_write_dt = year + month + "01";
                end_write_dt  = year + month + "31";
            }
            
            // 4-3. LIKE 조건  검색구분(board_title, board_cntn, user_id ), 입력TEXT
            let search_div_cd = $("#search_div_cd option:selected").val(); 
            let board_title, board_cntn, user_kor_nm;

            if(search_div_cd == "1") {
                board_title = $("#search_text").val();
            } else if(search_div_cd == "2"){
                board_cntn = $("#search_text").val();
            } else if(search_div_cd == "3"){
                user_kor_nm = $("#search_text").val();
            }

            data = {"page":pageNo,"strt_write_dt":strt_write_dt,"end_write_dt":end_write_dt,
                    "board_title":board_title,"board_cntn":board_cntn,"user_kor_nm":user_kor_nm}
            ajaxCall(reqUrl,data,formName,bindId);
            break;    
    }
}

// 상세 페이지 이동 
function goDetail(board_no) {
    $("#page_move"+ board_no).get(0).click();
}