package flight;

import user.Passenger;

import java.util.ArrayList;
import java.util.List;

public class Flight {
    private static int flightQuantity = 0;
    private int flightID;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private int price;
    private int[] seatBooked;
    private FlightStatus flightStatus;
    private ArrayList<Passenger> passengers;

    public Flight(String flightNumber, String departureTime, String arrivalTime, String departureAirport, String arrivalAirport, int price) {
        passengers = new ArrayList<>();
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.price = price;
        this.flightStatus = FlightStatus.UNPUBLISHED;
        flightQuantity++;
        flightID = flightQuantity;
        seatBooked = new int[0];

    }



    public int[] getSeatBooked() {
        return seatBooked;
    }

    public void addSeatBooked(int seatNumber) {
        List<Integer> list = new ArrayList<>();
        for (int i=0; i<seatBooked.length; i++) {
            list.add(seatBooked[i]);
        }
        list.add(seatNumber);
        int[] newSeatBooked = new int[list.size()];
        for(int i = 0;i<list.size();i++){
            newSeatBooked[i] = list.get(i);
        }
        seatBooked = newSeatBooked;
    }
}
