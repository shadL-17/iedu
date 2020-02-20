package cn.shadl.iedubasezuul.filter;

import cn.shadl.ieducommonbeans.domain.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();//获取请求上下文
        HttpServletRequest request = ctx.getRequest();//获取请求
        Cookie[] cookies = request.getCookies();//获取Cookies

        for(Cookie cookie : cookies) {  //从Cookies中检查有无登录状态信息
            if(cookie.getName().equals("currentUser") && cookie.getValue()!=null) { //已登录
                User user;
            }
            else {  //未登录
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(403);
                ctx.setResponseBody("检测到您未登录，即将跳转到登录页面。");
                try {
                    ctx.getResponse().sendRedirect("http://localhost:8080/web/login");//跳转到登录页面
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
