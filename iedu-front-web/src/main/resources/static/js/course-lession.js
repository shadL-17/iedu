var video_obj;
var video_timer;
var progress_bar;
var progress_bar_bg;
var v_total;
var v_effect;
var v_now;
var persent;

function init() {
    progress_bar = document.getElementById("progress-bar");
    video_obj = document.getElementById("video-obj");
    v_total = video_obj.duration;
    v_effect = 0;
}

function updateProgress() {
    v_now = video_obj.currentTime;
    if(v_now>v_effect && v_now-v_effect<=2) {
        v_effect = v_now;
        persent = Math.round((v_effect/v_total)*100)+'%';
        progress_bar.style.width = persent;
        progress_bar_bg.title = '当前进度：'+persent;
        progress_bar_bg.dataset.originalTitle = 'ff';
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

function get() {
    alert(progress_bar_bg.dataset.originalTitle);
}
function set() {
    progress_bar_bg.dataset.originalTitle = 'ff';
}