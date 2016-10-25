package com.ikamobile.common.proxy;

import java.util.Map;

/**
 * Created by zhangcheng on 2016/10/13.
 */
public class ApiParams {

    Map<String,String> params;

    Map<String,String> pathParam;

    Object contentParam;

    public Object getContentParam() {
        return contentParam;
    }

    public void setContentParam(Object contentParam) {
        this.contentParam = contentParam;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getPathParam() {
        return pathParam;
    }

    public void setPathParam(Map<String, String> pathParam) {
        this.pathParam = pathParam;
    }
}
