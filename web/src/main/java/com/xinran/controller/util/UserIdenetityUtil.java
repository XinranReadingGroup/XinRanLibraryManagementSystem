package com.xinran.controller.util;

import javax.servlet.http.HttpServletRequest;

import com.xinran.constant.ApplicationConstant;

/**
 * @author 高海军 帝奇 Apr 25, 2015 1:16:44 PM
 */
public abstract class UserIdenetityUtil {

    public static Long getCurrentUserId(HttpServletRequest request) {
        Long userId =  (Long) request.getSession().getAttribute(ApplicationConstant.USER_ID);
        if(null != userId){
            return userId;
        }else{
            String accessToken = request.getParameter(ApplicationConstant.ACCESS_TOKEN);
              userId = MobileSessionHolder.getCurrentUserIdByAccessToken(accessToken); 
              return userId;
        }
    }

    public static String getCurrentAccessToken(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(ApplicationConstant.ACCESS_TOKEN);
    }
}
