'use strict';

let msg;
let targetNm;       // 임시 메세지 상위 태그 선택자
let tmpClassNm;     // 임시 메세지 태그 class 명
let textColor;      // 임세 메세지 색상
let emailDupYn;     // 이메일 중복여부 
let joinBtnClickYn = 'N'; // 회원가입버튼 중복방지

$(document).ready(function () {

    initEvent();
});


// 이벤트 생성 
function initEvent() {

    $("#email_addr").change(function () {
        doAction("emailCheck");
    });

    $("#password").change(function () {
        validnPassword();
    });

    $("#password_confirm").change(function () {
        validnPasswordConfirm();
    });

    $("#user_nm").change(function () {
        validnName();
    });

    $("#user_nic_nm").change(function () {
        validnNicName();
    });

}


function doAction(acNm) {``
    let reqData;
    switch (acNm) {
        case "emailCheck":

            // 이메일 형식 유효성 체크
            if(validnMail()) {
                break;
            }
            reqData = $("#joinForm").serialize();
            $.ajax({
                url: "emailCheck",
                data: reqData,
                type: "POST",
                cache: false
            }).done(function (resData) {
                if (resData.errYn == "Y") {
                    alert("에러 : " + resData.errMsg);
                } else {
                    emailDupYn = resData.emailDupYn;
                    if (resData.emailDupYn == "N") {
                        // appendTmpMsg("사용가능","#email_addr_vali","temp_msg_mail","greenyellow");
                        $("#email_addr_vali").removeClass('fas fa-times');
                        $("#email_addr_vali").addClass('fas fa-check');
                    } else {
                        $("#email_addr_vali").addClass('fas fa-times');
                        commonCheck(true, "#email_addr_vali", "temp_msg_mail","이미 사용 중 입니다.");
                    }
                }
            });
            break;
        case "execJoin":
            // 전체 항목 검사
            if(validnTotal()) {
                break;
            }
            $("#joinBtn").prop("disabled", true);

            reqData = $("#joinForm").serialize();
            $.ajax({
                url: "join",
                data: reqData,
                type: "POST",
                cache: false
            }).done(function (resData) {
                if (resData.errYn == "Y") {
                    alert("에러 : " + resData.errMsg);
                } else {
                    alert("회원가입 완료!");
                    movePage("loginForm");
                }
                $("#joinBtn").prop("disabled", false);
            });


            break    
    }
}


// 전체 입력 폼 validation 수행
function validnTotal() {
    return doAction("emailCheck") || emailDupYn == 'Y' || validnPassword() || validnPasswordConfirm() || validnName() || validnNicName();
}

// 이메일 형식 유효성 검사
function validnMail() {
    let isErr = false;
    
    let checkMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);   // 이메일 형식
    let userEmail = $("#email_addr").val();

    targetNm = "#email_addr_vali";
    tmpClassNm = "temp_msg_mail";
    textColor = "red";

    if(isNull(userEmail)) {
        msg = "이메일은 필수 입니다.";
        isErr = true;
    } else if(!checkMail.test(userEmail)) {
        msg = "이메일 형식이 맞지 않습니다.";
        isErr = true;
    } else {
        msg = "사용가능";
    }

    commonCheck(isErr, targetNm, tmpClassNm, msg);
    return isErr;
}


