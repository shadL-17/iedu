var video_obj;
var video_timer;
var pause_timer;
var progress_bar;
var progress_bar_bg;
var v_total = 0;
var v_effect = 0;
var v_now = 0;
var persent;
var page_title;
var reqSender;
var pauseTime;
var completed;

function init(isFinished) {
    progress_bar = document.getElementById("progress-bar");
    progress_bar_bg = document.getElementById("progress-bar-bg");
    video_obj = document.getElementById("video-obj");
    v_total = video_obj.duration;
    page_title = document.getElementById('page-course-title');
    reqSender = document.getElementById('requestSender');
    completed = isFinished;
}

function updateProgress() {
    v_now = video_obj.currentTime;
    if((v_now>v_effect) && (v_now-3<v_effect)) {
        v_effect = v_now;
        persent = Math.round((v_effect/v_total)*100);
        progress_bar.style.width = persent+'%';
        progress_bar_bg.title = '当前进度：'+persent+'%';
        progress_bar_bg.dataset.originalTitle = '当前进度：'+persent+'%';
    }
    if (persent>98&&!completed) {
        const host_ip = document.getElementById('hostConfig').value;
        const uid = document.getElementById('hostConfig').dataset.uid;
        const cid = document.getElementById('hostConfig').dataset.cid;
        document.getElementById('lessionStat').value = 'finished';
        reqSender.src = 'http://'+host_ip+':8080/course/upgradeProgress?uid='+uid+'&cid='+cid;
        completed = true;
        stopVideoTimer();
        clearPauseTimer();
        window.location.reload();
    }
}

function startVideoTimer() {
    video_timer = setInterval(updateProgress, 1000);
}

function stopVideoTimer() {
    clearInterval(video_timer);
}

function setCurrentTime() {
    const v_before = v_now;
    v_now = video_obj.currentTime;
    recordSeeked(v_before, v_now);
}

function recordPause() {
    var datetime = new Date();
    var timeStr = datetime.getFullYear()+'-'+datetime.getMonth()+'-'+datetime.getDay()+' '+datetime.getHours()+':'+datetime.getMinutes()+':'+datetime.getSeconds();
    recordVideoAction('pause', v_now, v_now, timeStr);
}

function recordSeeked(before, after) {
    var datetime = new Date();
    var timeStr = datetime.getFullYear()+'-'+datetime.getMonth()+'-'+datetime.getDay()+' '+datetime.getHours()+':'+datetime.getMinutes()+':'+datetime.getSeconds();
    if (before < after) {//向后定位
        recordVideoAction('skip', before, after, timeStr);
    }
    else if(before > after) {//向前定位
        recordVideoAction('fallback', before, after, timeStr);
    }
}

function recordAbandon() {
    var datetime = new Date();
    var timeStr = datetime.getFullYear()+'-'+datetime.getMonth()+'-'+datetime.getDay()+' '+datetime.getHours()+':'+datetime.getMinutes()+':'+datetime.getSeconds();
    recordVideoAction('abandon', v_now, v_now, timeStr);
}

function recordReview() {
    var datetime = new Date();
    var timeStr = datetime.getFullYear()+'-'+datetime.getMonth()+'-'+datetime.getDay()+' '+datetime.getHours()+':'+datetime.getMinutes()+':'+datetime.getSeconds();
    recordVideoAction('review', v_now, v_now, timeStr);
}

window.onblur = function () {
    video_obj.pause();
}

window.onunload = function () {
    if (document.getElementById("lessionStat").value == 'unfinish') {
        recordAbandon();
    }
}

/*video_action_recorder*/
function recordVideoAction(action, timeBefore, timeAfter, actionTime) {
    const host_ip = document.getElementById('hostConfig').value;
    const uid = document.getElementById('hostConfig').dataset.uid;
    const lid = document.getElementById('hostConfig').dataset.lid;
    $.ajax({
        type: "POST",
        url: "http://"+host_ip + ":8080/course/saveVideoActionRecord",
        data: {
            uid: uid,
            lid: lid,
            action: action,
            timeBefore: timeBefore,
            timeAfter: timeAfter,
            actionTime: actionTime
        },
        dataType: "text",
        success: function(result) {

        }/*,
        error: function (xhr, textStatus, errorThrown) {
            alert("ajax run failed, host: "+host_ip+", status code："+xhr.status+", state: "+xhr.readyState+", message: "+xhr.statusText+", response: "+xhr.responseText+", requestStat: "+textStatus);
        }*/
    });
}

function startPauseTimer() {
    pause_timer = setInterval(pauseTimeCounter, 1000);
}

function clearPauseTimer() {
    pauseTime = 0;
    clearInterval(pause_timer);
}

function pauseTimeCounter() {
    if (pauseTime<5) {
        pauseTime++;
    }
    else {
        recordPause();
        pauseTime = 0;
        clearInterval(pause_timer);
    }
}