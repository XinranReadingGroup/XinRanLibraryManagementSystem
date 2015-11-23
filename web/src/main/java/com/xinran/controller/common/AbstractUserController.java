package com.xinran.controller.common;


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
import com.xinran.constant.SystemResultCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.UserException;
import com.xinran.pojo.User;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.UserVO;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 帝奇 Apr 25, 2015 11:16:35 AM
 */
public abstract class AbstractUserController {

    // private static final String userIdentifier = "userIdentifier";
    // private static final String password = "password";

    @Autowired
    protected UserService userService;
    
    
    //注册
    @RequestMapping("/user/signUp")
    public @ResponseBody AjaxResult signUp(@RequestParam(value = "userIdentifier") String identifier,
                                           @RequestParam(value = "password") String password,
                                           @RequestParam(value = "nickName") String nickName,
                                           HttpServletRequest request, HttpServletResponse response) {
        try {
        	Long  userId = userService.signUpForMobileIndentifier(identifier, password, nickName);
            return doSignInOrSignUp(request, response, userId, nickName);
        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }

    }

    protected abstract AjaxResult doSignInOrSignUp(HttpServletRequest request, HttpServletResponse response,
                                                   Long userId, String nickName);

    
    //登录
    @RequestMapping("/user/signIn")
    public @ResponseBody AjaxResult signIn(@RequestParam(value = "userIdentifier") String identifier,
                                           @RequestParam(value = "password") String password,
                                           HttpServletRequest request, HttpServletResponse response) {
        try {
        	Long   userId = userService.signIn(identifier, password);
            String nickName = userService.findUserByUserId(userId).getNickName();
            return doSignInOrSignUp(request, response, userId, nickName);
        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }

    }



    @RequestMapping("/user/profile")
    public ModelAndView viewMyselfUser(HttpServletRequest request) throws UserException {
        UserVO userVO = buildUserVO(request);
        return new ModelAndView("userProfile", "userVO", userVO);
    }

    private UserVO buildUserVO(HttpServletRequest request) {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        User user = userService.findUserByUserId(userIdInSession);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @RequestMapping("/user/{userId}/profile")
    public @ResponseBody AjaxResult viewOtherUser(@PathVariable(value = ApplicationConstant.USER_ID) Long userId,
                                             HttpServletRequest request) throws UserException {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        if (userIdInSession == userId) {
            User user = userService.findUserByUserId(userIdInSession);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return AjaxResultBuilder.buildSuccessfulResult(userVO);

        } else {
            throw new UserException(SystemResultCode.CantViewUserProfilerIfItIsNotYours );
        }

    }

    @RequestMapping(value = "/user/{userId}/profile", method = RequestMethod.POST)
    public @ResponseBody AjaxResult editUser(@PathVariable(value = ApplicationConstant.USER_ID) Long userId,
                                             @RequestParam(value = "area") String area,
                                             @RequestParam(value = "nickName") String nickName,
                                             @RequestParam(value = "imgId") String imgId,

                                             HttpServletRequest request) throws UserException {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        if (userIdInSession == userId) {
            User user = userService.findUserByUserId(userIdInSession);
            user.setNickName(nickName);
            user.setImgId(imgId);
            user.setArea(area);
            userService.updateUser(user);

            return AjaxResultBuilder.buildSuccessfulResult("ok");

        } else {
            throw new UserException(SystemResultCode.CantViewUserProfilerIfItIsNotYours );
        }

    }

}
