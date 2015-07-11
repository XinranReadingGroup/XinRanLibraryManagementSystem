package com.xinran.springmvc.interceptor;

/**
 * @author 高海军 帝奇 Apr 20, 2015 9:55:10 PM
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.AuthorizationException;

public class UserSessionInterceptor implements HandlerInterceptor {

    // private Logger log = LoggerFactory.getLogger(this.getClass());

    private static List<String> noNeedLoginURLs = Lists.newArrayList();



    // {
    // // web 首页
    // // noNeedLoginURLs.add("/");
    //
    // // mobile 首页
    // noNeedLoginURLs.add("/mobile");
    //
    // noNeedLoginURLs.add("/search");
    //
    // noNeedLoginURLs.add("/user/session/signIn");
    // noNeedLoginURLs.add("/user/session/signUp");
    //
    // noNeedLoginURLs.add("/user/signUp");
    // noNeedLoginURLs.add("/user/signIn");
    // noNeedLoginURLs.add("/user/signOut");
    //
    // noNeedLoginURLs.add("/book/isbn");
    //
    // }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        
        
        if ("/".equals(requestURI) || "/mobile".equals(requestURI)) {
            return true;
        }

        for (String url : noNeedLoginURLs) {
            if (-1 != requestURI.indexOf(url)) {
                return true;
            }
        }

        // intercept
        Long currentUserIdFromSession = UserIdenetityUtil.getCurrentUserId(request);
        if (currentUserIdFromSession == null) {
            // see http://stackoverflow.com/questions/12713873/spring-3-1-how-do-you-send-all-exception-to-one-page
            throw new AuthorizationException();
        } else {
            return true;
        }
        // // 白名单机制, 注册,登录,首页,搜索,数据详情,详情二维码,其他都需要登录登录
        // String requestURI = request.getRequestURI();
        //
        //
        // for (String url : noNeedLoginURLs) {
        // // TODO 使用注解会更好点
        // if (-1 != requestURI.indexOf(url)) {
        // return true;
        // }
        //
        // }
        //
        // Long currentUserIdFromSession = UserIdenetityUtil.getCurrentUserId(request);
        // String accessTokenFromSession = UserIdenetityUtil.getCurrentAccessToken(request);
        //
        // if (null == currentUserIdFromSession) {
        // return false;
        // }
        //
        // if (-1 != requestURI.indexOf("/mobile")) {
        // String accessTokenFromRequest = request.getParameter(ApplicationConstant.ACCESS_TOKEN);
        // if (Objects.equals(accessTokenFromRequest, accessTokenFromSession)) {
        // return true;
        // }
        // } else {
        // Cookie[] cookies = request.getCookies();
        // if (null != cookies) {
        // for (Cookie cookie : cookies) {
        // if (ApplicationConstant.ACCESS_TOKEN.equals(cookie.getName())) {
        // String accessTokenFromRequest = cookie.getValue();
        // if (Objects.equals(accessTokenFromRequest, accessTokenFromSession)) {
        // return true;
        // }
        // }
        //
        // }
        // }
        //
        // }
        //
        // log.debug("denied this path {}  ", requestURI);
        // return false;
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

    public static void setNoNeedLoginURLs(List<String> noNeedLoginURLs) {
        UserSessionInterceptor.noNeedLoginURLs = noNeedLoginURLs;
    }
}