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

function registerCheck() {
    var patt = /^[A-Za-z0-9]{6,12}$/;
    var pwd1 = document.getElementById("regPassword").value;
    var pwd2 = document.getElementById("regPassword2").value;
    var info_usrn = document.getElementById("usernameCheck");
    var info_pwd1 = document.getElementById("passwordCheck");
    var info_pwd2 = document.getElementById("password2Check");
    if(!patt.test(pwd1)) {
        info_pwd1.style.display = 'block';
        info_pwd2.style.display = 'none';
        return false;
    }
    if(pwd1 != pwd2) {
        info_pwd1.style.display = 'none';
        info_pwd2.style.display = 'block';
        return false;
    }
    alert("注册成功！");
    return true;
}