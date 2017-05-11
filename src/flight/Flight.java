package flight;

import user.Passenger;

import java.util.ArrayList;

public class Flight {
    private static int flightID = 0;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private int price;
    private int seatCapacity;
    private int[] seatBooked;
    private FlightStatus flightStatus;
    private ArrayList<Passenger> passengers;

}
