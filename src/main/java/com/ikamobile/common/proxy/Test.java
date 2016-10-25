package com.ikamobile.common.proxy;

import com.ikamobile.service.result.FlightSearchResult;
import com.ikamobile.common.exception.ServiceNotExistException;
import com.ikamobile.service.FlightService;

/**
 * Created by zhangcheng on 2016/10/10.
 */
public class Test {
    public static void main(String[] args) throws ServiceNotExistException {
        ServiceProxyFactory factory = ServiceProxyFactory.getInstance(new GlobalParamHandlerImpl());
        FlightService proxy = factory.getProxy(FlightService.class);
        long l = System.currentTimeMillis();
        FlightSearchResult flightSearchResult = proxy.searchFlight(132, "CTU", "PEK", "2016-10-30");
        System.out.println(System.currentTimeMillis()-l+"ms");
    }
}
