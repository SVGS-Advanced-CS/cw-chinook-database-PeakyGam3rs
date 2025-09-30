package com.svgs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Main {
    private static Connection conn;
    public static void main(String[] args) {
        createDatabase();
        String url = "jdbc:sqlite:./src/main/resources/users.db";

        try {
            conn = DriverManager.getConnection(url);
            Statement state = conn.createStatement();
            //state.executeUpdate("DELETE FROM employees WHERE employeeId>=11 AND FirstName=='Anthony' AND LastName=='Tyler'");
            //state.executeUpdate("INSERT INTO employees(FirstName, LastName, EmployeeId) VALUES('Anthony', 'Tyler', 9)");
            /* 
            Statement state = conn.createStatement();
            String query = "SELECT * FROM employees WHERE EmployeeId==4 ORDER BY LastName ASC";
            ResultSet results = state.executeQuery(query);

            while (results.next()) {
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                System.out.println("First Name: " + firstName + " Last Name: " + lastName);
            }
            */
            
        } catch (SQLException e) {
            System.out.println(e);
        }

        mainMenu();

    }

    public static void createDatabase() {
        String url = "jdbc:sqlite:./src/main/resources/users.db";

        try {
            conn = DriverManager.getConnection(url);
            Statement state = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users(username TEXT PRIMARY KEY, password TEXT NOT NULL, role TEXT);";
            state.execute(sql);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void mainMenu() {
        System.out.println("1. Log in");
        System.out.println("2. Create User");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        try (Scanner input = new Scanner(System.in)) {
            String choice = input.nextLine();
            switch (choice) {
                case "1" -> loginUser();
                case "2" -> createUser();
                case "3" -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
        mainMenu();

    }
    
    public static void loginUser() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Username? ");
            String username = input.nextLine();
            System.out.print("Password? ");
            String password = input.nextLine();

            String query = "SELECT role FROM users WHERE username='" + username + "' AND password='" + password + "'";

            try {
                Statement state = conn.createStatement();
                ResultSet results = state.executeQuery(query);
                if (!results.next()) {
                    System.out.println("Incorrect username or password!");
                    mainMenu();
                    return;
                }
                System.out.println("You have logged in! Your role is " + results.getString("role"));
                if (results.getString("role").equals("admin")) {
                    adminMenu();
                } else {
                    userMenu(username);
                }
            } catch (SQLException e) {
            }
        }
    }

    public static void createUser() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Username? ");
            String username = input.nextLine();
            System.out.println("Password? ");
            String password = input.nextLine();
            System.out.println("Role? ");
            String role = input.nextLine();

            String query = "INSERT into users(username,password,role) VALUES ('" +
            username + "','" + password + "','" + role + "')";
      // System.out.println(query);
            try {
                Statement state = conn.createStatement();
                state.executeUpdate(query);
            } catch (SQLException e) {
                if (e.getErrorCode() == 19) {
                    System.out.println("This user already exists!");
                    createUser();
                } 
                System.out.println(e);
            }
        }
        mainMenu();
    }
    public static void userMenu(String username) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Change your password (y/n)?");
            String response = input.nextLine();
            if (response.equals("y")) {
                System.out.println("New password? ");
                String newPassword = input.nextLine();
                String query = "UPDATE users SET password ='" + 
                newPassword + 
                "' WHERE username='" + username + "'";
                try {
                    Statement state = conn.createStatement();
                    state.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else {
                mainMenu();
            }
        }
    }

    public static void adminMenu() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Which user do you want to delete? ");
            String response = input.nextLine();
            String query = "DELETE FROM users WHERE username='" +
            response + "'";

            try {
                Statement state = conn.createStatement();
                int numDeleted = state.executeUpdate(query);
                if (numDeleted==0) {
                    System.out.println("No user with that username");
                }
                adminMenu();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        mainMenu();
    }

}