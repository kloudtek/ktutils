/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various JDBC related utility functions
 */
public class JDBCUtils {
    private static final Logger logger = Logger.getLogger(JDBCUtils.class.getName());
    private static final String POSTGRESDRIVER = "org.postgresql.Driver";

    public static Connection createConnection(String url, String username, String password) throws SQLException {
        String driver = null;
        if (url.startsWith("jdbc:postgresql")) {
            driver = POSTGRESDRIVER;
        }
        if (driver != null) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Missing driving: " + driver);
            }
        }
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeQuietly(ResultSet resultSet, Statement st) {
        closeQuietly(resultSet);
        closeQuietly(st);
    }

    public static void closeQuietly(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error while closing resultset: " + e.getMessage(), e);
        }
    }

    public static void closeQuietly(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error while closing statement: " + e.getMessage(), e);
        }
    }
}
