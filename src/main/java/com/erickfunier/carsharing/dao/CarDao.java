package com.erickfunier.carsharing.dao;

import com.erickfunier.carsharing.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.erickfunier.carsharing.repository.InMemoryDB.DB_URL;

public class CarDao {
    private List<Car> cars;

    public CarDao() {
        cars = new ArrayList<>();
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                Car car = new Car(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name")),
                        resultSet.getInt(resultSet.findColumn("company_id"))
                );
                cars.add(car);

            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }

    public int insert(Car car) {
        String sql = "INSERT INTO car(name, company_id) VALUES ('" + car.getName() + "', " + car.getCompanyId() + ")";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            String sqlSelect = "SELECT id FROM car WHERE name = '" + car.getName() + "'";

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

    public Car findById(int carId) {
        Car car = null;
        String sql = "SELECT * FROM car WHERE id = " + carId + "";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                car = new Car(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name")),
                        resultSet.getInt(resultSet.findColumn("company_id"))
                );
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return car;
    }

    public List<Car> findByCompanyId(int companyId) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT car.id, car.name, car.company_id " +
                "FROM car LEFT JOIN customer " +
                "ON car.id = customer.rented_car_id " +
                "WHERE car.company_id = " + companyId + " AND customer.name IS NULL";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                Car car = new Car(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name")),
                        resultSet.getInt(resultSet.findColumn("company_id"))
                );
                cars.add(car);
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }
}
