package user;

import java.util.ArrayList;
import order.Order;

public class Passenger extends User {

    private String identityID;
    private ArrayList<Order> orderList;

    public Passenger(String identityID, String realName, String password) {
        this.identityID = identityID;
        this.userName = realName;
        User.userID++;
        passHash = hashPass(password);
    }

    public String getIdentityID() {
        return identityID;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }
}
