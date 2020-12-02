'use strict';
checkIE();
let navbar = "";
let replyFormNo = 0;          // 상세 게시글의 답글쓰기 form의 댓글번호 
let window_width;             // window width size
let navbarWidth;              // 모바일 버전에서 메뉴바 상세 hover 이벤트 제어변수
let isMobileMenuOn = false;   // 모바일 버전에서 메뉴바의 show상태
let position = 0;             // 스크롤이 위/아래 인지 구분하기 위한 변수
let lv1_menu_href_array = []; // href 속성을 array에 넣어 뒀다가 다시 사용하기 위한 array
let lv1_menus_for_a;        
let lv1_menus_for_p;
let isScrollEvent = false;

// iframe 반응형 관련 변수
var $videoIframe;
var responsiveHeight;
$(document).ready(function () {
    lv1_menus_for_a = $(".navbar__menu__ul__item > p > a");
    lv1_menus_for_p = $(".navbar__menu__ul__item > p");
    comEventInit();
});


// 이벤트 생성 
function comEventInit() {
    navbar = document.querySelector('#navbar');
    // 메뉴 hover 이벤트
    $(".navbar__menu__ul").hover(function () {
        navbarWidth = navbar.getBoundingClientRect().width;
        if(navbarWidth >= 768) { 
            $('.sub__menu__ul').removeClass('hide');
            $('.sub__menu__ul').addClass('showme');
            $('.menu__bg').removeClass('hide');
        }
    }, function () {
        navbarWidth = navbar.getBoundingClientRect().width;
        if(navbarWidth >= 768) { 
            $('.sub__menu__ul').addClass('hide');
            $('.sub__menu__ul').removeClass('showme');
            $('.menu__bg').addClass('hide');
        }
    });

    // 모바일 레이아웃 일 때 햄버거 flag 기능
    $(".navbar__toggle-btn").click(function(event){
        event.stopPropagation();
        isScrollEvent = true;
        if(isMobileMenuOn) {
            $(".navbar__loginjoin").hide();
            $(".navbar__menu").hide();
            if(lv2_on_menu_object != -1) {
                lv2_on_menu_object.addClass('hide');
                lv2_on_menu_index = -1;
                lv2_on_menu_object = -1;
            }
            isMobileMenuOn = false;
            transAttr_pcVersionEvent();
        } else {
            $(".navbar__loginjoin").show();
            $(".navbar__menu").show();
            isMobileMenuOn = true;
            transAttr_mobileVersionEvent();
        }
    });

    // 메뉴바 숨기기 & 스크롤시 변환이벤트
    $(window).on('scroll', function (event) {
        event.stopPropagation();
        let isDown = true;
        if (window.scrollY > position) {
            isDown = true;
        } else {
            isDown = false;
        }
        position = window.scrollY;
        let window_width = document.querySelector('body').getBoundingClientRect().width;
        
        if(window_width > 769 ) {
            if(window.scrollY > 200 && isDown) {
                navbar.classList.add('hide');
            } else if(window.scrollY > 200 && !isDown) {
                navbar.classList.remove('hide');
            }
        }
    });

    // 화면 사이즈 변경 이벤트 
    $(window).on('resize', function() { 
        window_width = document.querySelector('body').getBoundingClientRect().width;
        if(window_width > 769) {
            $(".navbar__loginjoin").css('display','flex');
            $(".navbar__menu").show();
        } else {
            $(".navbar__loginjoin").hide();
            $(".navbar__menu").hide();
            transAttr_pcVersionEvent();
            isMobileMenuOn = false;
        }

        if(lv2_on_menu_object != -1) {
            lv2_on_menu_object.addClass('hide');
            lv2_on_menu_index = -1;
            lv2_on_menu_object = -1;
        }
    });

    // header 대메뉴 변경 이벤트 
    $("#dept_lv01").on("change", function () {
        let lv01_cd = $("#dept_lv01 option:selected").val();

        switch(lv01_cd) {
            case "MENU01":
                movePage("sgc_001_01");
                break;
            case "MENU02":
                movePage("sgc_002_01");
                break;    
            case "MENU03":
                movePage("sgc_003_01");
                break;    
            case "MENU04":
                movePage("sgc_004_01");
                break;    
            case "MENU05":
                movePage("sgc_005_01");
                break;    
            case "MENU06":
                movePage("sgc_006_01");
                break;    
            case "MENU07":
                movePage("sgc_007_01");
                break;    
        }
    });

    // header 중메뉴 변경 이벤트 
    $("#dept_lv02").on("change", function () {
        let lv01_cd = $("#dept_lv01 option:selected").val();
        let lv02_cd = $("#dept_lv02 option:selected").val();

        switch(lv01_cd) {
            case "MENU01":
                movePage("sgc_001_"+ lv02_cd);
                break;    
            case "MENU02":
                movePage("sgc_002_"+ lv02_cd);
                break;    
            case "MENU03":
                movePage("sgc_003_"+ lv02_cd);
                break;    
            case "MENU04":
                movePage("sgc_004_"+ lv02_cd);
                break;    
            case "MENU05":
                movePage("sgc_005_"+ lv02_cd);
                break;    
            case "MENU06":
                movePage("sgc_006_"+ lv02_cd);
                break;    
            case "MENU07":
                movePage("sgc_007_"+ lv02_cd);
                break;    
        }

        
    });
}


