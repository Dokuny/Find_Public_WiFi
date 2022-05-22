package com.dokuny.find_public_wifi.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Getter
@Setter
@Builder
public class History {
    private int id;
    private double lat;
    private double lng;
    private String checkDate;

    public static ArrayList<History> ofAll(ResultSet rs) throws SQLException {
        ArrayList<History> list = new ArrayList<>();

        while (rs.next()) {
            History build = History.builder()
                    .id(rs.getInt("id"))
                    .lat(rs.getDouble("lat"))
                    .lng(rs.getDouble("lng"))
                    .checkDate(rs.getString("check_date"))
                    .build();
            list.add(build);
        }

        return list;
    }
}
