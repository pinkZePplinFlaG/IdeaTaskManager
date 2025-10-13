package com.digitalnotepad.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector {
    static String connectionUrl = "jdbc:mysql://localhost:3306/ideas?serverTimezone=UTC";
    static List<String> responseData;
    public static List<String> connectToDatabase(String SQLQuery){
        responseData = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, "java", "password"); 
                PreparedStatement ps = conn.prepareStatement(SQLQuery); 
                ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    long id = rs.getLong("id");
                    String title = rs.getString("title");
                    Date created = rs.getDate(3);
                    String implemented = rs.getString("implemented");
                    String description = rs.getString("description");
                    System.out.println("id: "+id);
                    System.out.println("title: "+title);
                    System.out.println("created: "+created.toString());
                    System.out.println("implemented: "+implemented);
                    System.out.println("description: "+description);
                    responseData.add("id:");
                    responseData.add(Long.toString(id));
                    responseData.add("title:");
                    responseData.add(title);
                    responseData.add("created:");
                    responseData.add(created.toString());
                    responseData.add("implemented:");
                    responseData.add(implemented);
                    responseData.add("description:");
                    responseData.add(description);
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responseData;
    }    
}
