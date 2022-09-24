package com.erickfunier.carsharing.repository;

import java.sql.*;

public class InMemoryDB {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    public static void createDatabase() {
        Connection connection = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            connection = DriverManager.getConnection(DB_URL);

            //STEP 3: Execute a query (create Company table)
            stmt = connection.createStatement();
            connection.setAutoCommit(true);

            String sql = "CREATE TABLE IF NOT EXISTS company " +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) UNIQUE NOT NULL)";
            stmt.executeUpdate(sql);

            //STEP 3: Execute a query (create Car table)
            sql = "CREATE TABLE IF NOT EXISTS car (" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) UNIQUE NOT NULL, " +
                    "company_id INTEGER NOT NULL, " +
                    "CONSTRAINT fk_company FOREIGN KEY (company_id) " +
                    "REFERENCES company(id) " +
                    "ON UPDATE CASCADE)";
            stmt.executeUpdate(sql);

            //STEP 3: Execute a query (create Customer table)
            sql = "CREATE TABLE IF NOT EXISTS customer (" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) UNIQUE NOT NULL, " +
                    "rented_car_id INTEGER, " +
                    "CONSTRAINT fk_rented_car FOREIGN KEY (rented_car_id) " +
                    "REFERENCES car(id) " +
                    "ON UPDATE CASCADE)";
            stmt.executeUpdate(sql);

            // STEP 4: Clean-up environment
            stmt.close();
            connection.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            }
            try {
                if(connection != null) connection.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
