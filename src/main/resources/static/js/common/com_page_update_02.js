'use strict';

$(document).ready(function (){
    
    $("#user_id").val($("#text_user_id").text());
    basicSearch();
});


// 댓글 작성 후 댓글 재 조회 
function basicSearch() {
    comDoAction("boardUpdateSearch",$("#page_name").val());
}