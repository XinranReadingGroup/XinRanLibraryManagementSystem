package com.xinran.controller.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        	User   user = userService.signUp(identifier, password, nickName);
            return doSignInOrSignUp(request, response, user);
        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }

    }

    protected abstract AjaxResult doSignInOrSignUp(HttpServletRequest request, HttpServletResponse response,
                                                   User   user);

    
    //登录
    @RequestMapping("/user/signIn")
    public @ResponseBody AjaxResult signIn(@RequestParam(value = "userIdentifier") String identifier,
                                           @RequestParam(value = "password") String password,
                                           HttpServletRequest request, HttpServletResponse response) {
        try {
        	User   user = userService.signIn(identifier, password);
            return doSignInOrSignUp(request, response,    user);
        } catch (UserException e) {
            return AjaxResultBuilder.buildFailedResult(e);
        }

    }





    protected UserVO buildUserVO(HttpServletRequest request) {
        Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
        User user = userService.findUserByUserId(userIdInSession);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

//    @RequestMapping("/user/profile")
//    public @ResponseBody AjaxResult viewProfile(
//                                             HttpServletRequest request) throws UserException {
//            Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
//            User user = userService.findUserByUserId(userIdInSession);
//            UserVO userVO = new UserVO();
//            BeanUtils.copyProperties(user, userVO);
//            return AjaxResultBuilder.buildSuccessfulResult(userVO);
//
//    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public @ResponseBody AjaxResult editProfile(
                                             @RequestParam(value = "area",required = false) String area,
                                             @RequestParam(value = "nickName",required = false) String nickName,
                                             @RequestParam(value = "imgId",required = false) String imgId,
                                             @RequestParam(value = "signature",required = false) String signature,
                                             HttpServletRequest request) throws UserException {
            Long userIdInSession = UserIdenetityUtil.getCurrentUserId(request);
            User user = userService.findUserByUserId(userIdInSession);
            if(!StringUtils.isEmpty(nickName)){
                user.setNickName(nickName);
            }
            if(!StringUtils.isEmpty(imgId)){
            user.setImgId(imgId);

            }
            if(!StringUtils.isEmpty(area)){
            user.setArea(area);

            }
            if(!StringUtils.isEmpty(signature)){
            user.setSignature(signature);
            }

            userService.updateUser(user);

            return AjaxResultBuilder.buildSuccessfulResult("ok");

    }

}
