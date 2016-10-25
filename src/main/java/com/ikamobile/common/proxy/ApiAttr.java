package com.ikamobile.common.proxy;


import com.ikamobile.common.annotations.ParamAttr;
import com.ikamobile.common.annotations.RequestInfo;

/**
 * Created by zhangcheng on 2016/10/12.
 */
public class ApiAttr {

    String url;

    RequestInfo.HttpMethod httpMethod;

    RequestInfo.PostType postType;

    ParameterAttr[] paramAttrs;

    public ParameterAttr[] getParamAttrs() {
        return paramAttrs;
    }

    public void setParamAttrs(ParameterAttr[] paramAttrs) {
        this.paramAttrs = paramAttrs;
    }





    public RequestInfo.HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public RequestInfo.PostType getPostType() {
        return postType;
    }

    public String getUrl() {
        return url;
    }

    public void setHttpMethod(RequestInfo.HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setPostType(RequestInfo.PostType postType) {
        this.postType = postType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ParameterAttr {

        ParamAttr.ParamLocation location;
        String name;

        public void setLoacation(ParamAttr.ParamLocation loacation) {
            this.location = loacation;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
