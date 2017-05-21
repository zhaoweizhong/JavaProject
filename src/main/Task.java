package main;

import data.Data;
import flight.Flight;
import flight.FlightStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class Task extends TimerTask {
    public static final long CHECKING_INTERVAL = 1000l; // 1 sec
    public static final long DAY_OF_CREATE = 30 * 24 * 3600 * 1000l; // 18 days
    public static final long INTERVAL_TO_CREATE = 3600 * 1000l; // 1 hr
    public static final long TIME_TO_TERMINATE = 2 * 3600 * 1000l; // 2 hrs
    public static final long TIME_TO_PUBLISH = 15 * 24 * 3600 * 1000l; // 8 days

    @Override
    public void run() {
        ArrayList<Flight> flights0 = (ArrayList<Flight>)Data.flights.clone();
        Date now = new Date();
        for (Flight flight: flights0) {
            if (flight.getDepartureTime().getTime() - now.getTime() <= TIME_TO_TERMINATE) {
                flight.setFlightStatus(FlightStatus.TERMINATE);
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
                    String sql = "update flight set flightStatus='" + FlightStatus.TERMINATE.toString() +"' where flightID='" + flight.getFlightID() + "'";
                    PreparedStatement pstmt;
                    pstmt = conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                }catch(Exception e)
                {e.printStackTrace();}
            }else if (flight.getDepartureTime().getTime() - now.getTime() <= TIME_TO_PUBLISH) {
                flight.setFlightStatus(FlightStatus.AVAILABLE);
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
                    String sql = "update flight set flightStatus='" + FlightStatus.AVAILABLE.toString() +"' where flightID='" + flight.getFlightID() + "'";
                    PreparedStatement pstmt;
                    pstmt = conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                }catch(Exception e)
                {e.printStackTrace();}
            }
        }
    }
}