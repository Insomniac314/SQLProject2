package com.sqliteproject2;

import java.io.File;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        File f = new File("test.db");
        int[] ids = new int[]{706,456,249,223,1423};
        int[] senses = new int[]{2,4,5,1,3};
        int[] powers = new int[]{3,5,4,2,1};
        int[] intels = new int[]{5,1,3,4,2};
        int[] charis = new int[]{1,3,2,5,4};
        int[] endurs = new int[]{4,2,1,3,5};
        
        try {
            Class.forName("org.sqlite.JDBC");
            if(!f.exists()){
                System.out.println("DB not found. Creating one");
                conn = DriverManager.getConnection("jdbc:sqlite:test.db");
                System.out.println("Creating Statement");
                stmt = conn.createStatement();
                System.out.println("Execution");
                stmt.execute("CREATE TABLE zNumber (id INT PRIMARY KEY, sense INT, power INT, intel INT, chari INT, endur INT);");
                pstmt = conn.prepareStatement("INSERT INTO zNumber (id,sense,power,intel,chari,endur) VALUES (?,?,?,?,?,?)");
                for(int i = 0; i < ids.length; i++){
                    pstmt.setInt(1,ids[i]);
                    pstmt.setInt(2,senses[i]);
                    pstmt.setInt(3,powers[i]);
                    pstmt.setInt(4,intels[i]);
                    pstmt.setInt(5,charis[i]);
                    pstmt.setInt(6,endurs[i]);
                    pstmt.executeUpdate();
                }
            }

            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM zNumber");
            while(rs.next()){
                System.out.println(
                    rs.getInt("id") + " " +
                    rs.getInt("sense") + " " +
                    rs.getInt("power") + " " +                                   
                    rs.getInt("intel") + " " +
                    rs.getInt("chari") + " " +
                    rs.getInt("endur")
                );
            }
        } catch (SQLException e) {
            System.out.println("Fault Locator detected SQLException");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());
        }
    }
}