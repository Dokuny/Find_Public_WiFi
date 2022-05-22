package com.dokuny.find_public_wifi.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ConnectionManager {

    Connection getConnection();
    void closeConnection(Connection conn, Statement stmt, ResultSet rs);

    void closeConnection(Connection conn, Statement stmt);
}
