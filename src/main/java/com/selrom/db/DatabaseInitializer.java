package com.selrom.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void initialize(Connection conn) {
        try {
            System.out.println("Checking database schema...");
            String schemaSql = loadSchema();
            if (schemaSql == null) {
                System.err.println("Could not load schema.sql");
                return;
            }

            Map<String, TableDefinition> schemaTables = parseSchema(schemaSql);
            System.out.println("Parsed " + schemaTables.size() + " tables from schema.sql.");

            for (Map.Entry<String, TableDefinition> entry : schemaTables.entrySet()) {
                String tableName = entry.getKey();
                TableDefinition tableDef = entry.getValue();

                if (!tableExists(conn, tableName)) {
                    System.out.println("Table '" + tableName + "' does not exist. Creating...");
                    createTable(conn, tableDef.fullSql);
                } else {
                    // Check columns
                    checkAndSyncColumns(conn, tableName, tableDef);
                }
            }

            // Execute any other statements (like INSERT)
            List<String> otherStatements = parseOtherStatements(schemaSql);
            try (Statement stmt = conn.createStatement()) {
                for (String sql : otherStatements) {
                    try {
                        stmt.execute(sql);
                    } catch (SQLException e) {
                        // Ignore errors like duplicate entry
                    }
                }
            }

            // Fix peracc table schema for existing installations
            try (Statement stmt = conn.createStatement()) {
                // Check if sno is auto_increment. If not, modify it.
                // This is a "hotfix" migration.
                try {
                    stmt.execute("ALTER TABLE peracc MODIFY sno INT AUTO_INCREMENT");
                    System.out.println("Migrated peracc table to AUTO_INCREMENT.");
                } catch (SQLException e) {
                    // Ignore if already auto_increment or other benign errors
                    System.out.println("Skipping peracc migration: " + e.getMessage());
                }
            }

            // Ensure super admin user exists (idempotent)
//            ensureAdminUser(conn);

            System.out.println("Database schema verified/updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String loadSchema() {
        try (InputStream in = DatabaseInitializer.class.getResourceAsStream("/db/schema.sql")) {
            if (in == null) return null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        String catalog = conn.getCatalog();

        // Special check for 'users' table in H2 to avoid conflict with system table
        if (tableName.equalsIgnoreCase("users")) {
             DatabaseMetaData meta = conn.getMetaData();
             // Check if it has 'utype' column which implies it is OUR table
             if (!columnExists(meta, tableName, "utype")) {
                 System.out.println("DEBUG: Found 'users' table but missing 'utype' column. Assuming system table and returning false.");
                 return false;
             }
        }

        // First try exact match
        try (ResultSet rs = conn.getMetaData().getTables(catalog, null, tableName, new String[]{"TABLE"})) {
            if (rs.next()) {
                System.out.println("DEBUG: Found table " + rs.getString("TABLE_NAME") + " type=" + rs.getString("TABLE_TYPE"));
                return true;
            }
        }

        // Then try case variations if not found (for robustness on case-sensitive OS with case-insensitive logic)
        // Note: on Linux MySQL, if lower_case_table_names=0, 'User' and 'user' are different tables.
        // We really want the table with the exact name from schema.sql.
        // However, existing DB might have UpperCase tables.

        try (ResultSet rs = conn.getMetaData().getTables(catalog, null, tableName.toUpperCase(), new String[]{"TABLE"})) {
            if (rs.next()) {
                System.out.println("DEBUG: Found table (upper) " + rs.getString("TABLE_NAME") + " type=" + rs.getString("TABLE_TYPE"));
                // If we found the UPPERCASE version, we consider it existing to avoid duplicate creation errors
                // or parallel existence confusion.
                return true;
            }
        }
        try (ResultSet rs = conn.getMetaData().getTables(catalog, null, tableName.toLowerCase(), new String[]{"TABLE"})) {
            if (rs.next()) {
                System.out.println("DEBUG: Found table (lower) " + rs.getString("TABLE_NAME") + " type=" + rs.getString("TABLE_TYPE"));
                return true;
            }
        }
        return false;
    }

    private static void createTable(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Failed to execute creation SQL: " + sql.substring(0, Math.min(50, sql.length())) + "...");
            throw e;
        }
    }

    private static void checkAndSyncColumns(Connection conn, String tableName, TableDefinition tableDef) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();

        for (ColumnDefinition colDef : tableDef.columns) {
            if (!columnExists(meta, tableName, colDef.name)) {
                System.out.println("Column " + colDef.name + " missing in table " + tableName + ". Adding...");
                String alterSql = "ALTER TABLE `" + tableName + "` ADD COLUMN " + colDef.fullDefinition;
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(alterSql);
                } catch (SQLException e) {
                    System.err.println("Failed to add column " + colDef.name + ": " + e.getMessage());
                }
            }
        }
    }

    private static boolean columnExists(DatabaseMetaData meta, String tableName, String columnName) throws SQLException {
        String catalog = meta.getConnection().getCatalog();
        try (ResultSet rs = meta.getColumns(catalog, null, tableName, columnName)) {
            if (rs.next()) return true;
        }
        // Try variants
        try (ResultSet rs = meta.getColumns(catalog, null, tableName.toUpperCase(), columnName.toUpperCase())) {
            if (rs.next()) return true;
        }
        try (ResultSet rs = meta.getColumns(catalog, null, tableName.toLowerCase(), columnName.toLowerCase())) {
            if (rs.next()) return true;
        }
        return false;
    }

    private static void ensureAdminUser(Connection conn) {
        // First check if 'users' table exists to avoid errors if creation failed
        try {
             if (!tableExists(conn, "users")) {
                 System.err.println("Cannot ensure admin user: 'users' table missing.");
                 return;
             }
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }

        String checkSql = "SELECT `user` FROM users WHERE `user` = 'admin'";
        String insertSql = "INSERT INTO users (utype, `user`, pass, last) VALUES ('Super Admin', 'admin', 'admin', 'admin')";

        try (Statement stmt = conn.createStatement()) {
             try (ResultSet rs = stmt.executeQuery(checkSql)) {
                if (!rs.next()) {
                    System.out.println("Creating admin user...");
                    stmt.executeUpdate(insertSql);
                }
             }
        } catch (SQLException e) {
            System.err.println("Failed to ensure admin user: " + e.getMessage());
        }
    }

    // --- Simple SQL Parser ---

    private static class TableDefinition {
        String name;
        String fullSql;
        List<ColumnDefinition> columns = new ArrayList<>();
    }

    private static class ColumnDefinition {
        String name;
        String fullDefinition;
    }

    private static Map<String, TableDefinition> parseSchema(String schemaSql) {
        Map<String, TableDefinition> tables = new HashMap<>();

        // Remove comments
        schemaSql = schemaSql.replaceAll("--.*", "");

        // Split by semicolon
        String[] statements = schemaSql.split(";");

        Pattern createTablePattern = Pattern.compile("CREATE\\s+TABLE\\s+(?:IF\\s+NOT\\s+EXISTS\\s+)?`?(\\w+)`?\\s*\\((.*)\\)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        for (String stmt : statements) {
            stmt = stmt.trim();
            if (stmt.isEmpty()) continue;

            Matcher m = createTablePattern.matcher(stmt);
            if (m.find()) {
                TableDefinition table = new TableDefinition();
                table.name = m.group(1);
                table.fullSql = stmt;

                String body = m.group(2);
                table.columns = parseColumns(body);

                tables.put(table.name, table);
            }
        }
        return tables;
    }

    private static List<String> parseOtherStatements(String schemaSql) {
        List<String> others = new ArrayList<>();
         String[] statements = schemaSql.split(";");
         for (String stmt : statements) {
            stmt = stmt.trim();
            if (stmt.isEmpty()) continue;
            if (!stmt.toUpperCase().startsWith("CREATE TABLE")) {
                others.add(stmt);
            }
        }
        return others;
    }

    private static List<ColumnDefinition> parseColumns(String body) {
        List<ColumnDefinition> columns = new ArrayList<>();
        List<String> parts = splitByCommaIgnoringParens(body);

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            // Skip keys/constraints
            if (part.toUpperCase().startsWith("PRIMARY KEY") ||
                part.toUpperCase().startsWith("KEY") ||
                part.toUpperCase().startsWith("UNIQUE KEY") ||
                part.toUpperCase().startsWith("CONSTRAINT")) {
                continue;
            }

            // Extract column name
            String colName = null;
            if (part.startsWith("`")) {
                int endQuote = part.indexOf("`", 1);
                if (endQuote > 1) {
                    colName = part.substring(1, endQuote);
                }
            } else {
                int space = part.indexOf(" ");
                if (space > 0) {
                    colName = part.substring(0, space);
                }
            }

            if (colName != null) {
                ColumnDefinition col = new ColumnDefinition();
                col.name = colName;
                col.fullDefinition = part;
                columns.add(col);
            }
        }
        return columns;
    }

    private static List<String> splitByCommaIgnoringParens(String text) {
        List<String> tokens = new ArrayList<>();
        int parens = 0;
        StringBuilder current = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c == '(') parens++;
            if (c == ')') parens--;

            if (c == ',' && parens == 0) {
                tokens.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString());
        return tokens;
    }
}
