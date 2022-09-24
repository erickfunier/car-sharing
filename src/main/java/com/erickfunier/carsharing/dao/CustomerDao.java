package com.erickfunier.carsharing.dao;

import com.erickfunier.carsharing.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.erickfunier.carsharing.repository.InMemoryDB.DB_URL;

public class CustomerDao {

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name")),
                        resultSet.getInt(resultSet.findColumn("rented_car_id"))
                );
                customers.add(customer);

            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }

    public int insert(Customer customer) {
        String sql = "INSERT INTO customer(name) VALUES ('" + customer.getName() + "')";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            String sqlSelect = "SELECT id FROM customer WHERE name = '" + customer.getName() + "'";

            ResultSet resultSet = stmt.executeQuery(sqlSelect);

            int id = -1;
            while(resultSet.next()) {
                id = resultSet.getInt(resultSet.findColumn("id"));
            }
            stmt.close();
            connection.close();

            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer findById(int id) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE id = " + id + "";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name")),
                        resultSet.getInt(resultSet.findColumn("rented_car_id"))
                );
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public int rentACar(Customer customer) {
        String sql = "UPDATE customer SET rented_car_id = " + customer.getRentedCarId() + " WHERE id = " + customer.getId();
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            String sqlSelect = "SELECT rented_car_id FROM customer WHERE id = '" + customer.getId() + "'";

            ResultSet resultSet = stmt.executeQuery(sqlSelect);

            int rented_car_id = -1;
            while(resultSet.next()) {
                rented_car_id = resultSet.getInt(resultSet.findColumn("rented_car_id"));
            }
            stmt.close();
            connection.close();

            return rented_car_id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int returnACar(Customer customer) {
        String sql = "UPDATE customer SET rented_car_id = NULL";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            String sqlSelect = "SELECT rented_car_id FROM customer WHERE id = '" + customer.getId() + "'";

            ResultSet resultSet = stmt.executeQuery(sqlSelect);

            int rented_car_id = -1;
            while(resultSet.next()) {
                rented_car_id = resultSet.getInt(resultSet.findColumn("rented_car_id"));
            }
            stmt.close();
            connection.close();

            return rented_car_id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
