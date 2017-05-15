package flight;

import data.Data;
import exceptions.StatusUnavailableException;
import user.Passenger;

import java.util.*;

public class Flight {
    private static int flightQuantity = 0;
    private int flightID;
    private String flightNumber;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private int price;
    private int seatCapacity;
    private ArrayList<String> seatBooked;
    private FlightStatus flightStatus;
    private ArrayList<Passenger> passengers;

    public Flight(String flightNumber, Date departureTime, Date arrivalTime, Airport departureAirport, Airport arrivalAirport, int price, int seatCapacity) {
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
        this.seatCapacity = seatCapacity;
        seatBooked = new ArrayList<>();
    }

    public Flight(int flightQuantity, int flightID, String flightNumber, int departureTimestamp, int arrivalTimestamp, int departureAirportID, int arrivalAirportID,
                  int price, int seatCapacity, ArrayList<String> seatBooked, String flightStatus, ArrayList<String> passengerIDs) {
        Flight.flightQuantity = flightQuantity;
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        departureTime = new Date(departureTimestamp);
        arrivalTime = new Date(arrivalTimestamp);
        departureAirport = Airport.getAirportByID(departureAirportID);
        arrivalAirport = Airport.getAirportByID(arrivalAirportID);
        this.price = price;
        this.seatCapacity = seatCapacity;
        this.seatBooked = seatBooked;
        passengers = new ArrayList<>();
        this.flightStatus = FlightStatus.valueOf(flightStatus);
        for (int i = 0; i <= passengerIDs.size(); i++) {
            passengers.add(Passenger.getPassengerByID(Integer.parseInt(passengerIDs.get(i))));
        }
    }

    public static Date calendar(int year, int month, int date, int hr, int min, int sec) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"));
        calendar.clear();
        calendar.set(year, month - 1, date, hr, min, sec);
        return calendar.getTime();
    }

    public int getFlightQuantity() {
        return flightQuantity;
    }

    public int getFlightID() {
        return flightID;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.flightNumber = flightNumber;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.departureTime = departureTime;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.arrivalTime = arrivalTime;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.departureAirport = departureAirport;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.arrivalAirport = arrivalAirport;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.price = price;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            passengers.add(passenger);
        } else {
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public static Flight getFlightByID(int flightID) {
        for (Flight flight: Data.flights) {
            if (flight.flightID == flightID) {
                return flight;
            }
        }
        return null;
    }

    public ArrayList<String> getSeatBooked() {
        return seatBooked;
    }

    public void addSeatBooked(int seatNumber) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            seatBooked.add(String.valueOf(seatNumber));
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }
}
