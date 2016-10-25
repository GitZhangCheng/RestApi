package com.ikamobile.service.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangcheng on 2016/10/10.
 */
@Data
public class FlightSearchResult {

    private int code;
    private String message;
    private FlightResult data;

    @Data
    public static class FlightResult {
        private String flightsId;
        private List<Flights> flights;
    }
    @Data
    public static class Flights {
        private String code;
        private AirlineCompany airlineCompany;
        private AirplaneType airplaneType;
        private Double cabinYPrice;
        private CabinPrice lowestAdultCabinPrice;
        private int stopNum;
        private Airport depAirport;
        private String arrDateTime;
        private String flyTime;
        private String lowestAdultPriceCabinId;
        private Airport arrAirport;
        private String arrTerminal;
        private String depTerminal;
        private String id;
        private String lowestAdultPriceCabinCode;
        private List<Cabins> cabins;
        private Date depDateTime;
    }

    @Data
    public static class AirlineCompany {
        private String code;
        private String serviceTel;
        private long createTime;
        private String name;
        private long updateTime;
        private int id;
        private String shortName;
    }
    @Data
    public static class AirplaneType {
        private String code;
        private long createTime;
        private String name;
        private String description;
        private long updateTime;
        private int id;
    }
    @Data
    public static class Airport {
        private String code;
        private double latitude;
        private String fullName;
        private String timeZone;
        private long updateTime;
        private int cityId;
        private String contactTel;
        private String miniName;
        private String icaoCode;
        private long createTime;
        private String shortNameSpell;
        private int id;
        private String fax;
        private String shortName;
        private int belongCountryId;
        private String email;
        private double longitude;
    }
    @Data
    public static class Cabins {
        private String changeRule;
        private String cabinId;
        private String code;
        private String flightId;
        private CabinPrice adultCabinPrice;
        private CabinPrice childCabinPrice;
        private String cabinCode;
        private CabinPrice babyCabinPrice;
        private String endorsementRule;
        private String refundRule;
        private String name;
        private List<CabinPrice> prices;
        private int avaiTicketNum;
        private boolean isTriple;
    }

    @Data
    public static class CabinPrice {
        private String cabinId;
        private double ticketPrice;
        private double totalPrice;
        private double ticketCostPrice;
        private String cabinCode;
        private double rebateToCustomer;
        private double ticketParPrice;
        private double airportConstructionFee;
        private double rebateToTmc;
        private double commissionChargeFromVendor;
        private double fuelSurcharge;
        private double ticketParPriceDiscount;
        private double refundPercentToCustomer;
        private double refund;
    }
}
