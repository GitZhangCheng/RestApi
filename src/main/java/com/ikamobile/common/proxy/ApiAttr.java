package com.ikamobile.common.proxy;


import com.ikamobile.common.annotations.ParamAttr;
import com.ikamobile.common.annotations.RequestInfo;

import java.lang.reflect.Type;

/**
 * Created by zhangcheng on 2016/10/12.
 */
public class ApiAttr {

    private String url;

    private RequestInfo.HttpMethod httpMethod;

    private RequestInfo.PostType postType;

    private ParameterAttr[] paramAttrs;

    private Type returnType;


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

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public Type getReturnType() {
        return returnType;
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
