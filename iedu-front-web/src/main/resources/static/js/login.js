function switchLogin() {
    document.getElementById("switchLogin").style.backgroundColor = 'dodgerblue';
    document.getElementById("switchLogin").style.color = 'white';
    document.getElementById("switchRegister").style.backgroundColor = 'white';
    document.getElementById("switchRegister").style.color = 'black';
    document.getElementById("paneLogin").style.display = 'block';
    document.getElementById("paneRegister").style.display = 'none';
}
function switchRegister() {
    document.getElementById("switchLogin").style.backgroundColor = 'white';
    document.getElementById("switchLogin").style.color = 'black';
    document.getElementById("switchRegister").style.backgroundColor = 'dodgerblue';
    document.getElementById("switchRegister").style.color = 'white';
    document.getElementById("paneLogin").style.display = 'none';
    document.getElementById("paneRegister").style.display = 'block';
}

function showMsg(msg) {
    if(msg!=null) {
        alert(msg);
    }
}