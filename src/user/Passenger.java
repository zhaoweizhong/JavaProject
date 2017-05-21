package user;

import java.sql.*;
import java.util.ArrayList;

import data.Data;
import order.Order;

public class Passenger extends User {
    private static int passengerQuantity = 0;
    private int passengerID;
    private String identityID;
    private String realName;
    private ArrayList<String> orderIDs;

    public Passenger(String identityID, String userName, String realName, String password) {
        /* Local */
        this.identityID = identityID;
        this.userName = userName;
        this.realName = realName;
        passengerQuantity++;
        passengerID = passengerQuantity;
        passHash = hashPass(password);
        orderIDs = new ArrayList<>();
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
            String sql = "insert into passenger (passengerQuantity,passengerID,realName,userName,identityID,passHash,orderIDs) values(?,?,?,?,?,?,?)";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, passengerQuantity);
            pstmt.setInt(2, passengerID);
            pstmt.setString(3, realName);
            pstmt.setString(4, userName);
            pstmt.setString(5, identityID);
            pstmt.setString(6, passHash);
            pstmt.setString(7, orderIDs.toString());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public Passenger(int passengerQuantity, int passengerID, String identityID, String userName, String realName, String passHash, ArrayList<String> orderIDs) {
        this.identityID = identityID;
        this.userName = userName;
        this.realName = realName;
        Passenger.passengerQuantity = passengerQuantity;
        this.passengerID = passengerID;
        this.passHash = passHash;
        this.orderIDs = orderIDs;
    }

    public int getPassengerQuantity() {
        return passengerQuantity;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public String getIdentityID() {
        return identityID;
    }

    public String getRealName() {
        return realName;
    }

    public static Passenger getPassengerByID(int passengerID) {
        for (Passenger passenger:Data.passengers) {
            if (passenger.passengerID == passengerID) {
                return passenger;
            }
        }
        return null;
    }

    public void addOrder(int orderID) {
        this.orderIDs.add(String.valueOf(orderID));
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
            String sql = "update passenger set orderIDs='" + orderIDs.toString() +"' where passengerID='" + this.passengerID + "'";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public void deleteOrder(int orderID) {
        this.orderIDs.remove(String.valueOf(orderID));
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
            String sql = "update passenger set orderIDs='" + orderIDs.toString() +"' where passengerID='" + this.passengerID + "'";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public ArrayList<String> getOrderIDs() {
        return orderIDs;
    }
}