//  pc 버전 일 경우 href 속성을 복원하고, onclick 속성도 삭제한다.
function transAttr_pcVersionEvent() {
    $.each(lv1_menus_for_a, function(index,item) {
        $(item).attr('href',lv1_menu_href_array[index]);
    });
    $.each(lv1_menus_for_p, function(index,item) {
        $(item).removeAttr('onclick');
    });
}

// 모바일 버전으로 화면이 변경되면 메뉴바의 이벤트 속성을 변경해준다.
function transAttr_mobileVersionEvent() {
    $.each(lv1_menus_for_a, function(index,item) {
        lv1_menu_href_array[index] = $(item).attr('href');
        $(item).removeAttr('href');
    });
    $.each(lv1_menus_for_p, function(index,item) {
        $(item).attr('onclick','mobile_lv2menu_flag( '+index+' ) ');
    });
}

// mobile 화면에서 lv2 메뉴의 flag 이벤트 
let lv2_on_menu_index = -1;  // 메뉴바 lv2 의 on상태인 메뉴그룹 index
let lv2_on_menu_object = -1; // 메뉴바 lv2 가 on상태인 메뉴그룹의 태그 object
function mobile_lv2menu_flag(reqIndex) {
    let lv2_menus = $(".sub__menu__ul");
    //  (li 태그) 중매뉴 중 선택된 태그 표시
    $.each(lv2_menus, function(index,item) {
        $(item).addClass('hide');
        if(index == reqIndex && lv2_on_menu_index != index) {
            $(item).removeClass('hide');
            lv2_on_menu_object = $(item);
            lv2_on_menu_index = index;
        } else if(index == reqIndex && lv2_on_menu_index == index){
            lv2_on_menu_index = -1;
            lv2_on_menu_object = -1;
        }
    });
    
    //  (li 태그) 대매뉴 중 선택된 태그 표시
    let lv1_menus_li = $(".navbar__menu__ul__item");
    $.each(lv1_menus_li, function(index2,item) {
        $(item).removeClass('active');
        if(reqIndex == index2) {
            $(item).addClass('active');
        }
    });
}

