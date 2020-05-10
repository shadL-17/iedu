function postSubmitCheck() {
    const title = document.getElementById('post_title_input').value;
    const content = document.getElementById('post_content_input').value;
    const type = document.getElementById('post_type_input').value;
    const role = document.getElementById('userRole').value;
    if (type==null || type=='') {
        alert('请选择帖子类型！');
        return false;
    }
    if (title!=null && title!='' && content!=null && content!='') {
        if (type=='public' && role!='admin') {
            alert('你没有权限发公告帖！');
            return false;
        }
        else {
            return true;
        }
    }
    else {
        alert('标题或正文不能为空！');
        return false;
    }
}