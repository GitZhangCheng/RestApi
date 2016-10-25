package com.ikamobile.common.proxy;

import com.ikamobile.common.annotations.RestAPIService;
import com.ikamobile.common.exception.ServiceNotExistException;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng on 2016/10/10.
 */
public class ServiceProxyFactory {

    //初始化工厂类。扫描所有service

    private static ServiceProxyFactory instance;

    Map<Class,Object> proxys = new HashMap<>();

    private RestConfig config;

    private ParamHandler handler;

    private ServiceProxyFactory(RestConfig config,ParamHandler handler){
        this.config = config;
        this.handler = handler;
    }

    public synchronized static ServiceProxyFactory getInstance(){
        return instance==null?getInstance(new RestConfig()):instance;
    }

    public synchronized static ServiceProxyFactory getInstance(RestConfig config){
        if(config == null){
            config = new RestConfig();
        }
        if(instance == null){
            instance = new ServiceProxyFactory(config,null);
        }
        return instance;
    }

    public synchronized static ServiceProxyFactory getInstance(ParamHandler handler){
        if(instance == null){
            instance = new ServiceProxyFactory(new RestConfig(),handler);
        }
        return instance;
    }

    public synchronized static ServiceProxyFactory getInstance(RestConfig config,ParamHandler handler){
        if(config == null){
            config = new RestConfig();
        }
        if(instance == null){
            instance = new ServiceProxyFactory(config,handler);
        }
        return instance;
    }






    //获取代理类
    public <T>T getProxy(Class<T> interfaceName) throws ServiceNotExistException{
        if(proxys.containsKey(interfaceName)){
            return (T)proxys.get(interfaceName);
        }
        if(!interfaceName.isInterface()){
            throw new ServiceNotExistException("服务定义必须是接口");
        }
        if(interfaceName.getAnnotation(RestAPIService.class)==null) {
            throw new ServiceNotExistException("服务需要RestService注解");
        }
        T t = (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{interfaceName}, new ServiceProxy(config,handler));
        proxys.put(interfaceName,t);
        return t;
    }


}
