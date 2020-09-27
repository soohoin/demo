'use strict';

$(document).ready(function (){

    bannerAutoPlay();
    content2Fadein();
});

// 아이템 fadein
function content2Fadein() {
    
    $('.hideme').each( function(i){
        var bottom_of_object = $(this).offset().top ;
        var bottom_of_window = $(window).scrollTop() + $(window).height();
        if( bottom_of_window > bottom_of_object ){
            $(this).addClass('showme');
        }
    });
    $(window).scroll( function(){
        // let a = $(window).scrollTop();
        // let b = $(window).height();
        // console.log( "$(window).scrollTop() : " + a);
        // console.log( "$(window).height() : " + b);
        // console.log( "(window).scrollTop() + $(window).height(); : " + (a + b));

    $('.hideme').each( function(i){
        var bottom_of_object = $(this).offset().top;
        var bottom_of_window = $(window).scrollTop() + $(window).height();
        if( bottom_of_window > bottom_of_object ){
            $(this).addClass('showme');
        }
        if( bottom_of_window < bottom_of_object ){
            $(this).removeClass('showme');
        }
    });
});
    
}

// const navbar = document.querySelector('#navbar');
// const navbarHeight = navbar.getBoundingClientRect(). height;
// let position = 0;   // 스크롤이 위/아래 인지 구분하기 위한 변수

let curPageNo = 1;  // 슬라이드 에서 사용하는 페이지 번호
let rollid;         // setinterval id
let nextPageNo = 1; // 슬라이드 다음페이지 번호


// 자동으로 배너를 돌리기 위해서 사용하는 이벤트
function bannerAutoPlay() {
    $("input[name=slider]").on('click',function() {
        //console.log(typeof $("input[name=slider]:checked").val());
        curPageNo = $("input[name=slider]:checked").val();
        nextPageNo = (curPageNo % 4) + 1;
    })
    
    rollid = setInterval(function(){
                $("#slide"+nextPageNo).trigger("click");
            },3000);

    $(".stopAutoslide").mouseover(function(){
        clearInterval(rollid);
    });

    $(".stopAutoslide").mouseout(function(){
        rollid = setInterval(function(){
                    $("#slide"+nextPageNo).trigger("click");
                },3000);
    });
}

// content items effect function
document.addEventListener('scroll',(event) => {
    
    // fadein items
    let content2stBoxsPsn = $(".content2st__boxs").offset().top;
    let content2stIconsPsn = $(".content2st__icons").offset().top;
    let windowBottmo = $(window).height() + window.scrollY;
    if(windowBottmo >= content2stBoxsPsn) {
        $(".content2st__boxs").animate({'opacity':'1','margin-left':'0px'},1000);
    } else {
        // $(".content2st__boxs").animate({'opacity':'0','margin-left':'0px'},1000);
    }
    if(windowBottmo >= content2stIconsPsn) {
        $(".content2st__icons").animate({'opacity':'1','margin-left':'0px'},1000);
    } else {
        // $(".content2st__icons").animate({'opacity':'0','margin-left':'0px'},1000);
    }
});






