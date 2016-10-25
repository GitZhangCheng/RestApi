package com.ikamobile.common.proxy;

import com.ikamobile.service.result.FlightSearchResult;
import com.ikamobile.common.exception.ServiceNotExistException;
import com.ikamobile.service.FlightService;
import com.ikamobile.service.result.SimpleResp;
import com.ikamobile.service.result.TrainInfoResult;

import java.util.List;

/**
 * Created by zhangcheng on 2016/10/10.
 */
public class Test {
    public static void main(String[] args) throws ServiceNotExistException {
        ServiceProxyFactory factory = ServiceProxyFactory.getInstance(new GlobalParamHandlerImpl());
        FlightService proxy = factory.getProxy(FlightService.class);
        long l = System.currentTimeMillis();
        FlightSearchResult flightSearchResult = proxy.searchFlight(132, "CTU", "PEK", "2016-10-30");
        SimpleResp<List<TrainInfoResult>> listSimpleResp = proxy.searchTrain("BJP", "ZZF", "2016-11-24", "0");
//        System.out.println(listSimpleResp.getData());
        System.out.println(System.currentTimeMillis()-l+"ms");
    }
}
