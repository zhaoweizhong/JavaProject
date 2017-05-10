package order;

import java.util.Date;

import flight.Flight;
import user.Passenger;

public class Order {

    private Passenger passenger;
    private int seat;
    private Flight flight;
    private Date creatDate;
    private OrderStatus status;

    public Order(Passenger passager, Flight flight, int seat) {
        this.passenger = passager;
        this.flight = flight;
        this.seat = seat;
        creatDate = new Date(); //now
        status = OrderStatus.UNPAID;
    }

    public Passenger getPassager() {
        return passenger;
    }

    public int getSeat() {
        return seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void printOrder() {
    }
}
