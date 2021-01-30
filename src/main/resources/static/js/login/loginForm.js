'use strict';

$(document).ready(function () {

    initEvent();
});


// 이벤트 생성 
function initEvent() {

    $(".loginBtn").on("click",function () {
        doAction("execJoin");
    });

    window.addEventListener('keydown',function(e){
        if(e.key == 'Enter' || e.code == 'Enter') {
            $(".loginBtn").trigger('click');
        }
    });
  
}

function doAction(acNm) {
    let reqData;
    let url;
    switch (acNm) {
        case "execJoin":
            
            // 이메일 형식 확인
            if(validn()) {
                break;
            }

            $("#loginForm").submit();
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
    } else if(!checkMail.test(userEmail)) {
        msg = "이메일 형식이 맞지 않습니다.";
        isErr = true;
        $("#email_addr").focus();    
    } else if(isNull($("#user_pw").val())) {
        msg = "비밀번호를 입력 하세요";
        isErr = true;
        $("#user_pw").focus();    
    }

    if(isErr) {
        alert(msg);
    }
}

