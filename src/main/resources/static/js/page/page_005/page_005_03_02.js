'use strict';

$(document).ready(function (){

    basicSearch();
    $("#user_nm").val($("#user_nm_tmp").text());
});

// 댓글 작성 후 댓글 재 조회 
function basicSearch() {
    comDoAction("search","sgc_005_03");
}

function MoveMainBoard() {
    movePage('sgc_005_03');
}

function MoveBoardDetail() {
    movePage('sgc_005_03_02');
}