// 공통 doAction 함수 
// action (CRUD 등등...) 을 수행한다.
let eventReplyNoid = ""; // 상세 게시글의 답글수정 에 사용 할 변수 
function comDoAction(acNm, pageName, replyNo) {
    $("#page_name").val(pageName);

    // 사용자 id input value 에 셋팅
    $("#user_id").val($("#text_user_id").text());
    let reqUrl, data, formName, bindId, callback;
    switch (acNm) {

        /* 공통 게시글 상세 조회 */
        case "search":
            reqUrl = pageName + "-DETAIL-S";
            data = null;
            formName = "hiddenForm";
            bindId = "boardDetail_bind";
            callback = update_callback_y;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;
        
        /* 댓글 / 답글 조회 */
        case "commonBoardReplySearch":
            reqUrl = "common_boardReply_search";
            data = null;
            formName = "hiddenForm";
            bindId = "boardReplyList";
            callback = null;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;
        case "commonSearchLikeIcon":
         /* like 조회 */
            reqUrl = "common_likeInfo_search";
            data = null;
            formName = "hiddenForm";
            bindId = "likeIcon";
            callback = null;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;   

        /* 공통 게시글 저장 (각 게시글 작성 페이지와 게시글 수정 페이지 에서 CALL )*/
        case "save":
            if (comValidation() || !confirm("저장 하시겠습니까?")) return;
            reqUrl = pageName + "-SAVE";
            data = null;
            formName = "frm";
            bindId = null;
            callback = comAfterSave(pageName);
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;

        /* 공통 게시글 (각 게시글 상세 페이지 에서 게시글 수정 버튼을 누르면 공통수정페이지로 이동 + 수절 할 게시글 조회) */   
        case "boardUpdateSearch":
            reqUrl = pageName + "-UPDATE-S";
            data = null;
            formName = "hiddenForm";
            bindId = "boardDetail_bind";
            callback = null;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;
        
        /* 공통 게시글 삭제 */
        case "boardDelete":
            if (!confirm("삭제 하시겠습니까?")) return;
            reqUrl = pageName + "-DELETE";
            data = null;
            formName = "hiddenForm";
            bindId = null
            callback = MoveMainBoard;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;

        /* 공통 댓글 등록 */
        case "insertReply" :
            $("#kind_of_reply").val("REPLY");         // 생성/수정 구분변수 셋팅
            comDoAction('replySave',pageName);        // replySave 호출해서 저장한다.
            break;

        /* 공통 댓글,답글  등록 or 수정  */
        case "replySave":
            if (comReplyValidation(replyNo)) {
                break;
            }

            if (replyNo != undefined) {
                $("#reply_cntn").val($("#text_reply_cntn" + replyNo).val());
                $("#reply_no").val(replyNo);
            } else {
                $("#reply_cntn").val($("#text_reply_cntn").val());
                $("#reply_no").val("");
            }

            reqUrl = pageName + "-SAVE";
            data = null;
            formName = "hiddenForm";
            bindId = null;
            callback = basicSearch_02;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;

        /* 공통 댓글,답글 삭제 (실제로 삭제X)*/    
        case "replyDelete" :
            if (!confirm("삭제 하시겠습니까?")) return;
            reqUrl = pageName + "-REPLY-DELETE";
            data = null;
            formName = "hiddenForm";
            bindId = null;
            callback = basicSearch_02;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;

        /* 공통 답글 form 삭제 */    
        case "replyFormDelete":

            $(".boardReply__cntn.reply_del" + replyNo).remove();

            if($("#update_yn").val() == 'Y') {
                $("#reply_" + eventReplyNoid+" .replyCntnList__outter").show();
                $("#reply_" + eventReplyNoid+" .lightLine.bottom").show();
            }
            break;
        
        /* 게시글 좋아요 클릭 Flag */
        case "clickLike":
            $("#page_name").val(pageName);
            reqUrl = "control_like";
            data = null;
            formName = "hiddenForm";
            bindId = null;
            callback = basicSearch_03;
            ajaxCall(reqUrl, data, formName, bindId, callback);
            break;

        /* 비 로그인 상태의 사용자가 좋아요를 클릭 했을 때 alert으로 회원 가입을 유도한다. */
        case "joinInfo" :
            if (!confirm("해당 기능은 회원 만 가능 합니다. \n\r 간편 회원가입 화면으로 이동할까요?")) return;
            movePage("loginForm");
            break;

        /* 공사중인 페이지 */
        case "repairPage" :
            alert("현재 이용이 잠시 중단 된 페이지 입니다.");
            break;
    }
}

