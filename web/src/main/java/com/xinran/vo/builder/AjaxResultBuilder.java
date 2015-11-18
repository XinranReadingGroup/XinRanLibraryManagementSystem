
package com.xinran.vo.builder;

import com.xinran.exception.XinranCheckedException;
import com.xinran.vo.AjaxResult;

/**
 * @author 高海军 帝奇 Apr 8, 2015 8:03:10 PM
 */
public class AjaxResultBuilder {

    public static AjaxResult buildSuccessfulResult(Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(200);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult buildFailedResult(Integer code, Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(code);
        ajaxResult.setData(data);
        return ajaxResult;
    }
    
    public static AjaxResult buildFailedResult(XinranCheckedException e) {
        return buildFailedResult(e.getCode(),e.getMessage());
    }
}
