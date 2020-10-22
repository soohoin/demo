'use strict';

let navbar = "";
let navbarHeight = "";
let position = 0;   // 스크롤이 위/아래 인지 구분하기 위한 변수

$(document).ready(function (){
    navbar = document.querySelector('#navbar');
    navbarHeight = navbar.getBoundingClientRect().height;

    // 메뉴 hover 이벤트
    $(".navbar__menu__ul").hover(function() {
        $('.sub__menu__ul').removeClass('hide');
        $('.sub__menu__ul').addClass('showme');
        $('.menu__bg').removeClass('hide');
        $('.navbar__menu__ul').css('height','200');
    }, function() {
        $('.sub__menu__ul').addClass('hide');
        $('.sub__menu__ul').removeClass('showme');
        $('.menu__bg').addClass('hide');
        $('.navbar__menu__ul').css('height','80');
    });

});

// 메뉴바 숨기기 & 스크롤시 변환이벤트
document.addEventListener('scroll',(event) => {
    let isDown = true;
    if(window.scrollY > position ) {
        isDown = true;
    } else {
        isDown = false;
    }
    
    position = window.scrollY;
    
    if(window.scrollY > 200) {
        if(isDown) {
            navbar.classList.add('hide');    
        } else {
            navbar.classList.remove('hide');    
        }
    }

    // if(window.scrollY > navbarHeight) {
    //     navbar.classList.add('navbar--dark');    
    // } else {
    //     navbar.classList.remove('navbar--dark');
    // }
   
});

// ajax 통신 비동기 통신 공통함수 
function ajaxCall(reqUrl, data, formName, bindId, callback, callbackParam) {
    let reqData = {};
    
    if(data != null && data != "") {
        reqData = data;
    } else if(formName != null && formName != "") {
        reqData = $("#"+formName).serialize();
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

    $.ajax({
        url: reqUrl,
        data:reqData,
        type:"POST",
        cache:false,
        success : function(resData) {

            if(resData.errYn == 'Y') {
                alert(redData.rsltMsg == undefined ? "예상치 못한 에러가 발생 했습니다. \n\r 관리자 에게 문의 하세요."
                                                    : redData.rsltMsg);
            } else {
                // rsltMsg 가 존재하면 alert 생성
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
            }
        },
        error: function(jqXHR, exception) {
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
    
    if(data != null && data != "") {
        reqData = data;
    } else if(formName != null && formName != "") {
        reqData = $("#"+formName).serialize();
        reqData = new FormData($("#"+formName)[0])
    }

    // let testForm = $("#"+formName);
    
    $.ajax({
        type:"POST",
        enctype: "multipart/form-data",
        url: reqUrl,
        data: reqData,
        processData: false,
        contentType: false,
        cache:false
    }).done(function (resData) {

        if(resData.errYn == "Y") {
            alert("저장 실패 : " + resData.errMsg);
        } else {

            //데이터 바인딩
            if(bindId != null) {
                $("#"+bindId).replaceWith(resData);
            }
    
            // callback 이 있으면 호출한다.
            if(callback != null) {
                callback(callbackParam);
            }
        }

    });
}

// null 체크함수
function isNull(val) {
    let checkVal = $.trim(val);
    if(checkVal === null) return true;
    if(typeof checkVal === "string" && checkVal === "") return true;
    if(typeof checkVal === "undefined") return true;

    return false;
}


// 공통 doAction 함수 
// action (CRUD 등등...) 을 수행한다.
function comDoAction(acNm, pageName) {
    let reqUrl, formName, bindId, data, callback;
    switch(acNm) {
        case "search":
            reqUrl = pageName+"-DETAIL-S";
            formName = "hiddenForm";
            bindId = "boardDetail_bind";
            // callback = setVideo;
            ajaxCall(reqUrl,null,formName,bindId);
            break;
        // case "boardReply":
        //     reqUrl = pageName+"-DETAIL-REPLY-S";
        //     formName = "hiddenForm";
        //     bindId = "boardReplyList";
        //     ajaxCall(reqUrl,null,formName,bindId);
        //     break;
        case "save":
            if(comValidation() || !confirm("저장 하시겠습니까?")) return;
            reqUrl = pageName+"-SAVE";
            formName = "frm";
            data = null;
            callback = comAfterSave(pageName);
            ajaxCall(reqUrl,data,formName,null,callback);
            break;    
    }
}

// 페이지 이동
function movePage(reqLocation) {
    window.location = reqLocation;
}

// 페이지 이동
function comAfterSave(pageName) {
    alert("저장완료");
    window.location = '/'+pageName;
}

// 입력 값 유효성 체크
function comValidation() {
    let errYn = false;
    let errMsg = "";

    if(isNull($("#board_title").val())) {
        errMsg = "제목";
        errYn = true;
        $("#board_title").focus();
    } else if(isNull($("#board_cntn").val())) {
        errMsg = "내용";
        errYn = true;
        $("#board_cntn").focus();
    } 

    if(errYn) {
        alert(errMsg + " 은(는) 필수 입니다.")
    }
    return errYn
}

// textarea 크기 자동조정
function resize(obj) {
    obj.style.height = "1px";
    obj.style.height = (12+obj.scrollHeight)+"px";
  }