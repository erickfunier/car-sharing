package com.erickfunier.carsharing.model;

public class Customer {
    private int id;
    private String name;
    private int rentedCarId;

    public Customer(int id, String name, int rented_car_id) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rented_car_id;
    }

    public Customer(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
