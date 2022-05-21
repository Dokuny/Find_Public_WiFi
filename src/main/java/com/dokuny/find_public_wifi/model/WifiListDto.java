package com.dokuny.find_public_wifi.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Getter
@Builder
public class WifiListDto {
    private double distance;
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

    public static ArrayList<WifiListDto> ofAll(ResultSet rs) throws SQLException {
        ArrayList<WifiListDto> list = new ArrayList<>();
        while (rs.next()) {
            WifiListDto build = WifiListDto.builder()
                    .distance((int)(Math.acos(rs.getDouble("distance"))*6371*10000)/10000.0)
                    .managementNum(rs.getString("management_num"))
                    .gu(rs.getString("gu"))
                    .name(rs.getString("name"))
                    .roadAddr(rs.getString("road_addr"))
                    .detailAddr(rs.getString("detail_addr"))
                    .installType(rs.getString("install_type"))
                    .installFloor(rs.getString("install_floor"))
                    .installAgency(rs.getString("install_agency"))
                    .serviceType(rs.getString("service_type"))
                    .networkType(rs.getString("network_type"))
                    .installYear(rs.getInt("install_year"))
                    .inOut(rs.getString("in_out"))
                    .env(rs.getString("env"))
                    .lat(rs.getDouble("lat"))
                    .lng(rs.getDouble("lng"))
                    .workedTime(rs.getString("worked_time"))
                    .build();

            list.add(build);
        }
        return list;
    }


}
