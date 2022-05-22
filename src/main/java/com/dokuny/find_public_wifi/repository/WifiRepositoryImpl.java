package com.dokuny.find_public_wifi.repository;

import com.dokuny.find_public_wifi.model.dto.WifiApiDto;
import com.dokuny.find_public_wifi.model.dto.WifiListDto;
import com.dokuny.find_public_wifi.service.ApiService;
import com.dokuny.find_public_wifi.util.ConnectionManager;
import com.dokuny.find_public_wifi.util.ConnectionManagerForSQLite;

import java.sql.*;
import java.util.ArrayList;

public class WifiRepositoryImpl implements WifiRepository {

    private final ConnectionManager cm;

    public WifiRepositoryImpl() {
        cm = new ConnectionManagerForSQLite();
    }

    @Override
    public int updateAll() {
        ApiService apiService = new ApiService();
        ArrayList<WifiApiDto> wifiDataAll = apiService.getWifiDataAll();

        Connection conn = cm.getConnection();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO wifi(management_num,gu,name,road_addr,detail_addr,install_type,install_floor,install_agency,service_type,network_type,install_year,in_out,env,lat,lng,worked_time,cos_lat,cos_lng,sin_lat,sin_lng) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try {
            stmt = conn.prepareStatement("PRAGMA cache_size=10000");
            stmt.execute();

            stmt = conn.prepareStatement("DELETE FROM wifi;");
            stmt.execute();

            stmt = conn.prepareStatement("BEGIN TRANSACTION;");
            stmt.execute();

            stmt = conn.prepareStatement(sql);

            for (WifiApiDto dto : wifiDataAll) {
                stmt.setString(1, dto.getX_SWIFI_MGR_NO());
                stmt.setString(2, dto.getX_SWIFI_WRDOFC());
                stmt.setString(3, dto.getX_SWIFI_MAIN_NM());
                stmt.setString(4, dto.getX_SWIFI_ADRES1());
                stmt.setString(5, dto.getX_SWIFI_ADRES2());
                stmt.setString(6, dto.getX_SWIFI_INSTL_TY());
                stmt.setString(7, dto.getX_SWIFI_INSTL_FLOOR());
                stmt.setString(8, dto.getX_SWIFI_INSTL_MBY());
                stmt.setString(9, dto.getX_SWIFI_SVC_SE());
                stmt.setString(10, dto.getX_SWIFI_CMCWR());
                stmt.setString(11, dto.getX_SWIFI_CNSTC_YEAR());
                stmt.setString(12, dto.getX_SWIFI_INOUT_DOOR());
                stmt.setString(13, dto.getX_SWIFI_REMARS3());
                stmt.setDouble(14, dto.getLNT());
                stmt.setDouble(15, dto.getLAT());
                stmt.setString(16, dto.getWORK_DTTM());
                stmt.setDouble(17, Math.cos(Math.toRadians(dto.getLNT())));
                stmt.setDouble(18, Math.cos(Math.toRadians(dto.getLAT())));
                stmt.setDouble(19, Math.sin(Math.toRadians(dto.getLNT())));
                stmt.setDouble(20, Math.sin(Math.toRadians(dto.getLAT())));
                stmt.addBatch();
                stmt.clearParameters();
            }
            stmt.executeBatch();
            stmt.clearBatch();

            stmt = conn.prepareStatement("END TRANSACTION;");
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            cm.closeConnection(conn,stmt);
        }
        return wifiDataAll.size();
    }


    @Override
    public ArrayList<WifiListDto> findAll(double lat, double lng) {
        ArrayList<WifiListDto> list = new ArrayList<>();

        Connection conn = cm.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT " + buildDistanceQuery(lat, lng)
                + " AS distance" + ","
                + " w.*"
                + " FROM wifi w"
                + " WHERE lat NOT In(0) AND lng NOT IN(0)"
                + " ORDER BY distance DESC"
                + " LIMIT 20;";

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            list = WifiListDto.ofAll(rs);
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            cm.closeConnection(conn,stmt,rs);
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
