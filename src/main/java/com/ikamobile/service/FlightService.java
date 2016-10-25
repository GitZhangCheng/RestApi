package com.ikamobile.service;

import com.ikamobile.common.annotations.ParamAttr;
import com.ikamobile.common.annotations.RequestInfo;
import com.ikamobile.common.annotations.RestAPIService;
import com.ikamobile.service.result.FlightSearchResult;
import com.ikamobile.service.result.SimpleResp;
import com.ikamobile.service.result.TrainInfoResult;

import java.util.List;

/**
 * Created by zhangcheng on 2016/10/10.
 */
@RestAPIService
public interface FlightService {

    @RequestInfo(url="/flight/searchNationFlights",method= RequestInfo.HttpMethod.GET)
    FlightSearchResult searchFlight(
            @ParamAttr(name="employeeId")int employeeId,
            @ParamAttr(name="arrCityCode")String formCity,
            @ParamAttr(name="depCityCode")String depCity,
            @ParamAttr(name="depDate")String date);

    @RequestInfo(url = "/train/leftTicket/list",method = RequestInfo.HttpMethod.GET)
    SimpleResp<List<TrainInfoResult>> searchTrain(@ParamAttr(name="depStation")String depStation,
                                                  @ParamAttr(name="arrStation")String arrStation,
                                                  @ParamAttr(name="depDate")String depDate,
                                                  @ParamAttr(name="trainType")String trainType);
}
