package order;

import java.util.Date;
import java.util.Random;

import com.sun.tools.corba.se.idl.constExpr.Or;
import exceptions.StatusUnavailableException;
import flight.Flight;
import flight.FlightStatus;
import flight.SeatClass;
import user.Passenger;

public class Order {
    private static int orderQuantity = 0;
    private int orderID;
    private Passenger passenger;
    private SeatClass seatClass;
    private int seatNumber;
    private Flight flight;
    private Date createDate;
    private OrderStatus status;

    public Order(Passenger passenger, Flight flight, SeatClass seatClass) {
        this.passenger = passenger;
        this.flight = flight;
        this.seatClass = seatClass;
        createDate = new Date(); // Now
        status = OrderStatus.UNPAID;
        orderQuantity++;
        orderID = orderQuantity;
    }

    public Order(int orderQuantity, int orderID, int passengerID, String seatClass, int seatNumber, int flightID, int createDateTimestamp, String orderStatus) {
        Order.orderQuantity = orderQuantity;
        this.orderID = orderID;
        passenger = Passenger.getPassengerByID(passengerID);
        this.seatClass = SeatClass.valueOf(seatClass);
        this.seatNumber = seatNumber;
        flight = Flight.getFlightByID(flightID);
        createDate = new Date(createDateTimestamp);
        status = OrderStatus.valueOf(orderStatus);
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getSeat() {
        String seat = null;
        if (seatClass == SeatClass.EconomyClass) {
            switch (seatNumber % 6) {
                case 0: seat = (seatNumber / 6) + "A"; break;
                case 1: seat = (seatNumber / 6) + "B"; break;
                case 2: seat = (seatNumber / 6) + "C"; break;
                case 3: seat = (seatNumber / 6) + "H"; break;
                case 4: seat = (seatNumber / 6) + "J"; break;
                case 5: seat = (seatNumber / 6) + "K"; break;
            }
        }else{
            switch (seatNumber % 4) {
                case 0: seat = (seatNumber / 4) + "A"; break;
                case 1: seat = (seatNumber / 4) + "C"; break;
                case 2: seat = (seatNumber / 4) + "H"; break;
                case 3: seat = (seatNumber / 4) + "K"; break;
            }
        }
        return seat;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public SeatClass getSeatClass() {
        return seatClass;
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

    public void processOrder() {
        try {
            // Generate Seat Number by Random
            Random random = new Random();
            boolean flag = true;
            if (seatClass == SeatClass.EconomyClass) {
                do {
                    int num = random.nextInt(145) + 8;
                    for(int i=0;i<flight.getSeatBooked().size();i++){
                        if(String.valueOf(num) == flight.getSeatBooked().get(i)){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        flight.addSeatBooked(num);
                        seatNumber = num;
                        break;
                    }
                }while (1 == 1);
            }else{
                do {
                    int num = random.nextInt(9);
                    for(int i=0;i<flight.getSeatBooked().size();i++){
                        if(String.valueOf(num) == flight.getSeatBooked().get(i)){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        flight.addSeatBooked(num);
                        seatNumber = num;
                        break;
                    }
                }while (1 == 1);
            }
        }
        catch (StatusUnavailableException ex){
            System.out.println("The flight is not available.");
        }
    }
}
