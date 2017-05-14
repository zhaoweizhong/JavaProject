package data;

import flight.Airport;
import flight.Flight;
import order.Order;
import user.Admin;
import user.Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    public ArrayList<Admin> admins = new ArrayList<>();
    public ArrayList<Passenger> passengers = new ArrayList<>();
    public ArrayList<Flight> flights = new ArrayList<>();
    public ArrayList<Airport> airports = new ArrayList<>();

    public Data() {
    }

    public void initialize() {
        try{
            /** Initialize the MySQL Connection */
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("成功加载MySQL驱动！");

            String url = "jdbc:mysql://ss.lomme.cn:3306/flight";    //JDBC的URL
            Connection conn;

            conn = DriverManager.getConnection(url,"flight","123130");
            Statement stmt = conn.createStatement(); //创建Statement对象
            //System.out.println("成功连接到数据库！");

            /** Admin */
            String sql1 = "select * from admin";    //要执行的SQL
            ResultSet rs1 = stmt.executeQuery(sql1);//创建数据对象
            while (rs1.next()){
                Admin admin = new Admin(rs1.getString(1), rs1.getString(2));
                admins.add(admin);
            }
            rs1.close();

            /** Passenger */
            String sql2 = "select * from passenger";    //要执行的SQL
            ResultSet rs2 = stmt.executeQuery(sql2);//创建数据对象
            while (rs2.next()){
                Passenger passenger = new Passenger(rs2.getInt(1), rs2.getInt(2), rs2.getString(5), rs2.getString(4), rs2.getString(3), rs2.getString(6), toArrayList(rs2.getString(7)));
                passengers.add(passenger);
            }
            rs2.close();

            /** Airport */
            String sql3 = "select * from airport";    //要执行的SQL
            ResultSet rs3 = stmt.executeQuery(sql3);//创建数据对象
            while (rs3.next()){
                Airport airport = new Airport(rs3.getInt(1), rs3.getInt(2), rs3.getString(3), rs3.getString(4));
                airports.add(airport);
            }
            rs3.close();

            /** Flight */
            String sql4 = "select * from flight";    //要执行的SQL
            ResultSet rs4 = stmt.executeQuery(sql4);//创建数据对象
            while (rs4.next()){
                Flight flight = new Flight();
                flights.add(flight);
            }
            rs4.close();

            /** Close the MySQL Connection */
            stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String toString(ArrayList<String> arrayList) {
        int size=arrayList.size();
        String[] str = arrayList.toArray(new String[size]);
        return Arrays.toString(str);
    }

    public static ArrayList<String> toArrayList(String str) {
        String str1 = str.replace("[","");
        String str2 = str1.replace("]","");
        String str3 = str2.replace(" ","");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(str3.split(",")));
        return arrayList;
    }
}