package com.ikamobile.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangcheng on 2016/10/10.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamAttr {

    ParamLocation location() default ParamLocation.URL;

    String name();

    enum ParamLocation{
        PATH,URL,CONTENT;
    }
}
