package com.ikamobile.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangcheng on 2016/10/10.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestInfo {

    String url();

    HttpMethod method();

    PostType postType() default PostType.FORM;

    enum HttpMethod{
        POST,GET,DELETE,PUT;
    }
    enum PostType{
        FORM,JSON;
    }
}
