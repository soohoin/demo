'use strict';

$(document).ready(function (){

    basicSearch();
    $("#user_nm").val($("#user_nm_tmp").text());
});

// 댓글 작성 후 댓글 재 조회 
function basicSearch() {
    doAction("search","sgc_002_01");
}

function MoveMainBoard() {
    movePage('sgc_002_01');
}

function MoveBoardDetail() {
    movePage('sgc_002_01_02');
}


// action (CRUD 등등...) 을 수행한다.
function doAction(acNm) {
    let reqUrl, formName, bindId, callback;
    switch(acNm) {
        case "search":
            reqUrl = "sgc_002_01-DETAIL-S";
            formName = "hiddenForm";
            bindId = "boardDetail_bind";
            callback = setVideo;
            ajaxCall(reqUrl,null,formName,bindId,callback);
            break;
    }
}

// 유튜브 & 동영상을 bind 한다.
function setVideo() {
    $("#youtube_video").html($("#youtube_src").val());
    update_callback_y();
    $videoIframe = $('iframe');
    $videoIframe.css('position','absolute');
    $videoIframe.css('width', '100%');
    $videoIframe.css('height', '100%');
}