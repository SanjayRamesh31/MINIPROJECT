package Reservation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//package Reservation;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//class Hotel {
//      private String name;
//      private String location;
//      private Map<Integer, Room> rooms = new HashMap<>();
//
//      public Hotel(String name, String location) {
//          this.name = name;
//          this.location = location;
//      }
//
//      public String getName() {
//          return name;
//      }
//
//      public String getLocation() {
//          return location;
//      }
//
//      public void addRoom(Room room) {
//          rooms.put(room.getRoomNumber(), room);
//      }
//
//      public Collection<Room> getRooms() {
//          return rooms.values();
//      }
//
//      public Room getRoom(int roomNumber) {
//          return rooms.get(roomNumber);
//      }
//  }





class Hotel {
    private String name;
    private String location;
    private Map<Integer, Room> rooms = new HashMap<>();

    public Hotel(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }
}