package com.dokuny.find_public_wifi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Wifi {
    private String id;
    private String managementNum;
    private String gu;
    private String name;
    private String roadAddr;
    private String detailAddr;
    private String installType;
    private String installFloor;
    private String installAgency;
    private String serviceType;
    private String networkType;
    private int installYear;
    private String inOut;
    private String env;
    private double lat;
    private double lng;
    private String workedTime;
    private double sin_lat;
    private double sin_lnt;
    private double cos_lat;
    private double cos_lnt;

}
