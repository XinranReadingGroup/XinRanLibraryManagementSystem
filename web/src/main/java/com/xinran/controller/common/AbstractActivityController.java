package com.xinran.controller.common;

import com.xinran.constant.ScoreReason;
import com.xinran.constant.SystemResultCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.XinranCheckedException;
import com.xinran.pojo.Activity;
import com.xinran.pojo.Pagination;
import com.xinran.pojo.Score;
import com.xinran.pojo.User;
import com.xinran.service.ActivityService;
import com.xinran.service.ScoreService;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsy
 */
public class AbstractActivityController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    protected UserService userService;

    @Autowired
    protected ActivityService activityService;

    @Autowired
    protected ScoreService scoreService;

    @RequestMapping("/activities")
    public
    @ResponseBody
    AjaxResult listActivities(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "status", required = true) String status,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        Map<String,Object> map;
        if ("all".equals(status)) {
            map = listActivities(uid, pageNo, pageSize);
        } else if ("available".equals(status)) {
            map = listAvailableActivities(uid, pageNo, pageSize);
        } else {
        	throw new XinranCheckedException(SystemResultCode.BAD_ACTIVITY_STATUS);
        }
        return AjaxResultBuilder.buildSuccessfulResult(map);

    }


    @RequestMapping("/activity/new")
    public
    @ResponseBody
    AjaxResult createActivity(@ModelAttribute Activity activity,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        if(activity!=null&activity.getId()!=null){
            boolean ret = upateActivity(uid,activity);
            return AjaxResultBuilder.buildSuccessfulResult(ret);
        }else{
            boolean ret = addActivity(uid,activity);
            return AjaxResultBuilder.buildSuccessfulResult(ret);
        }
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
    AjaxResult cancelActivity(@PathVariable(value = "activityId") Long activityId,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        boolean ret = cancelActivity(uid,activityId);
        return AjaxResultBuilder.buildSuccessfulResult(ret);
    }

    @RequestMapping("/activity/convert/{activityId}")
    public
    @ResponseBody
    AjaxResult convertActivity(@PathVariable(value = "activityId") Long activityId,
                              HttpServletRequest request) {
        Long uid = UserIdenetityUtil.getCurrentUserId(request);
        boolean ret = convertActivity(uid,activityId);
        return AjaxResultBuilder.buildSuccessfulResult(ret);
    }

    @InitBinder("uploadItem")
    void initBinder(WebDataBinder binder) {
        binder.setValidator(new UploadValidator());
    }
    @RequestMapping(value = "/activity/upload", method = RequestMethod.POST)
    public @ResponseBody AjaxResult upload(@Validated @ModelAttribute("uploadItem") FileUploadForm uploadForm,
                                           BindingResult result,
                                           ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        String saveDirectory = "/home/admin/xinran/upload/img/activity/";
        //String saveDirectory = "d:\\workspace\\jdw\\XinRanLibraryManagementSystem\\";

        MultipartFile multipartFile = uploadForm.getFile();

        if (null != multipartFile) {
            try {
                String fileName = System.currentTimeMillis() + File.pathSeparatorChar +getFileName(multipartFile.getOriginalFilename());
                multipartFile.transferTo(new File(saveDirectory + fileName));
                return AjaxResultBuilder.buildSuccessfulResult("/img/activity/"+fileName);
            } catch (IllegalStateException | IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return AjaxResultBuilder.buildSuccessfulResult(false);
    }

    private String getFileName(String path){
        if(StringUtils.isNotBlank(path)){
            if(path.lastIndexOf("/")!=-1){
                return path.substring(path.lastIndexOf("/")+1,path.length());
            }else if(path.lastIndexOf("\\")!=-1){
                return path.substring(path.lastIndexOf("\\")+1,path.length());
            }
            return path;
        }
        return "1.jpg";
    }


    protected Map<String,Object> listActivities(Long uid, Integer pageNo, Integer pageSize) {
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user.getUserName())) {
            Pagination page = new Pagination();
            if (pageNo != null && pageNo >= 0) {
                page.setCurrent(pageNo);
            }
            if (pageSize != null && pageSize > 0) {
                page.setSize(pageSize);
            }
            List<Activity> activities = activityService.queryActivityWithPagination(page);
            Map<String,Object> map = new HashMap<>(2);
            map.put("activities",activities);
            map.put("page",page);
            map.put("totalPage",page.getTotal()==0?0:(page.getTotal()-1)/page.getSize()+1);
            return map;
        }
        return null;
    }

    protected Map<String,Object> listAvailableActivities(Long uid, Integer pageNo, Integer pageSize) {
        Pagination page = new Pagination();
        if (pageNo != null && pageNo >= 0) {
            page.setCurrent(pageNo);
        }
        if (pageSize != null && pageSize > 0) {
            page.setSize(pageSize);
        }
        List<Activity> activities = activityService.queryAvailableActivityWithPagination(uid, page);
        Map<String,Object> map = new HashMap<>(2);
        map.put("activities",activities);
        map.put("page",page);
        map.put("totalPage",page.getTotal()==0?0:(page.getTotal()-1)/page.getSize()+1);
        return map;
    }

    protected boolean cancelActivity(Long uid, Long activityId){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user.getUserName())) {
            return activityService.cancelActivity(activityId);
        }
        return false;
    }

    protected boolean publishActivity(Long uid, Long activityId){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user.getUserName())) {
            return activityService.publishActivity(activityId);
        }
        return false;
    }

    protected boolean addActivity(Long uid, Activity activity){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user.getUserName())) {
            activityService.createActivity(activity);
            return true;
        }
        log.error("current user id is not the admin role,user id is {}",uid);
        return false;
    }

    protected boolean upateActivity(Long uid, Activity activity){
        User user = userService.findUserByUserId(uid);
        if (user !=null && userService.isAdmin(user.getUserName())) {
            activityService.updateActivity(activity);
            return true;
        }
        log.error("current user id is not the admin role,user id is {}",uid);
        return false;
    }


    protected boolean convertActivity(Long uid, Long activityId){
        try {
            Activity activity = activityService.findActivityById(activityId);

            Score score = new Score();
            score.setUserId(uid);
            score.setFactId(activity.getId());
            score.setScoreReason(ScoreReason.PARTY.getReason());

            Long scoreVal = activity.getScore();
            if ("add".equals(activity.getAction()) && scoreVal < 0 || "sub".equals(activity.getAction()) && scoreVal > 0) {
                scoreVal = -scoreVal;
            }
            score.setScoreValue(scoreVal.intValue());

            Integer currentScore = scoreService.queryTotalScoreByUserId(uid);
            if("sub".equals(activity.getAction()) && currentScore+scoreVal < 0 ){
                return false;
            }

            scoreService.addScore(score);

            Integer sumScore = scoreService.queryTotalScoreByUserId(uid);
            User user = userService.findUserByUserId(uid);
            user.setScore(sumScore);
            userService.updateUser(user);
        }catch(Exception e){
            log.error(String.format("update convert activity error, activityId is {},user is {}",activityId,uid),e);
            return false;
        }
        return true;
    }

}
