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
    
}

function doAction(acNm) {

    switch(acNm) {
        case "joinRuleApply":

            let rule_yn = $('input[name="rule_yn"]:checked').val();
            if(rule_yn == 'N') {
                alert('약관에 동의하지 않았습니다.');
            } else {
                movePage('joinForm')
            }
            break;
    }
}



