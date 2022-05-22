package com.dokuny.find_public_wifi.util;

import java.sql.*;
import java.util.Properties;

public class ConnectionManagerForSQLite implements ConnectionManager{

    String url;
    String driver;

    public ConnectionManagerForSQLite() {
        Properties prop = PropertyManager.getProp("config.properties");
        url = prop.getProperty("db_url");
        driver = prop.getProperty("db_driver");
    }

    @Override
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    @Override
    public void closeConnection(Connection conn, Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
