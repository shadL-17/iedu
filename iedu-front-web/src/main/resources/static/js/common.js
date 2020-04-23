function toURL(url) {
    window.location.href = url;
}

function showMsg(msg) {
    alert(msg);
}

function setValue(id,val) {
    document.getElementById(id).value = val;
}

function showObj(id,mode) {
    document.getElementById(id).style.display = mode;
}

function hideObj(id) {
    document.getElementById(id).style.display = 'none';
}