package user;

import java.sql.*;

public class Admin extends User {
    public Admin(String userName, String password) {
        /** Local */
        this.userName = userName;
        passHash = hashPass(password);
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
            String sql = "insert into admin (userName,passHash) values(?,?)";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, passHash);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public Admin(String userName, String passHash, int id) {
        this.userName = userName;
        this.passHash = passHash;
    }
}