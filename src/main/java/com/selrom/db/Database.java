package com.selrom.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Modified to support dynamic configuration check.
 */
public class Database {

    private static Database dbIsntance;
    private static Connection con;

    // MySQL Database Defaults
    private static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/Swayam_main?createDatabaseIfNotExist=true";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = "admin";
    private static final String CONFIG_FILE = "db_main.properties";

    private Database() {
        // private constructor //
    }

    public static Database getInstance() {
        if (dbIsntance == null) {
            dbIsntance = new Database();
        }
        return dbIsntance;
    }

    /**
     * Checks if the configuration file exists.
     * 
     * @return true if db_main.properties exists, false otherwise.
     */
    public boolean isConfigured() {
        File f = new File(CONFIG_FILE);
        return f.exists();
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con = null;
    }

    public Properties loadConfig() {
        Properties props = new Properties();
        File f = new File(CONFIG_FILE);
        if (f.exists()) {
            try (FileInputStream in = new FileInputStream(f)) {
                props.load(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Return empty if not exists so we know to use defaults or prompt user
            // However, strictly speaking, if file doesn't exist, we usually want to return
            // defaults
            // solely for fallback.
        }

        // If values are missing, use defaults (Fail-safe)
        if (!props.containsKey("db.url"))
            props.setProperty("db.url", DEFAULT_DB_URL);
        if (!props.containsKey("db.username"))
            props.setProperty("db.username", DEFAULT_USER);
        if (!props.containsKey("db.password"))
            props.setProperty("db.password", DEFAULT_PASS);

        return props;
    }

    public void saveConfig(String url, String username, String password) {
        Properties props = new Properties();
        props.setProperty("db.url", url);
        props.setProperty("db.username", username);
        props.setProperty("db.password", password);

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Main Database Configuration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (con == null || con.isClosed()) {
            try {
                Properties props = loadConfig();

                // Load MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(
                        props.getProperty("db.url"),
                        props.getProperty("db.username"),
                        props.getProperty("db.password"));

                // Initialize DB if needed
                DatabaseInitializer.initialize(con);

            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Database: " + ex + "\n");
                throw ex; // Re-throw to let caller know
            }
        }

        return con;
    }
}
