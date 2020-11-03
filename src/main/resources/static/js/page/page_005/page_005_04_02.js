'use strict';

$(document).ready(function (){

    basicSearch();
    $("#user_nm").val($("#user_nm_tmp").text());
});

// 기본 상세화면 모든 데이터 조회
function basicSearch() {
    comDoAction("search","sgc_005_04");
}

// 부분조회 - 답글 / 댓글
function basicSearch_02() {
    comDoAction("commonBoardReplySearch","sgc_005_04");
}

// 부분조회 - 좋아요 영역
function basicSearch_03() {
    comDoAction("commonSearchLikeIcon","sgc_005_04");
}

function MoveMainBoard() {
    movePage('sgc_005_04');
}

function MoveBoardDetail() {
    movePage('sgc_005_04_02');
}