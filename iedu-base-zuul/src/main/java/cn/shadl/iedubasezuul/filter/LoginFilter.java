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
        String url = request.getRequestURL().toString();//获取请求URL

        if(url.contains("/login") || url.contains("/sso") || url.contains("/css") || url.contains("/js") || url.contains("/image")) {//直接放行：登录页、单点登录服务、静态资源
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            return null;
        }
        if(cookies!=null && cookies.length>0) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("user")) {
                    Cookie userCookie = new Cookie("user",cookie.getValue());
                    ctx.getResponse().addCookie(userCookie);
                    ctx.setSendZuulResponse(true);
                    ctx.setResponseStatusCode(200);
                    return null;
                }
            }
        }

        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(403);
        ctx.setResponseBody("检测到您未登录，即将跳转到登录页面。");
        try {
            ctx.getResponse().sendRedirect("http://localhost:8080/web/login");//跳转到登录页面
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
