package com.xinran.controller.common;

import com.xinran.constant.SystemResultCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.XinranCheckedException;
import com.xinran.pojo.Activity;
import com.xinran.pojo.Pagination;
import com.xinran.pojo.User;
import com.xinran.service.ActivityService;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author gsy
 */
public class AbstractActivityController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    protected UserService userService;

    @Autowired
    protected ActivityService activityService;

    @RequestMapping("/activities")
    public
    @ResponseBody
    AjaxResult listActivities(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "status", required = true) String status,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        List<Activity> activities = null;
        if ("all".equals(status)) {
            activities = listActivities(uid, pageNo, pageSize);
        } else if ("available".equals(status)) {
            activities = listAvailableActivities(uid, pageNo, pageSize);
        } else {
            return AjaxResultBuilder.buildFailedResult(new XinranCheckedException(SystemResultCode.WrongActivityQueryStatus));
        }
        return AjaxResultBuilder.buildSuccessfulResult(activities);
    }


    @RequestMapping("/activity/new")
    public
    @ResponseBody
    AjaxResult createActivity(@ModelAttribute Activity activity,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        boolean ret = addActivity(uid,activity);
        return AjaxResultBuilder.buildSuccessfulResult(ret);
    }

    @RequestMapping("/activity/{activityId}")
    public
    @ResponseBody
    AjaxResult activityDetail(@PathVariable(value = "activityId") Long activityId,
                              HttpServletRequest request) {
        Activity activity = activityService.findActivityById(activityId);
        return AjaxResultBuilder.buildSuccessfulResult(activity);
    }

    @RequestMapping("/activity/publish/{activityId}")
    public
    @ResponseBody
    AjaxResult publishActivity(@PathVariable(value = "activityId") Long activityId,
                               HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        boolean ret = publishActivity(uid,activityId);
        return AjaxResultBuilder.buildSuccessfulResult(ret);
    }


    @RequestMapping("/activity/cancel/{activityId}")
    public
    @ResponseBody
    AjaxResult cancelactivity(@PathVariable(value = "activityId") Long activityId,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        boolean ret = cancelActivity(uid,activityId);
        return AjaxResultBuilder.buildSuccessfulResult(ret);
    }

    protected List<Activity> listActivities(Long uid, Integer pageNo, Integer pageSize) {
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user)) {
            Pagination page = new Pagination();
            if (pageNo != null && pageNo >= 0) {
                page.setCurrent(pageNo);
            }
            if (pageSize != null && pageSize > 0) {
                page.setSize(pageSize);
            }
            List<Activity> activities = activityService.queryActivityWithPagination(page);
            return activities;
        }
        return null;
    }

    protected List<Activity> listAvailableActivities(Long uid, Integer pageNo, Integer pageSize) {
        Pagination page = new Pagination();
        if (pageNo != null && pageNo >= 0) {
            page.setCurrent(pageNo);
        }
        if (pageSize != null && pageSize > 0) {
            page.setSize(pageSize);
        }
        List<Activity> activities = activityService.queryAvailableActivityWithPagination(uid, page);
        return activities;
    }

    protected boolean cancelActivity(Long uid, Long activityId){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user)) {
            return activityService.cancelActivity(activityId);
        }
        return false;
    }

    protected boolean publishActivity(Long uid, Long activityId){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user)) {
            return activityService.publishActivity(activityId);
        }
        return false;
    }

    protected boolean addActivity(Long uid, Activity activity){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user)) {
            activityService.createActivity(activity);
            return true;
        }
        log.error("current user id is not the admin role,user id is {}",uid);
        return false;
    }

}
