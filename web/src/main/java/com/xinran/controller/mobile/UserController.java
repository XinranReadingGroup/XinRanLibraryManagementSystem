
package com.xinran.controller.mobile;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.xinran.constant.ApplicationConstant;
import com.xinran.constant.SystemResultCode;
import com.xinran.controller.common.AbstractUserController;
import com.xinran.controller.util.MobileSessionHolder;
import com.xinran.exception.UserException;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 Apr 8, 2015 7:50:59 AM
 */
@RestController(value = "mobileUserController")
@RequestMapping("/mobile")
public class UserController extends AbstractUserController {

    @RequestMapping("/user/signOut")
    public @ResponseBody AjaxResult signOut(@RequestParam(value = ApplicationConstant.ACCESS_TOKEN) String accessToken,
                                            HttpServletRequest request) {
        try {
            request.getSession().invalidate();

            // TODO
            userService.signOut(null);
            return AjaxResultBuilder.buildSuccessfulResult("ok");

        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(e);

        }

    }

    protected AjaxResult doSignInOrSignUp(HttpServletRequest request, HttpServletResponse response, Long userId,
                                          String nickName) {
        HttpSession session = request.getSession();

        Map<String, String> jsonMap = Maps.newHashMapWithExpectedSize(1);
        // TODO test 60天不过期
        session.setMaxInactiveInterval(Long.valueOf(TimeUnit.DAYS.toSeconds(60L)).intValue());
        String radomAccessToken = RandomStringUtils.randomAlphanumeric(10);
        session.setAttribute(ApplicationConstant.USER_ID, userId);
        session.setAttribute(ApplicationConstant.ACCESS_TOKEN, radomAccessToken);
        MobileSessionHolder.attachUserIdToAccessToken(radomAccessToken, userId);
        jsonMap.put(ApplicationConstant.ACCESS_TOKEN, radomAccessToken);
        return AjaxResultBuilder.buildSuccessfulResult(jsonMap);
    }


    /**
     * 登录页面
     */
    @RequestMapping("/user/session/signIn")
    public @ResponseBody AjaxResult newSessionSignIn(HttpServletRequest request, HttpServletResponse response) {
        return AjaxResultBuilder.buildFailedResult(SystemResultCode.UserNotLoginedInOrAccessTokenInvalid.getCode(), SystemResultCode.UserNotLoginedInOrAccessTokenInvalid.getDesc());

    }

}
