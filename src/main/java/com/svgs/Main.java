package com.svgs;

import java.sql.Connection;
import java.sql.DriverManager;
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
            conn.close();
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void mainMenu() {
        System.out.println("1. Log in");
        System.out.println("2. Create User");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();
        if (choice.equals("1")) {
            
        } else if (choice.equals("2")) {
            
        } else if (choice.equals("3")) {
            return;
        } else {
            System.out.println("Invalid choice");
        }
        mainMenu();

    }
}