package com.ikamobile.common.proxy;

import com.alibaba.fastjson.JSON;
import com.ikamobile.common.annotations.RequestInfo;
import com.ikamobile.common.utils.BeanUtils;
import com.ikamobile.common.utils.HttpsUtils;
import com.ikamobile.common.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import com.ikamobile.common.annotations.ParamAttr;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangcheng on 2016/10/10.
 */
public class ServiceProxy implements InvocationHandler {

    RestConfig config;

    ParamHandler handler;

    Map<String,ApiAttr> apiAttrMap = new HashMap<>();


    public ServiceProxy(RestConfig config) {
        this.config = config;
    }
    public ServiceProxy(ParamHandler handler) {
        this.handler = handler;
    }
    public ServiceProxy(RestConfig config, ParamHandler handler) {
        this.config = config;
        this.handler = handler;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestInfo annotation = method.getAnnotation(RequestInfo.class);
        RequestInfo.HttpMethod httpMethod = annotation.method();
        System.out.println(httpMethod);
        String url = annotation.url();
        System.out.println(url);

        ApiAttr apiAttr = getApiAttr(method);
        ApiParams apiParams = getApiParams(apiAttr.getParamAttrs(),args);
        String requestUrl = initURL(url,apiParams.getPathParam());

        String ret = null;
        if(apiAttr.getHttpMethod().equals(RequestInfo.HttpMethod.GET)){
            ret = HttpsUtils.get(requestUrl,apiParams.getParams());
        }else if(apiAttr.getHttpMethod().equals(RequestInfo.HttpMethod.POST)){
            if(apiAttr.getPostType().equals(RequestInfo.PostType.FORM)){
                ret = HttpsUtils.postForm(url, BeanUtils.bean2Map(apiParams.getContentParam()));
            }else if(apiAttr.getPostType().equals(RequestInfo.PostType.JSON)){
                ret = HttpsUtils.postJSON(url, JSON.toJSONString(apiParams.getContentParam()));
            }
        }
        System.out.println("result:"+ret);
        System.out.println(method.getReturnType().getName());
        Object o = JSON.parseObject(ret, method.getReturnType());
        return o;
    }

    private String initURL(String url, Map<String, String> pathParam) throws Exception {
        String r = url;
        r.replaceAll(" ","");//删除空格
        if(pathParam!=null&&!pathParam.isEmpty()) {
            for (String s : pathParam.keySet()) {
                r = r.replaceAll("\\{" + s.trim() + "\\}", pathParam.get(s));
            }
        }

        Pattern pattern = Pattern.compile("(\\{\\w+\\})");
        Matcher m = pattern.matcher(r);
        String error="";
        while (m.find()){
            error+=m.group()+" ";
        }
        if(StringUtils.isNotEmpty(error)){
            throw new Exception("not find param values:"+error);
        }
        return config.getDomain()+r;
    }



    private ApiAttr getApiAttr(Method method){
        RequestInfo annotation = method.getAnnotation(RequestInfo.class);
        String key = annotation.method().name() + ":" + annotation.url();
        if(apiAttrMap.containsKey(key)){
            return apiAttrMap.get(key);
        }else {
            ApiAttr apiAttr = new ApiAttr();
            apiAttr.setUrl(annotation.url());
            apiAttr.setHttpMethod(annotation.method());
            apiAttr.setPostType(annotation.postType());
            Parameter[] parameters = method.getParameters();
            ApiAttr.ParameterAttr[] pas = new ApiAttr.ParameterAttr[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                ParamAttr a = parameters[i].getAnnotation(ParamAttr.class);
                ApiAttr.ParameterAttr pa = new ApiAttr.ParameterAttr();
                pa.setLoacation(a.location());
                pa.setName(a.name());
                pas[i] = pa;
            }
            apiAttr.setParamAttrs(pas);
            apiAttrMap.put(key, apiAttr);
            return apiAttr;
        }
    }

    private ApiParams getApiParams(ApiAttr.ParameterAttr[] parameterAttrs, Object[] args) {
        ApiParams apiParams = new ApiParams();
        System.out.println("---------params:");
        Map<String,String> paramMap= new HashMap<>();
        Map<String,String> pathParamMap= new HashMap<>();
        Map<String,Object> contentParamMap= new HashMap<>();
        for (int i=0;i<parameterAttrs.length;i++) {
            if(parameterAttrs[i].location.equals(ParamAttr.ParamLocation.URL)){
                paramMap.put(parameterAttrs[i].name,String.valueOf(args[i]));
            }else if(parameterAttrs[i].location.equals(ParamAttr.ParamLocation.PATH)){
                pathParamMap.put(parameterAttrs[i].name,String.valueOf(args[i]));
            }else{
                contentParamMap.put(parameterAttrs[i].name,args[i]);
            }
        }
        apiParams.setPathParam(pathParamMap);
        apiParams.setParams(paramMap);
        apiParams.setContentParam(contentParamMap);

        if(handler!=null){
            handler.addParam(apiParams);
        }

        return apiParams;
    }

}
