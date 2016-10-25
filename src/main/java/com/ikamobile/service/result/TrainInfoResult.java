package com.ikamobile.service.result;

import lombok.Data;

import java.util.List;

/**
 * Created by guest on 16/3/22.
 * 火车票余票信息
 */
@Data
public class TrainInfoResult {
    private String number;
    private String trainId;
    private String duration;
    private TrainFromStationInfo from;
    private TrainToStationInfo to;
    private Boolean bookable;
    private List<Seat> seats;

    @Data
    class TrainFromStationInfo {
        private String code;
        private String name;
        private String time;
        private Boolean first;
    }

    @Data
    public class TrainToStationInfo {
        private String code;
        private String name;
        private String time;
        private Boolean last;
    }

    @Data
    class Seat{
        private String code;//作为code
        private String name;//座位名称
        private Integer qty;//余票量
        private Double price;//坐席价格
    }





}
