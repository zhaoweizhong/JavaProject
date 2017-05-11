package order;

import java.util.Date;

import flight.Flight;
import user.Passenger;

public class Order {

    private Passenger passenger;
    private int seat;
    private Flight flight;
    private Date createDate;
    private OrderStatus status;

    public Order(Passenger passenger, Flight flight, int seat) {
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        createDate = new Date(); //now
        status = OrderStatus.UNPAID;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public int getSeat() {
        return seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void printOrder() {
    }
}
