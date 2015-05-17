package com.xinran.springmvc.interceptor;

/**
 * @author 高海军 帝奇 Apr 20, 2015 9:55:10 PM
 */
import java.util.List;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xinran.constant.ApplicationConstant;
import com.xinran.controller.util.UserIdenetityUtil;

public class UserSessionInterceptor implements HandlerInterceptor {

    private Logger              log             = LoggerFactory.getLogger(this.getClass());

    private static List<String> noNeedLoginURLs = Lists.newArrayList();

    {
        noNeedLoginURLs.add("/");

        noNeedLoginURLs.add("/search");

        noNeedLoginURLs.add("/user/signUp");
        noNeedLoginURLs.add("/user/signIn");
        noNeedLoginURLs.add("/user/signOut");

        noNeedLoginURLs.add("/book/isbn");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 白名单机制, 注册,登录,首页,搜索,数据详情,详情二维码,其他都需要登录登录
        String path = request.getRequestURI();

        if ("/mobile".equals(path) || "/web".equals(path)) {
            return true;
        }

        for (String url : noNeedLoginURLs) {
            // TODO 使用注解会更好点
            if (-1 != path.indexOf(url)) {
                return true;
            }

        }

        Long currentUserIdFromSession = UserIdenetityUtil.getCurrentUserId(request);
        String accessTokenFromSession = UserIdenetityUtil.getCurrentAccessToken(request);

        if (null == currentUserIdFromSession) {
            return false;
        }
        
        if (-1 != path.indexOf("/mobile")) {
            String accessTokenFromRequest = request.getParameter(ApplicationConstant.ACCESS_TOKEN);
            if (Objects.equals(accessTokenFromRequest, accessTokenFromSession)) {
                return true;
            }
        }

        if (-1 != path.indexOf("/web")) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (ApplicationConstant.ACCESS_TOKEN.equals(cookie.getName())) {
                        String accessTokenFromRequest = cookie.getValue();
                        if (Objects.equals(accessTokenFromRequest, accessTokenFromSession)) {
                            return true;
                        }
                    }

                }
            }

        }

        log.debug("denied this path {}  ", path);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // System.out.println("Post-handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                                                                                                                       throws Exception {
        // System.out.println("After completion handle");
    }
}