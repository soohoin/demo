'use strict';

$(document).ready(function (){
    // ClassicEditor
    //     .create(document.querySelector('#editor'), {
    //         // 제거 할 플러그인(배열)
    //         // plugins: [Essentials, Paragraph, Bold, Italic],
    //         removePlugins:[ 'ImageUpload',],
    //         // toolbar:['bold','italic','bulletedList','numberedList','blockQuote']
    //     })
    //     .catch( error => {
    //         console.log(error);
    //     });
});

// action (CRUD 등등...) 을 수행한다.
function doAction(acNm) {
    let reqUrl, formName, data, callback;
    switch(acNm) {
        case "save":
            ckUpdate(); // 공통에서 사용하는 함수 (common.js)
            if(!confirm("저장 하시겠습니까?") || validation()) return;
            reqUrl = "sgc_006_01-SAVE";
            formName = "frm";
            data = null;
            callback = afterSave;
            ajaxCallUpload(reqUrl,data,formName,null,callback);
            break;
    }
}

function afterSave() {
    // alert("저장완료");
    window.location = '/sgc_006_01';
}

// 입력 값 유효성 체크
function validation() {
    let errYn = false;
    let errMsg = "";

    if(isNull($("#board_title").val())) {
        errMsg = "제목";
        errYn = true;
        $("#board_title").focus();
    } else if($("#img_upload").val() == "03" && isNull($("#img_upload").val())) {
        errMsg = "이미지 업로드 파일";
        errYn = true;
        $("#img_upload").focus();
    }


    if(errYn) {
        alert(errMsg + " 은(는) 필수 입니다.")
    }
    return errYn
}