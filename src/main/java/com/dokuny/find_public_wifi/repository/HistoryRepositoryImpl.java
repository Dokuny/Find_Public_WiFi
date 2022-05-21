package com.dokuny.find_public_wifi.repository;

import com.dokuny.find_public_wifi.model.History;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryRepositoryImpl implements HistoryRepository {
    @Override
    public void save(double lat,double lng) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:\\study\\Project\\Find_Public_Wifi\\find_public_wifi");

            String sql = "INSERT INTO history(lat,lng,check_date) VALUES(?,?,?);";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1,lat);
            pstmt.setDouble(2,lng);
            pstmt.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").format(LocalDateTime.now()));
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
    }

    @Override
    public ArrayList<History> findAll() {
        ArrayList<History> list = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:\\study\\Project\\Find_Public_Wifi\\find_public_wifi");

            String sql = "SELECT * FROM history;";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            list = History.ofAll(rs);
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

    @Override
    public void delete(int id) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:\\study\\Project\\Find_Public_Wifi\\find_public_wifi");

            String sql = "DELETE FROM history WHERE id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
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
    }
}
