package main;

import data.Data;
import exceptions.PermissionDeniedException;
import exceptions.StatusUnavailableException;
import flight.Airport;
import flight.Flight;
import flight.FlightStatus;
import flight.SeatClass;
import order.Order;
import user.Admin;
import user.Passenger;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMethods {
    private User currentUser;
    private boolean isLogin;
    private boolean isAdmin;

    public MainMethods() {
        isLogin = false;
        isAdmin = false;
    }

    public boolean register(String identityID, String userName, String realName, String password) {
        for (Passenger passenger:Data.passengers) {
            if (passenger.getUserName().equals(userName)) {
                return false;
            }
        }
        Passenger passenger = new Passenger(identityID, userName, realName, password);
        Data.passengers.add(passenger);
        return true;
    }

    public boolean login(String userName, String password) {
        User userTemp;
        for (int i = 0; i < Data.admins.size(); i++) {
            userTemp = Data.admins.get(i);
            if (userTemp.getUserName().equals(userName) && userTemp.getPassHash().equals(User.hashPass(password))) {
                isLogin = true;
                isAdmin = true;
                currentUser = userTemp;
                return true;
            }
        }
        for (int i = 0; i < Data.passengers.size(); i++) {
            userTemp = Data.passengers.get(i);
            if (userTemp.getUserName().equals(userName) && userTemp.getPassHash().equals(User.hashPass(password))) {
                isLogin = true;
                currentUser = userTemp;
                return true;
            }
        }
        return false;
    }

    public boolean logout() {
        if (isLogin) {
            isLogin = false;
            isAdmin = false;
            currentUser = null;
            return true;
        }
        return false;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean createFlight(String flightNumber, Date departureTime, Date arrivalTime, String departureAirportName, String arrivalAirportName, int economyPrice,
                                int firstPrice) throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            if (arrivalTime.getTime() > departureTime.getTime() && departureTime.getTime() > new Date().getTime()) {
                Flight flight = new Flight(flightNumber, departureTime, arrivalTime, Airport.getAirportByName(departureAirportName), Airport.getAirportByName(arrivalAirportName),
                        economyPrice, firstPrice);
                Data.flights.add(flight);
            }else{
                return false;
            }
            return true;
        } else {
            throw new PermissionDeniedException();
        }
    }

    public void deleteFlight(int flightID) throws PermissionDeniedException, StatusUnavailableException {
        if (isLogin && isAdmin) {
            if (Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.UNPUBLISHED || Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.TERMINATE) {
                Data.flights.remove(Flight.getFlightByID(flightID));
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
                    String sql = "delete from flight where flightID='" + flightID + "'";
                    PreparedStatement pstmt;
                    pstmt = conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                }catch(Exception e)
                {e.printStackTrace();}
            } else {
                throw new StatusUnavailableException();
            }
        } else {
            throw new PermissionDeniedException();
        }
    }

    public boolean updateFlight(int flightId, String flightNumber, Date departureTime, Date arrivalTime, String departureAirportName, String arrivalAirportName, int economyPrice, int firstPrice) {
        Flight flight = Flight.getFlightByID(flightId);
        try{
            flight.setFlightNumber(flightNumber);
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
            flight.setDepartureAirport(Airport.getAirportByName(departureAirportName));
            flight.setArrivalAirport(Airport.getAirportByName(arrivalAirportName));
            flight.setEconomyPrice(economyPrice);
            flight.setFirstPrice(firstPrice);
            return true;
        }catch (StatusUnavailableException e){
            return false;
        }

    }

    public boolean updateFlight(int flightId, int economyPrice, int firstPrice) {
        Flight flight = Flight.getFlightByID(flightId);
        try{
            flight.setEconomyPrice(economyPrice);
            flight.setFirstPrice(firstPrice);
            return true;
        }catch (StatusUnavailableException e){
            return false;
        }
    }

    public void changePassword(String password) throws PermissionDeniedException {
        if (isLogin) {
            currentUser.changePass(password);
        }else{
            throw new PermissionDeniedException();
        }
    }

    public ArrayList<Flight> queryFlight(String departureAirport, String arrivalAirport, Date departureDate) {
        ArrayList<Flight> flights = new ArrayList<>();
        for (Flight flight:Data.flights) {
            if (flight.getDepartureAirport().getAirportName().equals(departureAirport) && flight.getArrivalAirport().getAirportName().equals(arrivalAirport)) {
                long departureTimestamp = flight.getDepartureTime().getTime();
                Calendar departureTimeStart = new GregorianCalendar();
                departureTimeStart.setTime(departureDate);
                departureTimeStart.set(Calendar.HOUR_OF_DAY,0);
                departureTimeStart.set(Calendar.MINUTE,0);
                departureTimeStart.set(Calendar.SECOND,0);
                departureTimeStart.set(Calendar.MILLISECOND,0);
                Calendar departureTimeStop = new GregorianCalendar();
                departureTimeStop.setTime(departureDate);
                departureTimeStop.set(Calendar.HOUR_OF_DAY,23);
                departureTimeStop.set(Calendar.MINUTE,59);
                departureTimeStop.set(Calendar.SECOND,59);
                departureTimeStop.set(Calendar.MILLISECOND,999);
                if (departureTimestamp <= departureTimeStop.getTimeInMillis() && departureTimestamp >= departureTimeStart.getTimeInMillis()){
                    flights.add(flight);
                }
            }
        }
        return flights; /* The return value may be null */
    }

    public ArrayList<Flight> queryFlight(String flightNumber) {
        ArrayList<Flight> flights = new ArrayList<>();
        Pattern pattern = Pattern.compile(flightNumber);
        for (Flight flight:Data.flights) {
            Matcher matcher = pattern.matcher(flight.getFlightNumber());
            if(matcher.find()){
                flights.add(flight);
            }
        }
        return flights; /* The return value may be null */
    }

    public ArrayList<Order> listOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order order:Data.orders) {
            if (order.getPassenger().getUserName().equals(currentUser.getUserName())) {
                orders.add(order);
            }
        }
        return orders; /* The return value may be null */
    }

    public ArrayList<Order> superQuery(String flightNumber) throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            ArrayList<Order> orders = new ArrayList<>();
            for (Order order:Data.orders) {
                if (order.getFlight().getFlightNumber().equals(flightNumber)) {
                    orders.add(order);
                }
            }
            return orders; /* The return value may be null */
        } else {
            throw new PermissionDeniedException();
        }
    }

    public ArrayList<Order> superQuery(int passengerID) throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            ArrayList<Order> orders = new ArrayList<>();
            for (String orderID:Passenger.getPassengerByID(passengerID).getOrderIDs()) {
                orders.add(Order.getOrderByID(Integer.parseInt(orderID)));
            }
            return orders; /* The return value may be null */
        } else {
            throw new PermissionDeniedException();
        }
    }

        public void addAdmin(String userName, String password) throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            Admin newAdmin = new Admin(userName, password);
            Data.admins.add(newAdmin);
        } else {
            throw new PermissionDeniedException();
        }
    }

    public int reserveFlight(int flightID, String seatClass) throws PermissionDeniedException, StatusUnavailableException {
        if (isLogin && !isAdmin) {
            if (Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.AVAILABLE) {
                Flight flight = Flight.getFlightByID(flightID);
                flight.addPassenger((Passenger)currentUser);
                Order order = new Order((Passenger)currentUser, flight, SeatClass.valueOf(seatClass));
                Data.orders.add(order);
                ((Passenger) currentUser).addOrder(order.getOrderID());
                return order.getOrderID();
            }else{
                throw new StatusUnavailableException();
            }
        } else {
            throw new PermissionDeniedException();
        }
    }

    public boolean unsubscribeFlight(int orderID) throws PermissionDeniedException { // Return false when no flight is found
        if (isLogin && !isAdmin) {
            if (Data.orders.indexOf(Order.getOrderByID(orderID)) != -1 && Order.getOrderByID(orderID).getFlight().getPassengers().indexOf((Passenger)currentUser) != -1) {
                Order.getOrderByID(orderID).cancelOrder();
                Order.getOrderByID(orderID).getFlight().getPassengers().remove((Passenger)currentUser);
                if (Order.getOrderByID(orderID).getFlight().getFlightStatus() != FlightStatus.FULL) {
                    Order.getOrderByID(orderID).getFlight().setFlightStatus(FlightStatus.AVAILABLE);
                }
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
                    String sql = "delete from orders where orderID=" + orderID;
                    PreparedStatement pstmt;
                    pstmt = conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                }catch(Exception e)
                {e.printStackTrace();}
                ((Passenger) currentUser).deleteOrder(orderID);
                Order.getOrderByID(orderID).getFlight().removePassenger((Passenger)currentUser);
                Order.getOrderByID(orderID).getFlight().removeSeatBooked(Order.getOrderByID(orderID).getSeatNumber());
                Data.orders.remove(Order.getOrderByID(orderID));
                return true;
            }else{
                return false;
            }
        }else{
            throw new PermissionDeniedException();
        }
    }
}
