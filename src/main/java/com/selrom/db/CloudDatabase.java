package com.selrom.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CloudDatabase {
    // Constants for Cloud Database Credentials
    // TODO: Configure these with actual cloud database credentials or use a
    // configuration file
    private static final String CLOUD_DB_URL = "jdbc:mysql://localhost:3306/BBS_admin?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
    private static final String CLOUD_DB_USER = "root";
    private static final String CLOUD_DB_PASS = "root";

    private static Connection con;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (con != null && !con.isClosed()) {
            try {
                if (!con.isValid(2)) { // Check validity with 2 sec timeout
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                con = null;
            }
        }

        if (con == null || con.isClosed()) {
            // Load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CLOUD_DB_URL, CLOUD_DB_USER, CLOUD_DB_PASS);
        }
        return con;
    }
}
