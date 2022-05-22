package com.dokuny.find_public_wifi.repository;

import com.dokuny.find_public_wifi.model.entity.History;
import com.dokuny.find_public_wifi.util.ConnectionManager;
import com.dokuny.find_public_wifi.util.ConnectionManagerForSQLite;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryRepositoryImpl implements HistoryRepository {

    private final ConnectionManager cm;

    public HistoryRepositoryImpl() {
        cm = new ConnectionManagerForSQLite();
    }

    @Override
    public void save(double lat,double lng) {

        Connection conn = cm.getConnection();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO history(lat,lng,check_date) VALUES(?,?,?);";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1,lat);
            stmt.setDouble(2,lng);
            stmt.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").format(LocalDateTime.now()));
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            cm.closeConnection(conn,stmt);
        }
    }

    @Override
    public ArrayList<History> findAll() {
        ArrayList<History> list = new ArrayList<>();

        Connection conn = cm.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM history;";


        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            list = History.ofAll(rs);
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            cm.closeConnection(conn,stmt,rs);
        }

        return list;
    }

    @Override
    public void delete(int id) {
        Connection conn = cm.getConnection();
        PreparedStatement stmt = null;

        String sql = "DELETE FROM history WHERE id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
           cm.closeConnection(conn,stmt);
        }
    }
}