// 페이지 이동 1
function movePage(reqLocation) {
    window.location = reqLocation;
}

// 페이지 이동 2 (상세 페이지 이동 후 기본 조회 callback 함수 -  각각 상세 페이지 에 맞게  ) 
function comAfterSave(pageName) {
    window.location = '/' + pageName;
}


/* 게시글 상세 페이지의  callback_yn 변수는 기본 Y 값이고 조회 후 N으로 값을 넣어서 최초 조회와 callback의 호출을 구분하기 위해서 사용한다.*/
function update_callback_y() {
    /* callback 함수가  basicSearch로 doAction('search',something) 을 호출 시 꼭 셋팅해줘야 함 */
    $("#callback_yn").val("Y");
}




// 입력 값 유효성 체크 (게시글)
function comValidation() {
    let errYn = false;
    let errMsg = "";

    if (isNull($("#board_title").val())) {
        errMsg = "제목";
        errYn = true;
        $("#board_title").focus();
    } else if (isNull($("#board_cntn").val())) {
        errMsg = "내용";
        errYn = true;
        $("#board_cntn").focus();
    }

    if (errYn) {
        alert(errMsg + " 은(는) 필수 입니다.")
    }
    return errYn
}

// 댓글, 답글 유효성 
function comReplyValidation(replyNo) {
    let isErr = false;
    let errMsg = "";
    let conText = replyNo == undefined ? "" : replyNo;
    if (isNull($("#text_reply_cntn" + conText).val())) {
        errMsg = "내용을 입력 하세요.";
        isErr = true;
        $("#text_reply_cntn" + conText).focus();
    }

    if (isErr) {
        alert(errMsg);
    }
    return isErr
}


//  답글작성 폼 생성
function comMakeReplyForm(replyNo, pageName, replyNoId) {
    comDoAction('replyFormDelete', pageName, replyFormNo);  // 폼 생성 전에 기존의 폼을 먼저 지워준다.
    $("#update_yn").val("N");                           // 생성/수정 구분변수 셋팅
    $("#kind_of_reply").val("REREPLY");                 // 생성/수정 구분변수 셋팅
    eventReplyNoid = replyNoId;                         // 댓글과 답글을 구분해 주기 위한 변수 셋팅
    replyFormNo = replyNo;
    let user_nm = $("#user_nm").val();
    let appendCntn = '<div class="boardReply__cntn reply_del' + replyNo + '" style="margin: 0 0 20px 55px;" >' +
        '<div class="boardReply__cntn__outterLine">' +
        '<div class="boardReply__user_nm">' +
        '<p >' + user_nm + '</p>' +
        '</div>' +
        '<textarea name="text_reply_cntn' + replyNo + '" id="text_reply_cntn' + replyNo + '" placeholder="댓글을 남겨보세요" onkeydown="resize(this)" onkeyup="resize(this)"></textarea>' +
        '<div class="reply__bottom">' +
        '<div class="optionItems">' +
        '<i class="fas fa-camera-retro"></i>' +
        '<i class="far fa-smile"></i>' +
        '</div>' +
        '<div class="replyRegBtn">' +
        '<button class="btn" type="button" style="margin-right:5px;" onclick="comDoAction(\'replyFormDelete\', \'' + pageName + '\'  ,' + replyNo + ')">취소</button>' +
        '<button class="btn" type="button" onclick="comDoAction(\'replySave\', \'' + pageName + '\' ,' + replyNo + ')">등록</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    $("#reply_" + replyNoId).append(appendCntn);
}

