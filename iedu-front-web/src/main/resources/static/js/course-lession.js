var video_obj;
var video_timer;
var progress_bar;
var progress_bar_bg;
var v_total = 0;
var v_effect = 0;
var v_now = 0;
var persent;
var page_title;
var reqSender;

function init() {
    progress_bar = document.getElementById("progress-bar");
    progress_bar_bg = document.getElementById("progress-bar-bg");
    video_obj = document.getElementById("video-obj");
    v_total = video_obj.duration;
    page_title = document.getElementById('page-course-title');
    reqSender = document.getElementById('requestSender');
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
    if (persent>98) {
        const host_ip = document.getElementById('hostConfig').value;
        const uid = document.getElementById('hostConfig').dataset.uid;
        const cid = document.getElementById('hostConfig').dataset.cid;
        reqSender.src = 'http://'+host_ip+':8080/course/upgradeProgress?uid='+uid+'&cid='+cid;
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
    v_now = video_obj.currentTime;
}

window.onblur = function () {
    video_obj.pause();
}