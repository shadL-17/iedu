function tabButton1() {
    document.getElementById("pane_home").style.display = "block";
    document.getElementById("pane_info").style.display = "none";
    document.getElementById("pane_account").style.display = "none";
    document.getElementById("pane_userCourse").style.display = "none";
    document.getElementById("pane_userCommunity").style.display = "none";
}
function tabButton2() {
    document.getElementById("pane_home").style.display = "none";
    document.getElementById("pane_info").style.display = "block";
    document.getElementById("pane_account").style.display = "none";
    document.getElementById("pane_userCourse").style.display = "none";
    document.getElementById("pane_userCommunity").style.display = "none";
}
function tabButton3() {
    document.getElementById("pane_home").style.display = "none";
    document.getElementById("pane_info").style.display = "none";
    document.getElementById("pane_account").style.display = "block";
    document.getElementById("pane_userCourse").style.display = "none";
    document.getElementById("pane_userCommunity").style.display = "none";
}
function tabButton4() {
    document.getElementById("pane_home").style.display = "none";
    document.getElementById("pane_info").style.display = "none";
    document.getElementById("pane_account").style.display = "none";
    document.getElementById("pane_userCourse").style.display = "block";
    document.getElementById("pane_userCommunity").style.display = "none";
}
function tabButton5() {
    document.getElementById("pane_home").style.display = "none";
    document.getElementById("pane_info").style.display = "none";
    document.getElementById("pane_account").style.display = "none";
    document.getElementById("pane_userCourse").style.display = "none";
    document.getElementById("pane_userCommunity").style.display = "block";
}
function infoEditShow() {
    var infos = document.getElementsByClassName("infoValue");
    for (var i=0;i<infos.length;i++) {
        infos[i].style.display = "none";
    }
    infos = document.getElementsByClassName("infoInput");
    for (var i=0;i<infos.length;i++) {
        infos[i].style.display = "inline";
    }
}
function infoEditHide() {
    var infos = document.getElementsByClassName("infoValue");
    for (var i=0;i<infos.length;i++) {
        infos[i].style.display = "inline";
    }
    infos = document.getElementsByClassName("infoInput");
    for (var i=0;i<infos.length;i++) {
        infos[i].style.display = "none";
    }
}
