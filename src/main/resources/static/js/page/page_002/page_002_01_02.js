'use strict';

$(document).ready(function (){

    doAction("search");
});

// action (CRUD 등등...) 을 수행한다.
function doAction(acNm) {
    let reqUrl, formName, bindId, callback;
    switch(acNm) {
        case "search":
            reqUrl = "SGC_002_01-DETAIL-S";
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
}