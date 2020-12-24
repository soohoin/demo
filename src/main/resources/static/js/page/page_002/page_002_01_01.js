'use strict';

$(document).ready(function (){
    tranForm();
    $("#video_div_cd").change(function() {
        tranForm();
    });

    let write_yn = $("#write_yn").val();
    if(write_yn == "N") {
        doAction("search");
    }
});

// action (CRUD 등등...) 을 수행한다.
function doAction(acNm) {
    let reqUrl, formName, data, callback, bindId;
    switch(acNm) {
        case "save":
            if(!confirm("저장 하시겠습니까?") || validation()) return;
            reqUrl = "sgc_002_01-SAVE";
            formName = "frm";
            data = null;
            bindId = null;
            callback = afterSave;
            ajaxCallUpload(reqUrl,data,formName,bindId,callback);
            break;
        case "search":
            reqUrl = "sgc_002_01_02-UPDATE-S";
            data = null;
            formName = "frm";
            bindId = "boardDetail_bind";
            callback = setData;
            ajaxCall(reqUrl,data,formName,bindId,callback);
            break;
        case "change_file_update":
            $("#chang_file_yn").val("Y");
            $("#file_cancle").show();
            $("#img_upload").show();
            $("#file_update").hide();
            $("#origin_file_nm").hide();
            
            break;
        case "change_file_cancle":
            $("#chang_file_yn").val("N");
            $("#file_cancle").hide();
            $("#img_upload").hide();
            $("#file_update").show();
            $("#origin_file_nm").show();
            break;    
    }
}

function afterSave() {
    // alert("저장완료");
    window.location = '/sgc_002_01';
}

function setData() {
    let worwhip_order_cd = $("#worwhip_order_cd_tmp").val();
    $("#worship_order_cd").val(worwhip_order_cd);
}

// 입력 값 유효성 체크
function validation() {
    let errYn = false;
    let errMsg = "";

    if(isNull($("#board_title").val())) {
        errMsg = "제목";
        errYn = true;
        $("#board_title").focus();
    } else if(isNull($("#bible_index_cntn").val())) {
        errMsg = "본문";
        errYn = true;
        $("#bible_index_cntn").focus();
    } else if($("#video__div_cd").val() == "02" && isNull($("#youtube_ifrm_src").val())) {
        errMsg = "youtube 소스코드";
        errYn = true;
        $("#youtube_ifrm_src").focus();
    } else if($("#video__div_cd").val() == "03" && isNull($("#video_upload").val())) {
        errMsg = "영상 업로드 파일";
        errYn = true;
        $("#video_upload").focus();
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

// 업로드 영상 구분에 따라서 입력 폼 제어
function tranForm() {
    let div_cd = $("#video_div_cd option:selected").val();
    if(div_cd == "01") {
        $(".youtube_src").hide();
        $(".video_upload").hide();

        $("#video_upload").val('');
        $("#youtube_ifrm_src").val('');
    } else if(div_cd == "02") {
        $(".video_upload").hide();
        $(".youtube_src").show();

        $("#video_upload").val('');
    } else if(div_cd == "03") {
        $(".youtube_src").hide();
        $(".video_upload").show();

        $("#youtube_ifrm_src").val('');
    }
}