// 댓글, 답글의 내용을 수정 할 때 호출하는 함수 
function replyUpdate(replyNo, reReplyNo, pageName, replyNoId, replyCntn) {
    comDoAction('replyFormDelete', pageName, replyFormNo);  // 폼 생성 전에 기존의 폼을 먼저 지워준다.
    $("#update_yn").val("Y");                           // 생성/수정 구분변수 셋팅
    eventReplyNoid = replyNoId;                         // 댓글과 답글을 구분해 주기 위한 변수 셋팅
    replyFormNo = replyNo;

    // 수정하는 FORM이 댓글 또는 답글을 구분 하는 변수를  replyNoId 로 구분한다.
    checkKindOfReply(replyNoId);
     
    $("#re_reply_no").val(reReplyNo);                    
    let user_nm = $("#user_nm").val();
    let appendCntn = '<div class="boardReply__cntn reply_del' + replyNo + '" style="margin: 0 0 20px 55px;" >' +
        '<div class="boardReply__cntn__outterLine">' +
        '<div class="boardReply__user_nm">' +
        '<p >' + user_nm + '</p>' +
        '</div>' +
        '<textarea  name="text_reply_cntn' + replyNo + '" id="text_reply_cntn' + replyNo + '" placeholder="댓글을 남겨보세요" onkeydown="resize(this)" onkeyup="resize(this)">'+replyCntn+'</textarea>' +
        '<div class="reply__bottom">' +
        '<div class="optionItems">' +
        '<i class="fas fa-camera-retro"></i>' +
        '<i class="far fa-smile"></i>' +
        '</div>' +
        '<div class="replyRegBtn">' +
        '<button class="btn" type="button" style="margin-right:5px;" onclick="comDoAction(\'replyFormDelete\', \'' + pageName + '\'  ,' + replyNo + ')">취소</button>' +
        '<button class="btn" type="button" onclick="comDoAction(\'replySave\', \'' + pageName + '\' ,' + replyNo + ')">등록</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    $("#reply_" + replyNoId).append(appendCntn);
    $("#reply_" + replyNoId+" .replyCntnList__outter").hide();
    $("#reply_" + replyNoId+" .lightLine.bottom").hide();
}

// 저장하려는 정보가 댓글인지 답글인지를 확인
function checkKindOfReply(replyNoId) {
    let words = replyNoId.split('_');
    
    $.each(words ,function(index, el) {
        if(index == 1 && el != '0') {
            $("#kind_of_reply").val("REREPLY");                 // 답글
        } else if(index == 1 && el == '0') {
            $("#kind_of_reply").val("REPLY");                   // 댓글
        }
    });
}

// 댓글 / 답글을 삭제한다 (실제  DB는 삭제여부만 Y로 번경한다.)
function replyDelete(replyNo, reReplyNo, pageName, replyNoId) {
    $("#reply_no").val(replyNo);
    $("#re_reply_no").val(reReplyNo);

    // 수정하는 FORM이 댓글 또는 답글을 구분 하는 변수를  replyNoId 로 구분한다.
    checkKindOfReply(replyNoId);

    comDoAction("replyDelete",pageName )
}


// textarea 크기 자동조정
function resize(obj) {
    obj.style.height = "1px";
    obj.style.height = (12 + obj.scrollHeight) + "px";
}


/** ajax 공부 
 * $.ajax ({
            url : "request.php",
            success : function(data) { console.log("success")},
            error : function(data) { console.log("success")},
            complete : function(data) { console.log("success")},
            })
            .done(function(){ console.log("done") })
            .fail(function(){ console.log("fail") })
            .always(function(){ console.log("always") })
*/



