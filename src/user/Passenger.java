package user;

import java.util.ArrayList;
import order.Order;

public class Passenger extends User {
    private static int passengerID = 0;
    private String identityID;
    private String realName;
    private ArrayList<Order> orderList;

    public Passenger(String identityID, String userName, String realName, String password) {
        this.identityID = identityID;
        this.userName = userName;
        this.realName = realName;
        passengerID++;
        passHash = hashPass(password);
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

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }
}