// 페스워드 유효성 검사
function validnPassword() {
    let isErr = false;

    let checkPw01 = RegExp(/^[a-zA-Z0-9]{8,20}$/);    // 숫자,영문 조합 8~ 20자리   
    let checkPw02 = RegExp(/(\w)\1\1\1/);             // 같은 문자를 4번이상 반복 x
    
    let checkNum  = $("#password").val().search(/[0-9]/g);      // 숫자체크
    let checkEng  = $("#password").val().search(/[a-z]/ig);     // 영문체크
    
    let userPw = $("#password").val();            // 비교 할 사용자 페스워드
    let userEmail = $("#email_addr").val();

    targetNm   = "#password_vali";                // 유효성 아이콘 ID
    tmpClassNm = "temp_msg_password";             // 유효성 메세지 클레스명
    textColor  = "red";                           // 유효성 메세지 색상

    if(isNull(userPw)) {
        msg = "비밀번호는 필수 입니다.";
        isErr = true;
    } else if(!checkPw01.test(userPw) || checkNum < 0 || checkEng < 0) {
        msg = "숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.";
        isErr = true;
    } else if(checkPw02.test(userPw)) {
        msg = "같은 문자를 4번이상 반복 할 수 없습니다.";
        isErr = true;
    } else if(!isNull(userEmail) && userPw.search(userEmail) > -1) {
        msg = "비밀번호에 아이디를 포함 할 수 없습니다.";
        isErr = true;
    } else {
        msg = "사용가능";
    }

    commonCheck(isErr, targetNm, tmpClassNm, msg);
    return isErr;
}


// 페스워드 확인 유효성 검사
function validnPasswordConfirm() {
    let isErr = false;
    
    let userPw = $("#password").val();            // 비교 할 사용자 페스워드
    let userPwCheck = $("#password_confirm").val();   // 페스워드 확인

    targetNm = "#password_confirm_valdtn";
    tmpClassNm = "temp_msg_password_confirm";
    textColor = "red";

    if(isNull(userPwCheck)) {
        msg = "비밀번호 확인은 필수 입니다.";
        isErr = true;
    } else if(userPw != userPwCheck) {
        msg = "비밀번호가 일치하지 않습니다.";
        isErr = true;
    } else {
        msg = "비밀번호 일치";
    }

    commonCheck(isErr, targetNm, tmpClassNm, msg);
    return isErr;
}

// 이름 확인 유효성 검사
function validnName() {
    let isErr = false;
    
    let checkName = RegExp(/^[가-힣]+$/);     // 이름 유효성 체크 
    let userName  = $("#user_nm").val();     // 비교 할 사용자 페스워드
    
    targetNm = "#user_nm_valdtn";
    tmpClassNm = "temp_msg_user_nm";
    textColor = "red";

    if(isNull(userName)) {
        msg = "이름은 필수 입니다.";
        isErr = true;
    } else if(!checkName.test(userName)) {
        msg = "정상적인 한글 이름이 아닙니다.";
        isErr = true;
    } else if(userName.length == 1) {
        msg = "최소 두글자 이상 입력 하세요";
        isErr = true;
    } else {
        msg = "사용가능";
    }

    commonCheck(isErr, targetNm, tmpClassNm, msg);
    return isErr;
}



// 닉네임 확인 유효성 검사
function validnNicName() {
    let isErr = false;
    
    let checkNicName = RegExp(/^[가-힣]+$/);     // 이름 유효성 체크 
    let userNicName  = $("#user_nic_nm").val();     // 비교 할 사용자 페스워드

    targetNm = "#user_nic_nm_valdtn";
    tmpClassNm = "temp_msg_user_nic_nm";
    textColor = "red";

    if(isNull(userNicName)) {
        msg = "닉네임은 필수 입니다.";
        isErr = true;
    }
    commonCheck(isErr, targetNm, tmpClassNm, msg);
    return isErr;
}

// 공통 에러체크 
function commonCheck(isErr, targetNm, tmpClassNm, msg) {
    if(!isErr){
        textColor = "greenyellow";
        $(targetNm).removeClass('fas fa-times');
        $(targetNm).addClass('fas fa-check');
    } else {
        textColor = "red";
        $(targetNm).addClass('fas fa-times');
    } 
    appendTmpMsg(msg,targetNm,tmpClassNm,textColor);
}


// targetNm 요소 뒤에 임시 메세지 태그&내용 추가
function appendTmpMsg(errMsg, targetNm, tmpClassNm, textColor) {
    $( 'p' ).remove( '.'+tmpClassNm );
    $(targetNm).after("<p class='"+tmpClassNm+"' style='color: "+ textColor +";'>"+errMsg+"</p>");
}


