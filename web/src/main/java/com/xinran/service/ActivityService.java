package com.xinran.service;

import com.xinran.pojo.Activity;
import com.xinran.pojo.Pagination;

import java.util.List;

/**
 * Created by gsy on 2015/11/22.
 */
public interface ActivityService {

    Activity findActivityById(Long id);

    void createActivity(Activity activity);

    boolean updateActivity(Activity activity);

    List<Activity> queryActivityWithPagination(Pagination page);

    List<Activity> queryAvailableActivityWithPagination(Long userId, Pagination page);

    boolean cancelActivity(Long id);

    boolean publishActivity(Long id);
}
