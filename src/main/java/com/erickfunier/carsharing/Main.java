package com.erickfunier.carsharing;

import com.erickfunier.carsharing.port.Cli;
import com.erickfunier.carsharing.port.Port;
import com.erickfunier.carsharing.repository.InMemoryDB;

public class Main {

    public static void main(String[] args) {

        InMemoryDB.createDatabase();

        Port port = new Cli();

    }
}