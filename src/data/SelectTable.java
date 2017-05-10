package data;

import java.sql.*;

public class SelectTable {
    public static String getString(String name, String table){
        try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("成功加载MySQL驱动！");

            String url = "jdbc:mysql://ss.lomme.cn:3306/flight";    //JDBC的URL
            Connection conn;

            conn = DriverManager.getConnection(url,"flight","123130");
            Statement stmt = conn.createStatement(); //创建Statement对象
            //System.out.println("成功连接到数据库！");

            String sql = "select * from " + table;    //要执行的SQL
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            //System.out.println("用户名"+"\t"+"密码");
            while (rs.next()){
                System.out.print(rs.getString(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.println();
            }
            rs.close();

            stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
