package com.ikamobile.service.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guest on 16/3/23.
 */
@Data
public class SimpleResp<T> {
    public final static Integer SUCCESS_CODE = 0;
    public final static Integer SYS_ERROR_CODE = 99;
    public final static Integer BAD_REQUEST = 400;
    public final static Integer NOT_AUTH_CODE = 401;

    public final static Map<Integer,String> CODE_MESSAGE_MAP = new HashMap<Integer, String>(){{
        put(SUCCESS_CODE,"操作成功");
        put(SYS_ERROR_CODE,"系统错误");
        put(NOT_AUTH_CODE,"无权限操作");
    }};

    private int code;

    private String message;


    private T data;

    public SimpleResp(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public SimpleResp(int code){
        this.code = code;
        this.message = CODE_MESSAGE_MAP.get(code) == null ? "未知错误" : CODE_MESSAGE_MAP.get(code);
    }

    public SimpleResp(){
       this(SUCCESS_CODE);
    }

    public SimpleResp(int code, String message, T t) {
        this.code = code;
        this.message = message;
        this.data = t;
    }


}
