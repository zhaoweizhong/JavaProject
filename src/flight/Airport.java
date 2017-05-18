package flight;

import data.Data;
import user.Passenger;

import java.sql.*;


public class Airport {
    private static int airportQuantity = 0;
    private int airportID;
    private String airportName;
    private String cityName;

    public Airport(String airportName, String cityName) {
        /* Local */
        this.airportName = airportName;
        this.cityName = cityName;
        airportQuantity++;
        airportID = airportQuantity;
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
            String sql = "insert into airport (airportQuantity,airportID,airportName,cityName) values(?,?,?,?)";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, airportQuantity++);
            pstmt.setInt(2, airportID);
            pstmt.setString(3, airportName);
            pstmt.setString(4, cityName);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public Airport(int airportQuantity, int airportID, String airportName, String cityName) {
        this.airportName = airportName;
        this.cityName = cityName;
        Airport.airportQuantity = airportQuantity;
        this.airportID = airportID;
    }

    public int getAirportID() {
        return airportID;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static Airport getAirportByID(int airportID) {
        for (Airport airport: Data.airports) {
            if (airport.airportID == airportID) {
                return airport;
            }
        }
        return null;
    }

    public static Airport getAirportByName(String airportName) {
        for (Airport airport: Data.airports) {
            if (airport.airportName.equals(airportName)) {
                return airport;
            }
        }
        return null;
    }
}
