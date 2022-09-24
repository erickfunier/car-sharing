package com.erickfunier.carsharing.port;

import com.erickfunier.carsharing.dao.CarDao;
import com.erickfunier.carsharing.dao.CompanyDao;
import com.erickfunier.carsharing.dao.CustomerDao;
import com.erickfunier.carsharing.model.Car;
import com.erickfunier.carsharing.model.Company;
import com.erickfunier.carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class Cli implements Port{

    Scanner scanner;
    CompanyDao companyDao;
    CarDao carDao;
    CustomerDao customerDao;

    public Cli() {
        scanner = new Scanner(System.in);
        companyDao = new CompanyDao();
        carDao = new CarDao();
        customerDao = new CustomerDao();

        showMainMenu();
    }

    private void showCarListByCompanyIdMenu(int companyId) {
        boolean onMenu = true;

        while (onMenu) {
            List<Car> cars = carDao.findByCompanyId(companyId);
            if (cars.size() > 0) {
                int index = 1;
                System.out.print("\nCar list:\n");
                for (Car car : cars) {
                    System.out.println(index + ". " + car.getName());
                    index++;
                }

                onMenu = false;

            } else {
                System.out.println("\nThe car list is empty!");
                onMenu = false;
            }
        }
    }

    private void showRentCarListByCompanyIdMenu(int companyId, int customerId) {
        boolean onMenu = true;

        while (onMenu) {
            List<Car> cars = carDao.findByCompanyId(companyId);
            if (cars.size() > 0) {
                int index = 1;
                System.out.print("\nCar list:\n");
                for (Car car : cars) {
                    System.out.println(index + ". " + car.getName());
                    index++;
                }

                System.out.println("0. Back");
                int option = scanner.nextInt();
                if (option != 0) {
                    Customer customer = customerDao.findById(customerId);
                    customer.setRentedCarId(cars.get(option - 1).getId());
                    customerDao.rentACar(customer);
                    System.out.println("\nYou rented '" + cars.get(option - 1).getName() + "'");
                }
                onMenu = false;

            } else {
                System.out.println("\nThe car list is empty!");
                onMenu = false;
            }
        }
    }

    private void showCompanyMenu(int companyId) {
        boolean onMenu = true;
        Company company = companyDao.findById(companyId);
        System.out.print("\n'" + company.getName() + "' company:");
        while (onMenu) {

            System.out.print(
                    """
                    
                    1. Car list
                    2. Create a car
                    0. Back
                    """
            );

            switch (scanner.nextInt()) {
                case 1 -> {
                    showCarListByCompanyIdMenu(companyId);
                }
                case 2 -> {
                    System.out.println("\nEnter the car name: ");
                    scanner.nextLine();
                    Car car = new Car(scanner.nextLine(), companyId);
                    carDao.insert(car);
                    System.out.println("The car was created!");
                }
                case 0 -> onMenu = false;
            }
        }
    }

    private void showCompanyListMenu() {
        boolean onMenu = true;

        while (onMenu) {
            List<Company> companies = companyDao.findAll();
            if (companies.size() > 0) {
                System.out.println("\nChoose a company:");
                int index = 1;
                for (Company company : companies) {
                    System.out.println(index + ". " + company.getName());
                    index++;
                }
                System.out.println("0. Back");
                int option = scanner.nextInt();
                if (option == 0) {
                    onMenu = false;
                } else {
                    showCompanyMenu(companies.get(option - 1).getId());
                    onMenu = false;
                }

            } else {
                System.out.println("The company list is empty!");
                onMenu = false;
            }
        }
    }

    private void showRentACarMenu(int customerId) {
        boolean onMenu = true;

        while (onMenu) {
            Customer customer = customerDao.findById(customerId);
            Car car = carDao.findById(customer.getRentedCarId());
            if (car == null) {
                List<Company> companies = companyDao.findAll();
                if (companies.size() > 0) {
                    System.out.println("\nChoose a company:");
                    int index = 1;
                    for (Company company : companies) {
                        System.out.println(index + ". " + company.getName());
                        index++;
                    }
                    System.out.println("0. Back");
                    int option = scanner.nextInt();
                    if (option != 0) {
                        showRentCarListByCompanyIdMenu(companies.get(option - 1).getId(), customerId);
                    }
                    onMenu = false;

                } else {
                    System.out.println("\nThe company list is empty!");
                    onMenu = false;
                }
            } else {
                System.out.println("\nYou've already rented a car!");
                onMenu = false;
            }
        }
    }

    private void showCustomerMenu(int customerId) {
        boolean onMenu = true;
        while (onMenu) {
            System.out.print(
                    """
                    
                    1. Rent a car
                    2. Return a rented car
                    3. My rented car
                    0. Back
                    """
            );

            switch (scanner.nextInt()) {
                case 1 -> {
                    showRentACarMenu(customerId);
                }
                case 2 -> {
                    showReturnARentedCarMenu(customerId);
                }
                case 3 -> {
                    showRentedCarList(customerId);
                }
                case 0 -> onMenu = false;
            }
        }
    }

    private void showReturnARentedCarMenu(int customerId) {
        Customer customer = customerDao.findById(customerId);
        Car car = carDao.findById(customer.getRentedCarId());

        if (car != null) {
            customerDao.returnACar(customer);
            System.out.println("\nYou've returned a rented car!");
        } else {
            System.out.println("\nYou didn't rent a car!");
        }
    }

    private void showRentedCarList(int customerId) {
        Customer customer = customerDao.findById(customerId);
        Car car = carDao.findById(customer.getRentedCarId());

        if (car != null) {
            Company company = companyDao.findById(car.getCompanyId());

            System.out.println("\nYour rented car:");
            System.out.println(car.getName());
            System.out.println("Company:");
            System.out.println(company.getName());
        } else {
            System.out.println("\nYou didn't rent a car!");
        }
    }

    private void showCustomerListMenu() {
        boolean onMenu = true;

        while (onMenu) {
            List<Customer> customers = customerDao.findAll();
            if (customers.size() > 0) {
                System.out.println("\nThe customer list:");
                int index = 1;
                for (Customer customer : customers) {
                    System.out.println(index + ". " + customer.getName());
                    index++;
                }
                System.out.println("0. Back");
                int option = scanner.nextInt();
                if (option == 0) {
                    System.out.print("\n");
                    onMenu = false;
                } else {
                    showCustomerMenu(customers.get(option - 1).getId());
                    System.out.print("\n");
                    onMenu = false;
                }

            } else {
                System.out.println("\nThe customer list is empty!\n");
                onMenu = false;
            }
        }
    }

    private void showManagerMenu() {
        boolean onMenu = true;

        while (onMenu) {
            System.out.print(
                    """
                    
                    1. Company list
                    2. Create a company
                    0. Back
                    """
            );

            switch (scanner.nextInt()) {
                case 1 -> showCompanyListMenu();
                case 2 -> {
                    System.out.println("\nEnter the company name: ");
                    scanner.nextLine();
                    Company company = new Company(scanner.nextLine());
                    companyDao.insert(company);
                    System.out.println("The company was created!");
                }
                case 0 -> onMenu = false;
            }
        }
    }

    private void showMainMenu() {
        boolean onMenu = true;

        while (onMenu) {
            System.out.print(
                    """
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit
                    """
            );

            switch (scanner.nextInt()) {
                case 1 -> {
                    showManagerMenu();
                    System.out.print("\n");
                }
                case 2 -> {
                    showCustomerListMenu();
                }
                case 3 -> {
                    System.out.println("\nEnter the customer name:");
                    scanner.nextLine();
                    Customer customer = new Customer(scanner.nextLine());
                    customerDao.insert(customer);
                    System.out.println("The customer was added!\n");
                }
                case 0 -> onMenu = false;
            }
        }
    }
}
