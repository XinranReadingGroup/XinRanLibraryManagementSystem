
package com.xinran.controller.web;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xinran.constant.ApplicationConstant;
import com.xinran.controller.common.AbstractUserController;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 Apr 8, 2015 7:50:59 AM
 */
@RestController(value = "webUserController")
// @RequestMapping("/")
public class UserController extends AbstractUserController {



    protected AjaxResult doSignInOrSignUp(HttpServletRequest request, HttpServletResponse response, Long userId) {
        HttpSession session = request.getSession();

        Map<String, String> jsonMap = Maps.newHashMapWithExpectedSize(1);
        // TODO test 60天不过期
        int cookieAndSessionLiveTime = Long.valueOf(TimeUnit.DAYS.toSeconds(60L)).intValue();
        session.setMaxInactiveInterval(cookieAndSessionLiveTime);
        String radomAccessToken = RandomStringUtils.randomAlphanumeric(10);
        session.setAttribute(ApplicationConstant.USER_ID, userId);
        session.setAttribute(ApplicationConstant.ACCESS_TOKEN, radomAccessToken);

        Cookie cookie = new Cookie(ApplicationConstant.ACCESS_TOKEN, radomAccessToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieAndSessionLiveTime);
        response.addCookie(cookie);
        jsonMap.put(ApplicationConstant.ACCESS_TOKEN, radomAccessToken);

        return AjaxResultBuilder.buildSuccessfulResult(jsonMap);
    }

    /**
     * 登录页面
     */
    @RequestMapping("/user/session/signIn")
    public ModelAndView newSessionSignIn(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("newSessionSignIn");
    }

    /**
     * 注册页面
     */
    @RequestMapping("/user/session/signUp")
    public ModelAndView newSessionSignUp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("newSessionSignUp");

    }


}
