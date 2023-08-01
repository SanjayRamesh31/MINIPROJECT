//package Reservation;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class HotelReservationSystem {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/reservation";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "ai004@sanjay";
//
//    private static Map<String, Hotel> hotels = new HashMap<>();
//    private static Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        loadHotelsFromDatabase();
//        while (true) {
//            showMenu();
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    displayAvailableRooms();
//                    break;
//                case 2:
//                    makeReservation();
//                    break;
//                case 3:
//                    System.out.println("Exiting the Hotel Reservation System. Goodbye!");
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }
//
//    private static void showMenu() {
//        System.out.println("\n--- Hotel Reservation System Menu ---");
//        System.out.println("1. Display Available Rooms");
//        System.out.println("2. Make a Reservation");
//        System.out.println("3. Exit");
//        System.out.print("Enter your choice: ");
//        System.out.println();
//    }
//
//    private static void loadHotelsFromDatabase() {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM hotels");
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String location = resultSet.getString("location");
//                Hotel hotel = new Hotel(name, location);
//                loadRoomsForHotel(connection, hotel);
//                hotels.put(name, hotel);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void loadRoomsForHotel(Connection connection, Hotel hotel) {
//        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE hotel_name = ?")) {
//            statement.setString(1, hotel.getName());
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    int roomNumber = resultSet.getInt("room_number");
//                    String type = resultSet.getString("type");
//                    double price = resultSet.getDouble("price");
//                    
//                    hotel.addRoom(new Room(roomNumber, type, price));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void displayAvailableRooms() {
//        System.out.println("\nAvailable Hotels:");
//        for (Hotel hotel : hotels.values()) {
//            System.out.println(hotel.getName() + " - " + hotel.getLocation());
//        }
//
//        System.out.print("Enter the name of the hotel to view available rooms: ");
//        String hotelName = scanner.nextLine();
//        Hotel selectedHotel = hotels.get(hotelName);
//
//        if (selectedHotel == null) {
//            System.out.println("Hotel not found.");
//            return;
//        }
//
//        System.out.println("\nAvailable Rooms in " + selectedHotel.getName() + ":");
//        for (Room room : selectedHotel.getRooms()) {
//            System.out.println(
//                    "Room " + room.getRoomNumber() + " - " + room.getType() + " - Price per night: $" + room.getPrice());
//        }
//    }
//
//    private static void makeReservation() {
//        System.out.print("Enter hotel name: ");
//        String hotelName = scanner.nextLine();
//        Hotel selectedHotel = hotels.get(hotelName);
//
//        if (selectedHotel == null) {
//            System.out.println("Hotel not found.");
//            return;
//        }
//
//        System.out.print("Enter room number: ");
//        int roomNumber = scanner.nextInt();
//        scanner.nextLine();
//
//        Room selectedRoom = selectedHotel.getRoom(roomNumber);
//        if (selectedRoom == null) {
//            System.out.println("Room not found.");
//            return;
//        }
//
//        System.out.print("Enter check-in date (YYYY-MM-DD): ");
//        String checkInDate = scanner.nextLine();
//        System.out.print("Enter check-out date (YYYY-MM-DD): ");
//        String checkOutDate = scanner.nextLine();
//
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(
//                     "INSERT INTO reservations (hotel_name, room_number, check_in_date, check_out_date) " +
//                             "VALUES (?, ?, ?, ?)")) {
//            statement.setString(1, hotelName);
//            statement.setInt(2, roomNumber);
//            statement.setString(3, checkInDate);
//            statement.setString(4, checkOutDate);
//            int rowsAffected = statement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                System.out.println("Reservation successful!");
//            } else {
//                System.out.println("Reservation failed. Please try again.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}








package Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HotelReservationSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/reservation";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "ai004@sanjay";

    private static Scanner scanner = new Scanner(System.in);
    private static HotelManager hotelManager = new HotelManager();

    public static void main(String[] args) {
        loadHotelsFromDatabase();
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAvailableRooms();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    System.out.println("Exiting the Hotel Reservation System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Hotel Reservation System Menu ---");
        System.out.println("1. Display Available Rooms");
        System.out.println("2. Make a Reservation");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void loadHotelsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM hotels");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                Hotel hotel = new Hotel(name, location);
                loadRoomsForHotel(connection, hotel);
                hotelManager.addHotel(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadRoomsForHotel(Connection connection, Hotel hotel) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE hotel_name = ?")) {
            statement.setString(1, hotel.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    String type = resultSet.getString("type");
                    double price = resultSet.getDouble("price");

                    hotel.addRoom(new Room(roomNumber, type, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAvailableRooms() {
        System.out.println("\nAvailable Hotels:");
        for (Hotel hotel : hotelManager.getAllHotels()) {
            System.out.println(hotel.getName() + " - " + hotel.getLocation());
        }

        System.out.print("Enter the name of the hotel to view available rooms: ");
        String hotelName = scanner.nextLine();
        Hotel selectedHotel = hotelManager.getHotelByName(hotelName);

        if (selectedHotel == null) {
            System.out.println("Hotel not found.");
            return;
        }

        System.out.println("\nAvailable Rooms in " + selectedHotel.getName() + ":");
        for (Room room : selectedHotel.getRooms()) {
            System.out.println(
                    "Room " + room.getRoomNumber() + " - " + room.getType() + " - Price per night: $" + room.getPrice());
        }
    }

    private static void makeReservation() {
        System.out.print("Enter hotel name: ");
        String hotelName = scanner.nextLine();
        Hotel selectedHotel = hotelManager.getHotelByName(hotelName);

        if (selectedHotel == null) {
            System.out.println("Hotel not found.");
            return;
        }

        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = selectedHotel.getRoom(roomNumber);
        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO reservations (hotel_name, room_number, check_in_date, check_out_date) " +
                             "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, hotelName);
            statement.setInt(2, roomNumber);
            statement.setString(3, checkInDate);
            statement.setString(4, checkOutDate);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reservation successful!");
            } else {
                System.out.println("Reservation failed. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}







