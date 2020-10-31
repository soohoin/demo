'use strict';

$(document).ready(function (){

    basicSearch();
    $("#user_nm").val($("#user_nm_tmp").text());
});

// 댓글 작성 후 댓글 재 조회 
function basicSearch() {
    comDoAction("search","SGC_007_02");
}

function MoveMainBoard() {
    movePage('SGC_007_02');
}

function MoveBoardDetail() {
    movePage('SGC_007_02_02');
}