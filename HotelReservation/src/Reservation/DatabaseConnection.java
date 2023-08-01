package Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/reservation";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "ai004@sanjay";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}