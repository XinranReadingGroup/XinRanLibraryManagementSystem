
package com.xinran.controller.mobile;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.xinran.constant.ApplicationConstant;
import com.xinran.exception.SignInValidationException;
import com.xinran.exception.SignOutValidationException;
import com.xinran.exception.SignUpValidationException;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 Apr 8, 2015 7:50:59 AM
 */
@RestController
@RequestMapping("/mobile")
public class UserController {

    private static final String userIdentifier = "userIdentifier";
    private static final String password       = "password";

    @Autowired
    private UserService userService;

    @RequestMapping("/user/signUp")
    public @ResponseBody AjaxResult signUp(@RequestParam(value = userIdentifier) String identifier,
                                           @RequestParam(value = password) String password, HttpServletRequest request) {
        Long userId;
        try {
            userId = userService.signUpForMobileIndentifier(identifier, password);
            return doSignInOrSignUp(request, userId);
        } catch (SignUpValidationException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getMessage());
        }

    }


    private AjaxResult doSignInOrSignUp(HttpServletRequest request, Long userId) {
        HttpSession session = request.getSession(true);


        Map<String, String> jsonMap = Maps.newHashMapWithExpectedSize(1);
        // TODO test 60天不过期
        session.setMaxInactiveInterval(Long.valueOf(TimeUnit.DAYS.toDays(60)).intValue());
        String sessionId = session.getId();
        session.setAttribute("userId", userId);
        session.setAttribute("accessToken", sessionId);
        jsonMap.put("accessToken", sessionId);
        return AjaxResultBuilder.buildSuccessfulResult(jsonMap);
    }


    @RequestMapping("/user/signIn")
    public @ResponseBody AjaxResult signIn(@RequestParam(value = userIdentifier) String identifier,
                                           @RequestParam(value = password) String password, HttpServletRequest request) {
        Long userId;
        try {
            userId = userService.signIn(identifier, password);
            return doSignInOrSignUp(request, userId);
        } catch (SignInValidationException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getMessage());
        }

    }



    @RequestMapping("/user/signOut")
    public @ResponseBody AjaxResult signOut(@RequestParam(value = ApplicationConstant.ACCESS_TOKEN) String accessToken,
                                            HttpServletRequest request) {
        try {
            request.getSession().invalidate();
            userService.signOut(accessToken);
            return AjaxResultBuilder.buildSuccessfulResult("ok");

        } catch (SignOutValidationException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getMessage());

        }


    }

}
