package main;

import data.Data;
import exceptions.PermissionDeniedException;
import exceptions.StatusUnavailableException;
import flight.Airport;
import flight.Flight;
import flight.FlightStatus;
import user.Admin;
import user.Passenger;
import user.User;

import java.sql.*;
import java.util.Date;

public class MainMethod {
    private User currentUser;
    private boolean isLogin;
    private boolean isAdmin;

    public MainMethod() {
        isLogin = false;
        isAdmin = false;
    }

    public boolean Login(String userName, String password) {
        User userTemp;
        for (int i = 0; i < Data.admins.size(); i++) {
            userTemp = Data.admins.get(i);
            if (userTemp.getUserName() == userName && userTemp.getPassHash().equals(User.hashPass(password))) {
                isLogin = true;
                isAdmin = true;
                currentUser = userTemp;
                return true;
            }
        }
        for (int i = 0; i < Data.admins.size(); i++) {
            userTemp = Data.passengers.get(i);
            if (userTemp.getUserName() == userName && userTemp.getPassHash().equals(User.hashPass(password))) {
                isLogin = true;
                currentUser = userTemp;
                return true;
            }
        }
        return false;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean createFlight(String flightNumber, Date departureTime, Date arrivalTime, int departureAirportName, int arrivalAirportName, int price,
                                int seatCapacity) throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            new Flight(flightNumber, departureTime, arrivalTime, Airport.getAirportByName(departureAirportName), Airport.getAirportByName(arrivalAirportName),
                    price, seatCapacity);
            return true;
        } else {
            throw new PermissionDeniedException();
        }
    }

    public void deleteFlight(int flightID) throws PermissionDeniedException, StatusUnavailableException {
        if (isLogin && isAdmin) {
            if (Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.UNPUBLISHED || Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.TERMINATE) {
                Data.flights.remove(Flight.getFlightByID(flightID));
                /** Database */
                try {
                    /** Initialize the MySQL Connection */
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

    public void queryFlight() {
        // XXX I think this can be changed to a class
    }

    public void superQuery() {
        // XXX I think this can be changed to a class extends queryFlight
    }

    public void addAdmin(String userName, String password) throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            Admin newAdmin = new Admin(userName, password);
        } else {
            throw new PermissionDeniedException();
        }
    }

    public User getCurrentUser() throws PermissionDeniedException {
        if (isLogin && isAdmin) {
            return currentUser;
        } else {
            throw new PermissionDeniedException();
        }
    }

    public void reserveFlight(int flightID) throws PermissionDeniedException, StatusUnavailableException {
        // TODO reserveFlight
        if (isLogin) {
            if (!isAdmin) {
                Flight flight = Flight.getFlightByID(flightID);
                flight.addPassenger((Passenger) currentUser);
            }
        } else {
            throw new PermissionDeniedException();
        }
    }

    public boolean unsubscribeFlight(int flightID) throws PermissionDeniedException { //return false when no flight is found
        // TODO unsubscribeFlight
        return false;
    }
}
