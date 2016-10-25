package com.ikamobile.common.proxy;

import com.ikamobile.common.utils.MD5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局参数处理，添加验证信息
 * Created by zhangcheng on 2016/10/25.
 */
public class GlobalParamHandlerImpl implements ParamHandler {
    @Override
    public void addParam(ApiParams params) {
        if(params == null){
            params = new ApiParams();
            params.setParams(new HashMap<>());
        }
        params.getParams().putAll(getSign());
    }

    //增加一个接口，用于插入固定参数，url参数
    private Map<String,String> getSign() {
        String _ts = get_t();
        String sign = MD5Utils.getMd5(MD5Utils.getMd5("1" + _ts) + "sgAgLsgeAgneimxing");
        Map<String,String> signMap = new HashMap<>();
        signMap.put("_ts",_ts);
        signMap.put("sign",sign);
        signMap.put("pid","1");
        return signMap;
    }
    private static String get_t(){
        //现在系统毫秒milliseconds
        long milliseconds = System.currentTimeMillis();
        //现在系统秒数seconds
        long seconds = milliseconds/1000;
        return Long.toString(seconds);
    }
}
