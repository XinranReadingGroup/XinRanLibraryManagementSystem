package com.xinran.service.impl;

import com.xinran.constant.ActivityStatus;
import com.xinran.dao.mapper.ActivityMapper;
import com.xinran.pojo.Activity;
import com.xinran.pojo.Pagination;
import com.xinran.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gsy on 2015/11/22.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Activity findActivityById(Long id) {
        return activityMapper.findById(id);
    }

    @Override
    public void createActivity(Activity activity) {
        activityMapper.addActivity(activity);
    }

    @Override
    public List<Activity> queryActivityWithPagination(Pagination page) {
        return activityMapper.findActivities(page);
    }

    @Override
    public List<Activity> queryAvailableActivityWithPagination(Long userId, Pagination page) {
        return activityMapper.findAvailableActivities(page);
    }

    @Override
    public boolean cancelActivity(Long id) {
        Activity activity = findActivityById(id);
        if(activity==null){
            return false;
        }
        if(activity.getStatus()== ActivityStatus.CANCEL.getStatus()){
            return true;
        }
        activity.setStatus(ActivityStatus.CANCEL.getStatus());
        int ret = activityMapper.updateActivity(activity);
        return ret ==1;
    }

    @Override
    public boolean publishActivity(Long id) {
        Activity activity = findActivityById(id);
        if(activity==null){
            return false;
        }
        if(activity.getStatus()== ActivityStatus.PUBLISH.getStatus()){
            return true;
        }
        activity.setStatus(ActivityStatus.PUBLISH.getStatus());
        int ret = activityMapper.updateActivity(activity);
        return ret ==1;
    }
}
