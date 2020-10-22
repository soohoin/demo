'use strict';

$(document).ready(function (){

    comDoAction("search","SGC_007_01");
});

function doAction(acNm) {
    let reqUrl, data, formName, bindId, callback;
    switch(acNm) {
        case "save":
            if(Validation()) {
                break;
            }
            // 사용자 id input value 에 셋팅
            $("#user_id").val($("#text_user_id").text());
            $("#reply_cntn").val($("#text_reply_cntn").val());

            reqUrl = "page_007_01_02-SAVE";
            data = null;
            formName = "hiddenForm";
            bindId = null;
            callback = null;
            ajaxCall(reqUrl,data,formName,bindId,callback);
            break;

    }
}


// 입력 값 유효성 체크
function Validation() {
    let isErr = false;
    let errMsg = "";

    if(isNull($("#text_reply_cntn").val())) {
        errMsg = "내용을 입력 하세요.";
        isErr = true;
        $("#text_reply_cntn").focus();
    }

    if(isErr) {
        alert(errMsg);
    }
    return isErr
}
