package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import data.Data;
import flight.FlightStatus;

public class MainTest {
    public static void main(String[] args) {
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

            String sql = "update orders set seatNumber='15' where orderID='12'";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