// ajax 통신 비동기 통신 공통함수 
function ajaxCall(reqUrl, data, formName, bindId, callback, callbackParam) {
    let reqData = {};
    if (data != null && data != "") {
        reqData = data;
    } else if (formName != null && formName != "") {
        reqData = $("#" + formName).serialize();
    }

    $.ajax({
        url: reqUrl,
        data: reqData,
        type: "POST",
        cache: false,
        success: function (resData) {

            if (resData.errYn == 'Y') {
                alert(redData.rsltMsg == undefined ? "예상치 못한 에러가 발생 했습니다. \n\r 관리자 에게 문의 하세요."
                    : redData.rsltMsg);
            } else {
                // rsltMsg 가 존재하면 alert 생성
                if (resData.rsltMsg != undefined) {
                    alert(resData.rsltMsg);
                }

                //데이터 바인딩
                if (bindId != null) {
                    $("#" + bindId).replaceWith(resData);
                }

                // callback 이 있으면 호출한다.
                if (callback != null) {
                    callback(callbackParam);
                }
            }
        },
        error: function (jqXHR, exception) {
            if (jqXHR.status === 0) {
                alert('Not connect.\n Verify Network.');
            }
            else if (jqXHR.status == 400) {
                alert('Server understood the request, but request content was invalid. [400]');
            }
            else if (jqXHR.status == 401) {
                alert('Unauthorized access. [401]');
            }
            else if (jqXHR.status == 403) {
                alert('Forbidden resource can not be accessed. [403]');
            }
            else if (jqXHR.status == 404) {
                alert('Requested page not found. [404]');
            }
            else if (jqXHR.status == 500) {
                alert('Internal server error. [500]');
            }
            else if (jqXHR.status == 503) {
                alert('Service unavailable. [503]');
            }
            else if (exception === 'parsererror') {
                alert('Requested JSON parse failed. [Failed]');
            }
            else if (exception === 'timeout') {
                alert('Time out error. [Timeout]');
            }
            else if (exception === 'abort') {
                alert('Ajax request aborted. [Aborted]');
            }
            else {
                alert('Uncaught Error.n' + jqXHR.responseText);
            }
        }
    });
    /*
       .done(function (resData) {

        if(resData.rsltMsg != undefined) {
            alert(resData.rsltMsg);
        }

        //데이터 바인딩
        if(bindId != null) {
            $("#"+bindId).replaceWith(resData);
        }

        // callback 이 있으면 호출한다.
        if(callback != null) {
            callback(callbackParam);
        }

    }).fail(function(resData){ 
        alert(resData.rsltMsg + "\n\r 에러내용 : " + resData.errMsg);
    });
    */
}


// ajax 통신 비동기 통신 공통함수 - 파일 업로드
function ajaxCallUpload(reqUrl, data, formName, bindId, callback, callbackParam) {
    let reqData = {};

    if (data != null && data != "") {
        reqData = data;
    } else if (formName != null && formName != "") {
        reqData = $("#" + formName).serialize();
        reqData = new FormData($("#" + formName)[0])
    }

    // let testForm = $("#"+formName);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: reqUrl,
        data: reqData,
        processData: false,
        contentType: false,
        cache: false
    }).done(function (resData) {

        if (resData.errYn == "Y") {
            alert("저장 실패 : " + resData.errMsg);
        } else {

            //데이터 바인딩
            if (bindId != null) {
                $("#" + bindId).replaceWith(resData);
            }

            // callback 이 있으면 호출한다.
            if (callback != null) {
                callback(callbackParam);
            }
        }
    });
}

// null 체크함수
function isNull(val) {
    let checkVal = $.trim(val);
    if (checkVal === null) return true;
    if (typeof checkVal === "string" && checkVal === "") return true;
    if (typeof checkVal === "undefined") return true;

    return false;
}

function checkIE() {
    let agent = navigator.userAgent.toLowerCase();
    if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
        alert("IE는 사용이 불가능 합니다.");
        window.location.href = 'https://www.naver.com/';
        // if(window.history) {
            
        // } else {
            
        // }
    }
}