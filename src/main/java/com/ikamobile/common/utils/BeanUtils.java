package com.ikamobile.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng on 2016/8/29.
 */
public class BeanUtils {

    public static Map<String,String> bean2Map(Object obj){
        return bean2Map(obj, "");
    }

    public static Map<String,String> bean2Map(Object obj, String prefix){
        Map<String,String> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(value==null){
                        continue;
                    }
                    if(value instanceof List){
                        for (int i = 0; i < ((List) value).size(); i++) {

                            Object v = ((List) value).get(i);
                            if(isSimpleType(v)){
                                map.put(prefix + key + "[" + i + "]",String.valueOf(v));
                            }else {
                                map.putAll(bean2Map(v, prefix + key + "[" + i + "]."));
                            }
                        }
                    }else{
                        map.put(prefix+key, String.valueOf(value));
                    }
                }

            }
        }catch (Exception e){

        }

        return map;

    }

    private static boolean isSimpleType(Object o){
        return o instanceof String
                ||o instanceof Double
                ||o instanceof Integer
                ||o instanceof Float
                ||o instanceof Byte
                ||o instanceof Short
                ||o instanceof Boolean;

    }


}
