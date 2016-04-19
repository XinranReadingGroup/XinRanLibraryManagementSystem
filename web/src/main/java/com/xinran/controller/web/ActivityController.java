package com.xinran.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.common.AbstractActivityController;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.pojo.Activity;
import com.xinran.pojo.User;

/**
 * Created by gsy on 2015/11/22.
 */
@RestController(value = "webActivityController")
public class ActivityController extends AbstractActivityController{
    @RequestMapping("/admin/activity/new")
    public ModelAndView newActivity(@RequestParam(value = "id", required = false) Long activityId,
                                    HttpServletRequest request) {
        User user = userService.findUserByUserId(UserIdenetityUtil.getCurrentUserId(request));
        if (user !=null && userService.isAdmin(user.getUserName())) {
            if(activityId!=null){
                Activity activity = activityService.findActivityById(activityId);
                return new ModelAndView("newActivity","activity",activity);
            }else {
                return new ModelAndView("newActivity");
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/admin/activities")
    public ModelAndView activities(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   HttpServletRequest request) {
        Map<String,Object> map = listActivities(UserIdenetityUtil.getCurrentUserId(request), pageNo, pageSize);
        
        if(MapUtils.isNotEmpty(map)){
            List<Activity> list = (List<Activity>) map.get("activities");
            if(CollectionUtils.isNotEmpty(list)){
                for (Activity activity : list) {
                    if (activity.getStatus() == 0) {
                        long now = System.currentTimeMillis();
                        if (activity.getStartDate() != null && activity.getStartDate().getTime() > now) {
                            activity.setStatus(3);
                        } else if (activity.getEndDate() != null && activity.getEndDate().getTime() < now) {
                            activity.setStatus(2);
                        }
                    }
                } 
            }
            
        }
        
        
        return new ModelAndView("allActivities",map);
    }
}
