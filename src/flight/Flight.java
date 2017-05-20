package flight;

import data.Data;
import exceptions.StatusUnavailableException;
import user.Passenger;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Flight {
    private static int flightQuantity = 0;
    private int flightID;
    private String flightNumber;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private int economyPrice;
    private int firstPrice;
    private ArrayList<String> seatBooked;
    private FlightStatus flightStatus;
    private ArrayList<Passenger> passengers;

    public Flight(String flightNumber, Date departureTime, Date arrivalTime, Airport departureAirport, Airport arrivalAirport, int economyPrice, int firstPrice) {
        /* Local */
        passengers = new ArrayList<>();
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.economyPrice = economyPrice;
        this.firstPrice = firstPrice;
        this.flightStatus = FlightStatus.UNPUBLISHED;
        flightQuantity++;
        flightID = flightQuantity;
        seatBooked = new ArrayList<>();
        /* Database */
        try {
            /* Initialize the MySQL Connection */
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("成功加载MySQL驱动！");
            String url = "jdbc:mysql://ss.lomme.cn:3306/flight";    //JDBC的URL
            Connection conn;
            conn = DriverManager.getConnection(url,"flight","123130");
            Statement stmt = conn.createStatement(); //创建Statement对象
            //System.out.println("成功连接到数据库！");
            String sql = "insert into flight (flightQuantity,flightID,flightNumber,departureTimestamp,arrivalTimestamp,departureAirportID,arrivalAirportID,economyPrice,firstPrice," +
                    "seatBooked,flightStatus,passengerIDs) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, flightQuantity++);
            pstmt.setInt(2, flightID);
            pstmt.setString(3, flightNumber);
            pstmt.setObject(4, departureTime.getTime());
            pstmt.setObject(5, arrivalTime.getTime());
            pstmt.setInt(6, departureAirport.getAirportID());
            pstmt.setInt(7, arrivalAirport.getAirportID());
            pstmt.setInt(8, economyPrice);
            pstmt.setInt(9, firstPrice);
            pstmt.setString(10, seatBooked.toString());
            pstmt.setString(11, flightStatus.toString());
            pstmt.setString(12, passengers.toString());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public Flight(int flightQuantity, int flightID, String flightNumber, long departureTimestamp, long arrivalTimestamp, int departureAirportID, int arrivalAirportID,
                  int economyPrice, int firstPrice, ArrayList<String> seatBooked, String flightStatus, ArrayList<String> passengerIDs) {
        Flight.flightQuantity = flightQuantity;
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        departureTime = new Date(departureTimestamp);
        arrivalTime = new Date(arrivalTimestamp);
        departureAirport = Airport.getAirportByID(departureAirportID);
        arrivalAirport = Airport.getAirportByID(arrivalAirportID);
        this.economyPrice = economyPrice;
        this.firstPrice = firstPrice;
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

    public int getEconomyPrice() {
        return economyPrice;
    }

    public int getFirstPrice() {
        return firstPrice;
    }

    public void setEconomyPrice(int economyPrice) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.economyPrice = economyPrice;
        }else{
            throw new StatusUnavailableException(flightStatus);
        }
    }

    public void setFirstPrice(int firstPrice) throws StatusUnavailableException {
        if (flightStatus == FlightStatus.AVAILABLE) {
            this.firstPrice = firstPrice;
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
