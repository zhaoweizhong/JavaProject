package user;

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
        this.identityID = identityID;
        this.userName = userName;
        this.realName = realName;
        passengerQuantity++;
        passengerID = passengerQuantity;
        passHash = hashPass(password);
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

    public Passenger getPassengerByID(int passengerID) {
        return null;
    }

    public void addOrder(int orderID) {
        orderIDs.add(String.valueOf(orderID));
    }

    public ArrayList<String> getOrderIDs() {
        return orderIDs;
    }
}