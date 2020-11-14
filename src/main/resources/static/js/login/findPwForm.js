'use strict';

$(document).ready(function () {

    initEvent();
});


// 이벤트 생성 
function initEvent() {

    $("#executeBtn").on("click",function () {
        doAction("execJoin");
    });
}

function doAction(acNm) {
    let data, reqUrl, formName, bindId, callback;
    switch (acNm) {
        case "execJoin":
            
            // 이메일 형식 확인
            if(validn()) {
                break;
            }
            reqUrl = "findPw";
            data = null;
            formName = "findEmailForm";
            bindId = null
            callback = movePage("loginForm");
            ajaxCall(reqUrl, data, formName, bindId, callback);

            break
    }
}

// 이메일 형식 유효성 검사
function validn() {
    let isErr = false;
    let msg;
    let checkMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);   // 이메일 형식
    let userEmail = $("#email_addr").val();

    if(isNull(userEmail)) {
        msg = "이메일을 입력하세요";
        isErr = true;
        $("#email_addr").focus();    
    }

    if(isErr) {
        alert(msg);
    }
    return isErr;
}

