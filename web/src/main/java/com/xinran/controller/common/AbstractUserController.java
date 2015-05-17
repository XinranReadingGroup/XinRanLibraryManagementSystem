package com.xinran.controller.common;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.constant.ApplicationConstant;
import com.xinran.constant.ExceptionCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.UserException;
import com.xinran.pojo.User;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.UserVO;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 Apr 25, 2015 11:16:35 AM
 */
public abstract class AbstractUserController {

    // private static final String userIdentifier = "userIdentifier";
    // private static final String password = "password";

    @Autowired
    private UserService userService;

    @RequestMapping("/user/signUp")
    public @ResponseBody AjaxResult signUp(@RequestParam(value = "userIdentifier") String identifier,
                                           @RequestParam(value = "password") String password,
                                           @RequestParam(value = "nickName") String nickName,
                                           HttpServletRequest request, HttpServletResponse response) {
        Long userId;
        try {
            userId = userService.signUpForMobileIndentifier(identifier, password, nickName);
            return doSignInOrSignUp(request, response, userId);
        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());
        }

    }

    protected abstract AjaxResult doSignInOrSignUp(HttpServletRequest request, HttpServletResponse response, Long userId);

    @RequestMapping("/user/signIn")
    public @ResponseBody AjaxResult signIn(@RequestParam(value = "userIdentifier") String identifier,
                                           @RequestParam(value = "password") String password,
                                           HttpServletRequest request, HttpServletResponse response) {
        Long userId;
        try {
            userId = userService.signIn(identifier, password);
            return doSignInOrSignUp(request, response, userId);
        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());
        }

    }


    @RequestMapping("/user/signOut")
    public @ResponseBody AjaxResult signOut(@RequestParam(value = ApplicationConstant.ACCESS_TOKEN) String accessToken,
                                            HttpServletRequest request) {
        try {
            request.getSession().invalidate();
            userService.signOut(accessToken);
            return AjaxResultBuilder.buildSuccessfulResult("ok");

        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(400, e.getCode());

        }

    }

    @RequestMapping("/user/profile")
    public @ResponseBody AjaxResult viewMyselfUser(
                                                   HttpServletRequest request) throws UserException {
        UserVO userVO = viewMyselfUser1(request);
        return AjaxResultBuilder.buildSuccessfulResult(userVO);
    }

    private UserVO viewMyselfUser1(HttpServletRequest request) {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        User user = userService.findUserByUserId(userIdInSession);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }


    @RequestMapping("/user/{userId}/profile")
    public @ResponseBody AjaxResult viewUser(@PathVariable(value = ApplicationConstant.USER_ID) Long userId,
                                             HttpServletRequest request) throws UserException {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        if (Objects.equals(userIdInSession, userId)) {
            User user = userService.findUserByUserId(userIdInSession);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return AjaxResultBuilder.buildSuccessfulResult(userVO);

        } else {
            throw new UserException(ExceptionCode.CantViewUserProfilerIfItIsNotYours.getCode());
        }

    }

    @RequestMapping(value = "/user/{userId}/profile", method = RequestMethod.POST)
    public @ResponseBody AjaxResult editUser(@PathVariable(value = ApplicationConstant.USER_ID) Long userId,
                                             @RequestParam(value = "area") String area,
                                             @RequestParam(value = "nickName") String nickName,
                                             @RequestParam(value = "imgId") String imgId,

                                             HttpServletRequest request) throws UserException {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        if (Objects.equals(userIdInSession, userId)) {
            User user = userService.findUserByUserId(userIdInSession);
            user.setNickName(nickName);
            user.setImgId(imgId);
            user.setArea(area);
            userService.updateUser(user);

            return AjaxResultBuilder.buildSuccessfulResult("ok");

        } else {
            throw new UserException(ExceptionCode.CantViewUserProfilerIfItIsNotYours.getCode());
        }

    }

}
