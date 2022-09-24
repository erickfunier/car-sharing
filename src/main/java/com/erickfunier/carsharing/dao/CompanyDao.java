package com.erickfunier.carsharing.dao;

import com.erickfunier.carsharing.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.erickfunier.carsharing.repository.InMemoryDB.DB_URL;

public class CompanyDao {
    private List<Company> companies;

    public CompanyDao() {
        companies = new ArrayList<>();
    }

    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM company";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name"))
                );
                companies.add(company);

            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return companies;
    }

    public Company findById(int companyId) {
        Company company = null;
        String sql = "SELECT * FROM company WHERE id = " + companyId + "";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                company = new Company(
                        resultSet.getInt(resultSet.findColumn("id")),
                        resultSet.getString(resultSet.findColumn("name"))
                );
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return company;
    }

    public int insert(Company company) {
        String sql = "INSERT INTO company(name) VALUES ('" + company.getName() + "')";
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            String sqlSelect = "SELECT id FROM company WHERE name = '" + company.getName() + "'";

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


}
