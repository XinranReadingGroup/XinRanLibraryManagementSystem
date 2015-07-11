package com.xinran.springmvc.interceptor;

/**
 * @author 高海军 帝奇 Apr 20, 2015 9:55:10 PM
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.AuthorizationException;

public class UserSessionInterceptor implements HandlerInterceptor {

     private Logger log = LoggerFactory.getLogger(this.getClass());

    private  List<String> noNeedLoginURLs ;



	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        log.debug("requestURI is {} ,noNeedLoginURLs is  ", requestURI,JSON.toJSONString(noNeedLoginURLs));

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

	public void setNoNeedLoginURLs(List<String> noNeedLoginURLs) {
		this.noNeedLoginURLs = noNeedLoginURLs;
	}
  
}