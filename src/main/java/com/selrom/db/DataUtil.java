package com.selrom.db;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class DataUtil {

    private Connection conn = null;

    public DataUtil() {
        try {
            setup();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void setup() throws ClassNotFoundException {
        try {
            conn = Database.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Exception Setup: " + ex + "\n");
        }
    }

    public void close() throws SQLException {
        conn.close();
    }

    public void connectionCheck() throws SQLException, ClassNotFoundException {
        if (conn == null || conn.isClosed()) {
            conn.close();
            setup();
        }
    }

    public ResultSet doQuery(String query) throws SQLException, ClassNotFoundException {
        connectionCheck();
        PreparedStatement t;
        try {
            t = conn.prepareStatement(query);
            ResultSet set = t.executeQuery();
            return set;

        } catch (SQLException ex) {
            System.out.println("Do Query: " + ex + "\n");
        }
        return null;
    }

    public int doManipulation(String query) throws SQLException, ClassNotFoundException {
        connectionCheck();
        PreparedStatement t;
        try {
            conn.setAutoCommit(false);
            t = conn.prepareStatement(query);
            int set = t.executeUpdate();
            conn.commit();
            return set;
        } catch (SQLException ex) {
            System.out.println("Do Manipulation: " + ex + "\n");
            conn.rollback();
        }
        return -1;
    }

    public int doManipulation_Batch(ArrayList query) throws SQLException, ClassNotFoundException {
        try {
            connectionCheck();
            Statement s = conn.createStatement();
            conn.setAutoCommit(false);
            for (int i = 0; i < query.size(); i++) {
                s.addBatch(query.get(i).toString());
            }//array size
            int a[] = s.executeBatch();
            conn.commit();
            int set = a.length;
            return set;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Manipulation Batch: " + ex + "\n");
            conn.rollback();
        }

        return -1;
    }

    public boolean isValueExists(String table, String column, String value) {
        String query = "SELECT 1 FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        try {
            connectionCheck();
            return conn;
        } catch (Exception e) {
            System.out.println("Get Connection: " + e + "\n");
        }
        return null;
    }
}
