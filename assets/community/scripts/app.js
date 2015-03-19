var parseDatetime = function(datetime){
    datetime += '+08:00';
    MM = {Jan:"1月", Feb:"2月", Mar:"3月", Apr:"4月", May:"5月", Jun:"6月", Jul:"7月", Aug:"8月", Sep:"9月", Oct:"10月", Nov:"11月", Dec:"12月"};
    var result = String(new Date(datetime)).replace(/\w{3} (\w{3}) (\d{2}) (\d{4}) (\d{2}):(\d{2}):[^(]+\(([A-Z]{3})\)/,
            function($0,$1,$2,$3,$4,$5,$6){
                return MM[$1]+$2+"日, "+$4+":"+$5;
            }
    );
    return result;
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
        return false;
    }

return true;
}
                                                                                                    
var replaceRelativePath = function(content){

    var set = content.match(/<a href=['|"](\/[^'"]+)['|"]/g);
    if(set && set.length){
        for(var i=0; i<set.length; i++){
            var link = set[i];
            var sets = content.match(/<a href=['|"](\/[^'"]+)['|"]/);
            if(sets && sets.length == 2){
                var url = 'http://www.shanbay.com' + sets[1];
                content = content.replace(sets[1], url);
            }
        }
    }
    return content;
}


var convert = function (posts, append, userid, reverse) {
	var jsonStr = JSON.stringify(posts);
	if (append === undefined) {
		renderArticle(JSON.parse(jsonStr));
	} else {
		renderThread(JSON.parse(jsonStr), append, userid, reverse);
	}
}

var parseUserAgent=function (name){
    if (name=="web"){
        return "网页版";
    }
    return name;
}

var renderThread = function(posts, append, user_id, reverse){
                                                                                                    
    var MAX_NICKNAME_LENGTH = 5;
    var MAX_TEAMTITLE_LENGTH = 9;
    var MAX_LENGTH = 14;
    var thread = {};

    function processData(posts){
        thread['first_page'] = append!=true;
        thread['user_id'] = user_id;
        if(posts.length){
            thread['t'] = posts[0].thread;
            thread.posts = [];
            for(i=0; i<posts.length; i++){
                var post = posts[i];
                post.body_html = markdown.toHTML(post.body);
                post.body_html = replaceRelativePath(post.body_html);
                post.fancy_time = parseDatetime(post.time);
                post.user_agent= parseUserAgent(post.agent.name);
                var team_title = post.team ? post.team.title : '';
                var content = post.author.nickname + team_title;

                if (content.length > MAX_LENGTH){
                    if(post.author.nickname.length > MAX_NICKNAME_LENGTH && team_title.length > MAX_TEAMTITLE_LENGTH){
                        post.author.nickname = post.author.nickname.slice(0, MAX_NICKNAME_LENGTH) + '...';
                        post.team.title = post.team.title.slice(0, MAX_TEAMTITLE_LENGTH) + '...';
                    }
                    else if(post.author.nickname.length > MAX_NICKNAME_LENGTH){
                        tmp_max_length = MAX_LENGTH-team_title;
                        if(post.author.nickname.length > tmp_max_length){
                            post.author.nickname = post.author.nickname.slice(0, tmp_max_length) + '...';
                        }
                    }
                    else{
                        tmp_max_length = MAX_LENGTH-post.author.nickname.length;
                        if (team_title.length > tmp_max_length){
                            post.team.title = post.team.title.slice(0, tmp_max_length) + '...';
                        }
                    }
                }

                if(isEmpty(post.post_to)){
                    post.post_to = undefined;
                }
                thread.posts.push(post);
            }
        }
    }
    processData(posts);
    
    var html = $("#posts-tmpl").tmpl(thread);
    
    $('#thread-title').html(thread.t.title);
    if(append==true){
        if($('body #thread .post').length){
			if (reverse==true){
				$(html).insertBefore($('body #thread .post:first'));
			}
			else {
				$(html).insertAfter($('body #thread .post:last'));
			}
        }
        else{
            $('body #thread .thread-title').html($("#thread-tmpl").tmpl(thread));
            $('body #thread .thread-posts').html(html);
        }
    }
    else{
        $('body #thread .thread-title').html($("#thread-tmpl").tmpl(thread));
        $('body #thread .thread-posts').html(html);
    }
                                                                                                    
    function clearHighlightedButton(){
    $('.action-button-edit.active img').attr('src', 'img/icon_edit.png');
    $('.action-button-edit.active').removeClass('active');
    $('.action-button-reply.active img').attr('src', 'img/icon_reply.png');
    $('.action-button-reply.active').removeClass('active');
    }
                                                                                                    
    $('.action-button-edit').click(function(){
        
        $(this).find('img').attr('src', 'img/icon_edit_press.png');
        $(this).addClass('active');
        setTimeout(function(){
                  clearHighlightedButton();
                  }, 200);
    });
    $('.action-button-reply').click(function(){
        
        $(this).find('img').attr('src', 'img/icon_reply_press.png');
        $(this).addClass('active');
        setTimeout(function(){
                   clearHighlightedButton();
                   }, 200);
    });
    

    fixHeight();
    callRenderFinish();
}
                                                                                                    
