package Reservation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class HotelManager {
    private Map<String, Hotel> hotels = new HashMap<>();

    public void addHotel(Hotel hotel) {
        hotels.put(hotel.getName(), hotel);
    }

    public Collection<Hotel> getAllHotels() {
        return hotels.values();
    }

    public Hotel getHotelByName(String name) {
        return hotels.get(name);
    }
}