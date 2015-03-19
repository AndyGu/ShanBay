/*
  * initialize the page with given data
  * data includes: username, special_day, checkin_days, words, reading, sentence,
  * listening, time , user note
*/
var renderData = function(obj){
	$('#checkin-days-data').text(obj['checkin_days']);
	$('#words-data').text(obj['words']);
	$('#reading-data').text(obj['reading']);
	$('#sentences-data').text(obj['sentences']);
	$('#listening-data').text(obj['listening']);
	$('#used-time-data').text(obj['time']);

	if(obj['user_name']){
		$('#user-name-data').text(obj['user_name']);
		$('.username').show();
	}
	if(obj['special_day']){
		$('#special-day').show();
	}
	if(obj['diary']){
		$('#diary-hint').hide();
		$('#diary-data').text(obj['diary']);
	}
	gloabalStyleAdjust();
    callShareBarShow('#ff81748b');
};

// render finish signal
var renderFinish = function(){
	window.location = 'shanbay.native.app://checkin/rendered/';
};

/* 
  * update diary 
  * if param is null, the diary set hide
  * not null, show diary
*/
var updateDiary = function(diary_str){
	if(diary_str){
		$('#diary-hint').hide();		
		$('#diary-data').text(diary_str);
	}else{
		$('#diary-data').text('');
		$('#diary-hint').show();		
	}
	renderFinish();
};

// native window router
var callNativeSelector = function(selector){
	window.location = 'shanbay.native.app://' + selector;
};

// checkin page award hint 'detail' btn
var callAwardDetail = function(){
	callNativeSelector('checkin/award/detail/');
};

// checkin page weibo share btn
var callWeiboShare = function(){
	callNativeSelector('checkin/share/weibo/');
};

// checkin page weixin chat share btn
var callWeixinChatShare = function(){
	callNativeSelector('checkin/share/weixinchat/');
};

// checkin page weixin moments share btn
var callWeixinMomentsShare = function(){
	callNativeSelector('checkin/share/weixinmoments/');
};

// checkin page diary edit action
var callNoteEdit = function(){
	callNativeSelector('checkin/note/edit/')
};

// checkin share bottom bar show action
var callShareBarShow = function(color_str){
	callNativeSelector('checkin/share/bar/show?bg='+color_str);
};

/*
  * checkin page btn events bind
*/
$('#diary-data-box').click(function(){
    callNoteEdit();
});

$('.tip .detail').click(function(){
	callAwardDetail();
});

/*
  * Diary Position Adjustment
*/
var diaryDataPositionAdjust = function(){
	//diary adjust
	var selector = '.diary-box';
	var diaryHeight = $(selector).height();
	var diaryWidth = $(selector).width();
	var lineHeight = diaryHeight * 0.26;
	var diaryDataTop = $(selector).offset().top + diaryHeight * 0.18;
	var diaryDataLeft = $(selector).offset().left + diaryWidth * 0.12;
	var diaryDataHeight = diaryHeight * 0.78;
	var diaryDataWidth = diaryWidth * 0.76;
	$('#diary-data-box').css({'top': diaryDataTop, 'left': diaryDataLeft, 'height': diaryDataHeight, 
							'width': diaryDataWidth, 'line-height': lineHeight+'px'}).show();
};

var gloabalStyleAdjust = function(){
	diaryDataPositionAdjust();
};

//Window Resize Listen
$(window).resize(function(){
	gloabalStyleAdjust();
});

/*
  * Document Ready Action
*/
$(document).ready(function(){
});

