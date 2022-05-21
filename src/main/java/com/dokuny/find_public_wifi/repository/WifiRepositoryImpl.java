package com.dokuny.find_public_wifi.repository;

import com.dokuny.find_public_wifi.model.WifiApiDto;
import com.dokuny.find_public_wifi.model.WifiListDto;
import com.dokuny.find_public_wifi.service.ApiService;

import java.sql.*;
import java.util.ArrayList;

public class WifiRepositoryImpl implements WifiRepository {
    @Override
    public int updateAll() {
        ArrayList<WifiApiDto> wifiDataAll = ApiService.getWifiDataAll();
        int size = wifiDataAll.size();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:\\study\\Project\\Find_Public_Wifi\\find_public_wifi");


            pstmt = conn.prepareStatement("PRAGMA cache_size=10000");
            pstmt.execute();

            String sql = "DELETE FROM wifi;";
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();

            pstmt = conn.prepareStatement("BEGIN TRANSACTION;");
            pstmt.execute();

            sql = "INSERT INTO wifi(management_num,gu,name,road_addr,detail_addr,install_type,install_floor,install_agency,service_type,network_type,install_year,in_out,env,lat,lng,worked_time,cos_lat,cos_lng,sin_lat,sin_lng) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(sql);

            for (WifiApiDto dto : wifiDataAll) {
                pstmt.setString(1, dto.getX_SWIFI_MGR_NO());
                pstmt.setString(2, dto.getX_SWIFI_WRDOFC());
                pstmt.setString(3, dto.getX_SWIFI_MAIN_NM());
                pstmt.setString(4, dto.getX_SWIFI_ADRES1());
                pstmt.setString(5, dto.getX_SWIFI_ADRES2());
                pstmt.setString(6, dto.getX_SWIFI_INSTL_TY());
                pstmt.setString(7, dto.getX_SWIFI_INSTL_FLOOR());
                pstmt.setString(8, dto.getX_SWIFI_INSTL_MBY());
                pstmt.setString(9, dto.getX_SWIFI_SVC_SE());
                pstmt.setString(10, dto.getX_SWIFI_CMCWR());
                pstmt.setString(11, dto.getX_SWIFI_CNSTC_YEAR());
                pstmt.setString(12, dto.getX_SWIFI_INOUT_DOOR());
                pstmt.setString(13, dto.getX_SWIFI_REMARS3());
                pstmt.setDouble(14, dto.getLNT());
                pstmt.setDouble(15, dto.getLAT());
                pstmt.setString(16, dto.getWORK_DTTM());
                pstmt.setDouble(17, Math.cos(Math.toRadians(dto.getLNT())));
                pstmt.setDouble(18, Math.cos(Math.toRadians(dto.getLAT())));
                pstmt.setDouble(19, Math.sin(Math.toRadians(dto.getLNT())));
                pstmt.setDouble(20, Math.sin(Math.toRadians(dto.getLAT())));
                pstmt.addBatch();
                pstmt.clearParameters();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();

            pstmt = conn.prepareStatement("END TRANSACTION;");
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return size;
    }


    @Override
    public ArrayList<WifiListDto> findAll(double lat, double lng) {
        ArrayList<WifiListDto> list = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT " + buildDistanceQuery(lat, lng)
                + " AS distance" + ","
                + " w.*"
                + " FROM wifi w"
                + " WHERE lat NOT In(0) AND lng NOT IN(0)"
                + " ORDER BY distance DESC"
                + " LIMIT 20;";
        System.out.println(sql);

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:\\study\\Project\\Find_Public_Wifi\\find_public_wifi");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            list = WifiListDto.ofAll(rs);


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private String buildDistanceQuery(double lat, double lng) {

        final double sinLat = Math.sin(Math.toRadians(lat));
        final double cosLat = Math.cos(Math.toRadians(lat));
        final double sinLng = Math.sin(Math.toRadians(lng));
        final double cosLng = Math.cos(Math.toRadians(lng));


        return "(" + cosLat + "*cos_lat"
                + "*(cos_lng*" + cosLng
                + "+sin_lng*" + sinLng
                + ")+" + sinLat + "*sin_lat"
                + ")";
    }

}