function fixHeight () {
    // window.location = "thread-ui://height/"+$('body').height();
}

var callRenderFinish = function() {
    window.location = "shanbaycommunity://renderfinish";
}

var callNativeSelector = function(nativeSelector) { // put native selector as param
     window.location = "shanbaycommunity://www.shanbay.com"+nativeSelector;
}
                                                                                                    
var callAuthorFromPost = function(post_id) {
    callNativeSelector('/author/post/'+post_id+'/');
}
                                                                                                    
var callUserPostToFromPost = function(post_id) {
    callNativeSelector('/post_to/post/'+post_id+'/');
}

var callTeam = function(team_id) {
    callNativeSelector('/team/detail/'+team_id+'/');
}
                                                                                                    
var replyPost = function(post_id) {
    callNativeSelector('/post/'+post_id+'/reply/');
}
                                                                                                    
var editPost = function(post_id) {
    callNativeSelector('/post/'+post_id+'/edit/');
}

var updatePost = function(id, content) {
    content = markdown.toHTML(content);
    var html = $('#post-content-tmpl').tmpl({content: content});
    $('#post-' + id + ' .post-body').html(html);

    fixHeight();
}

var getPostposition = function(id){
    var dom = $('#post-' + id + ' .post-body')
    var pos = dom.position();
    pos.width = dom.width();
    pos.height = dom.height();
    return pos;
}

var replaceFlash = function(content){
    var set = content.match(/sid\/(\w+)\/v.swf/);
    if(set && set.length == 2){
        // var video_src = "http://v.youku.com/player/getRealM3U8/vid/" + set[1] + "/type//video.m3u8";
        var embed_src="http://player.youku.com/player.php/sid/" + set[1] + "/v.swf";
        // var list = ['<video width="280" height="180" src="', video_src, '" ><embed src="', embed_src, '" allowfullscreen="true" quality="high" width="480" height="320" align="middle" allowscriptaccess="always" type="application/x-shockwave-flash"></video>'];
        // var replacementStr = list.join('');

        var video='<div class="clearfix"><a href="' + embed_src + '"><img class="video-img fl"  src="img/icon_play.png"/></a><a class="fl video-label" href="' + embed_src + '">观看视频</a></div>';

        content = content.replace(/<embed[^>]+>/, video);

    }
    return content;
}

                                                                                                    
var renderArticle = function(data){
    $('body .title').html(data.title);
    data.content = replaceFlash(data.content);
    data.content = replaceRelativePath(data.content);
    var html = $('#content-tmpl').tmpl(data);
    $('body .footprint-content').html(html);
	var loopId = setInterval(function(){
		if($('.footprint-content').html()){
			$('body').show();
			clearInterval(loopId);
		}
	}, 60);
	setTimeout(function(){
		$('body').show();
	}, 1500);
    callRenderFinish();
};
