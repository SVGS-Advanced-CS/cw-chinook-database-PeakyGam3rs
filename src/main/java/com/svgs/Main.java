package com.svgs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:./src/main/resources/chinook.db";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement state = conn.createStatement();
            String query = "SELECT * FROM employees ORDER BY LastName ASC";
            ResultSet results = state.executeQuery(query);

            while (results.next()) {
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                System.out.println("First Name: " + firstName + " Last Name: " + lastName);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}