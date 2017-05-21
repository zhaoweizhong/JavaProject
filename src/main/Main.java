package main;

import com.sun.tools.corba.se.idl.constExpr.Or;
import data.Data;
import exceptions.PermissionDeniedException;
import exceptions.StatusUnavailableException;
import flight.Airport;
import flight.Flight;
import flight.FlightStatus;
import flight.SeatClass;
import order.Order;
import user.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

public class Main {
    public static MainMethods mainMethods = new MainMethods();
    public static final long CHECKING_INTERVAL = 1000l; // 1 sec
    public static void main(String[] args) {
        Timer timer = new Timer(false);
        timer.schedule(new Task(), CHECKING_INTERVAL, CHECKING_INTERVAL);
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**           Please enter the number to select            **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**      1) Query Flight                                   **");
        System.out.println("**      2) Login                                          **");
        System.out.println("**      3) Register                                       **");
        System.out.println("**      4) Initialize (Administrator Only)                **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 1: Clear(); QueryFlight(); break;
            case 2: Clear(); Login(); break;
            case 3: Clear(); Register(); break;
            case 4: Clear(); Initialize(); break;
            default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); main(null); break;
        }
    }

    public static void QueryFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**           Please enter the number to select            **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**    1) Query Flight By Departure and Destination        **");
        System.out.println("**    2) Query Flight By Flight Number (Fuzzy Supported)  **");
        System.out.println("**    3) Go Back                                          **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 1: Clear(); QueryFlightByDepAndDest(); break;
            case 2: Clear(); QueryFlightByFlightNum(); break;
            case 3: Clear(); if (mainMethods.isLogin()){UserCenter();}else{main(null);} break;
            default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); QueryFlight(); break;
        }
    }

    public static void QueryFlightByFlightNum() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**            Please enter the flight number              **");
        System.out.println("**                 Fuzzy Query Supported                  **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the flight number to query: ");
        String input = scanner.nextLine();
        ArrayList<Flight> flights = mainMethods.queryFlight(input);
        System.out.println("*************************************************************************************");
        System.out.println("*************************************************************************************");
        System.out.println("**                                                                                 **");
        System.out.println("**                      Welcome to the flight booking system!!                     **");
        System.out.println("**                                                                                 **");
        System.out.println("**                      Please enter the flight number to book                     **");
        System.out.println("**                                Enter 0 to go back                               **");
        System.out.println("**                                                                                 **");
        System.out.println("*************************************************************************************");
        System.out.println("**                                                                                 **");
        System.out.println("** ID.  Num.     Dep.      Dest.       Dep.Time          Dest.Time       Status    **");
        for (Flight flight:flights) {
            System.out.print("** ");
            System.out.printf("%2d)", flight.getFlightID());
            System.out.print(" ");
            System.out.printf("%-6s", flight.getFlightNumber());
            System.out.print("  ");
            System.out.printf("%-8s", flight.getDepartureAirport().getCityName());
            System.out.print("  ");
            System.out.printf("%-8s", flight.getArrivalAirport().getCityName());
            System.out.print("  ");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            System.out.print(format.format(flight.getDepartureTime()));
            System.out.print("  ");
            System.out.print(format.format(flight.getArrivalTime()));
            System.out.print("  ");
            System.out.printf("%-11s", flight.getFlightStatus());
            System.out.println(" **");
        }
        System.out.println("**                                                                                 **");
        System.out.println("*************************************************************************************");
        System.out.println("*************************************************************************************");
        System.out.print("Please enter the number to select: ");
        int input2 = scanner.nextInt();
        if (input2 == 0) {
            QueryFlightByFlightNum();
        } else {
            if (mainMethods.isLogin() && !mainMethods.isAdmin()) {
                if (Flight.getFlightByID(input2).getFlightStatus() != FlightStatus.AVAILABLE) {
                    System.out.println("Sorry, the flight you select is not available at present.");
                    Wait(1);
                    QueryFlightByFlightNum();
                }else{
                    MakeOrder(input2);
                }
            }else if (mainMethods.isLogin() && mainMethods.isAdmin()){
                System.out.println("Sorry, you are administrator so you cannot book flights.");
                Wait(1);
                QueryFlight();
            }else{
                System.out.println("Sorry, you are not logged in, please login(1) or register(2) first.");
                System.out.print("Please enter the number to select: ");
                int input3 = scanner.nextInt();
                switch (input3) {
                    case 1:
                        Clear();
                        Login();
                        break;
                    case 2:
                        Clear();
                        Register();
                        break;
                    default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); QueryFlightByFlightNum(); break;
                }
            }
        }
    }

    public static void QueryFlightByDepAndDest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**           Please enter the number to select            **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**      ID.         Airport           Airport Abbr.       **");
        for (Airport airport:Data.airports) {
            System.out.print("**     ");
            System.out.printf("%2d)", airport.getAirportID());
            System.out.print("          ");
            System.out.printf("%-8s", airport.getCityName());
            System.out.print("              ");
            System.out.printf("%-3s", airport.getAirportName());
            System.out.println("             **");
        }
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please select the Departure Airport: ");
        int depAirportNum = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        System.out.print("Please select the Departure Airport: ");
        int destAirportNum = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        System.out.print("Please enter the departure date (Format: yyyy-MM-dd): ");
        String depDateStr = scanner.nextLine();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date depDate = format1.parse(depDateStr);
            ArrayList<Flight> flights = mainMethods.queryFlight(Airport.getAirportByID(depAirportNum).getAirportName(), Airport.getAirportByID(destAirportNum).getAirportName(), depDate);
            System.out.println("*************************************************************************************");
            System.out.println("*************************************************************************************");
            System.out.println("**                                                                                 **");
            System.out.println("**                      Welcome to the flight booking system!!                     **");
            System.out.println("**                                                                                 **");
            System.out.println("**                      Please enter the flight number to book                     **");
            System.out.println("**                                Enter 0 to go back                               **");
            System.out.println("**                                                                                 **");
            System.out.println("*************************************************************************************");
            System.out.println("**                                                                                 **");
            System.out.println("** ID.  Num.     Dep.      Dest.       Dep.Time          Dest.Time       Status    **");
            for (Flight flight:flights) {
                System.out.print("** ");
                System.out.printf("%2d)", flight.getFlightID());
                System.out.print(" ");
                System.out.printf("%-6s", flight.getFlightNumber());
                System.out.print("  ");
                System.out.printf("%-8s", flight.getDepartureAirport().getCityName());
                System.out.print("  ");
                System.out.printf("%-8s", flight.getArrivalAirport().getCityName());
                System.out.print("  ");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                System.out.print(format.format(flight.getDepartureTime()));
                System.out.print("  ");
                System.out.print(format.format(flight.getArrivalTime()));
                System.out.print("  ");
                System.out.printf("%-11s", flight.getFlightStatus());
                System.out.println(" **");
            }
            System.out.println("**                                                                                 **");
            System.out.println("*************************************************************************************");
            System.out.println("*************************************************************************************");
            System.out.print("Please enter the number to select: ");
            int input = scanner.nextInt();
            if (input == 0) {
                QueryFlightByDepAndDest();
            }else{
                if (mainMethods.isLogin() && !mainMethods.isAdmin()){
                    if (Flight.getFlightByID(input).getFlightStatus() != FlightStatus.AVAILABLE) {
                        System.out.println("Sorry, the flight you select is not available at present.");
                        Wait(1);
                        QueryFlightByDepAndDest();
                    }else{
                        MakeOrder(input);
                    }
                }else if (mainMethods.isLogin() && mainMethods.isAdmin()){
                    System.out.println("Sorry, you are administrator so you cannot book flights.");
                    Wait(1);
                    QueryFlight();
                }else{
                    System.out.println("Sorry, you are not logged in, please login(1) or register(2) first.");
                    System.out.print("Please enter the number to select: ");
                    int input2 = scanner.nextInt();
                    switch (input2) {
                        case 1: Clear(); Login(); break;
                        case 2: Clear(); Register(); break;
                        default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); QueryFlightByDepAndDest(); break;
                    }
                }
            }
        }catch (ParseException ex) {
            System.out.print("The date format is incorrect!");
            Wait(1);
            Clear();
            QueryFlightByDepAndDest();
        }
    }
    public static void MakeOrder(int flightID) {
        Scanner scanner = new Scanner(System.in);
        Flight flight = Flight.getFlightByID(flightID);
        System.out.println("*****************************************************************************************************************");
        System.out.println("**                                                                                                             **");
        System.out.println("**                                    Welcome to the flight booking system!!                                   **");
        System.out.println("**                                                                                                             **");
        System.out.println("**                                     Please select the seat class to book                                    **");
        System.out.println("**                                      1) Economy Class    2) First Class                                     **");
        System.out.println("**                                              Enter 0 to go back                                             **");
        System.out.println("**                                                                                                             **");
        System.out.println("*****************************************************************************************************************");
        System.out.println("**                                                                                                             **");
        System.out.println("** ID.  Num.     Dep.      Dest.       Dep.Time          Dest.Time       Status     Economy Price  First Price **");
        System.out.print("** ");
        System.out.printf("%2d)", flight.getFlightID());
        System.out.print(" ");
        System.out.printf("%-6s", flight.getFlightNumber());
        System.out.print("  ");
        System.out.printf("%-8s", flight.getDepartureAirport().getCityName());
        System.out.print("  ");
        System.out.printf("%-8s", flight.getArrivalAirport().getCityName());
        System.out.print("  ");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.print(format.format(flight.getDepartureTime()));
        System.out.print("  ");
        System.out.print(format.format(flight.getArrivalTime()));
        System.out.print("  ");
        System.out.printf("%-11s", flight.getFlightStatus());
        System.out.print("     ");
        System.out.printf("RMB %-4d", flight.getEconomyPrice());
        System.out.print("      ");
        System.out.printf("RMB %-4d", flight.getFirstPrice());
        System.out.println("  **");
        System.out.println("**                                                                                                             **");
        System.out.println("*****************************************************************************************************************");
        System.out.println("*****************************************************************************************************************");
        System.out.print("Please select the seat class to book: ");
        int input = scanner.nextInt();
        try {
            try{
                switch (input) {
                    case 0: QueryFlight(); break;
                    case 1: PayOrder(mainMethods.reserveFlight(flightID, "Economy")); System.out.println("Order Successfully!!"); Wait(1); UserCenter(); break;
                    case 2: PayOrder(mainMethods.reserveFlight(flightID, "First")); System.out.println("Order Successfully!!"); Wait(1); UserCenter(); break;
                    default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); QueryFlight(); break;
                }
            }catch (StatusUnavailableException ex2) {
                System.out.println("Status Unavailable");
                Wait(1);
                QueryFlight();
            }
        }catch (PermissionDeniedException ex) {
            System.out.println("Permission Denied");
            Wait(1);
            Login();
        }
    }
    public static void PayOrder(int orderID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                          Pay                           **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        int price;
        if (Order.getOrderByID(orderID).getSeatClass() == SeatClass.Economy) {
            price = Order.getOrderByID(orderID).getFlight().getEconomyPrice();
        }else{
            price = Order.getOrderByID(orderID).getFlight().getFirstPrice();
        }
        System.out.println("You should pay RMB " + price);
        System.out.print("Enter Y to pay: ");
        String input = scanner.nextLine();
        if (input.equals("Y")) {
            Order.getOrderByID(orderID).processOrder();
        }else{
            Clear();
            PayOrder(orderID);
        }
    }

    public static void Login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                         Login                          **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (mainMethods.login(userName, password)) {
            System.out.println("Login Successfully!");
            Wait(1);
            Clear();
            if (!mainMethods.isAdmin()) {
                UserCenter();
            }else{
                AdminCenter();
            }
        }else{
            System.out.println("Login Failed!!");
            Wait(1);
            Clear();
            Login();
        }
    }

    public static void Register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                        Register                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Name (English): ");
        String realName = scanner.nextLine();
        System.out.print("Identity ID: ");
        String identityID = scanner.nextLine();
        if (mainMethods.register(identityID, userName, realName, password)) {
            System.out.println("Register Successfully!");
            Wait(1);
            Clear();
            Login();
        }else{
            System.out.println("Already Registered!");
            Wait(1);
            Clear();
            Login();
        }
    }

    public static void Logout() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                        Log Out                         **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        if (mainMethods.logout()) {
            System.out.println("Log Out Successfully!");
            Wait(1);
            Clear();
            main(null);
        }else{
            System.out.println("Log Out Failed!!");
            Wait(1);
            Clear();
            Login();
        }
    }

    public static void UserCenter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                       User Center                      **");
        System.out.println("**                                                        **");
        System.out.println("**            Please enter the number to select           **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**      1) Query Flight                                   **");
        System.out.println("**      2) My Orders                                      **");
        System.out.println("**      3) Change Password                                **");
        System.out.println("**      4) Log Out                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 1: Clear(); QueryFlight(); break;
            case 2: Clear(); Orders(); break;
            case 3: Clear(); ChangePassword(); break;
            case 4: Clear(); Logout(); break;
            default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); UserCenter(); break;
        }
    }

    public static void Orders() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders = mainMethods.listOrder();
        System.out.println("************************************************************************************************************************************");
        System.out.println("************************************************************************************************************************************");
        System.out.println("**                                                                                                                                **");
        System.out.println("**                                                Welcome to the flight booking system!!                                          **");
        System.out.println("**                                                                                                                                **");
        System.out.println("**                                                 Please enter the order ID to cancel                                            **");
        System.out.println("**                                                          Enter 0 to go back                                                    **");
        System.out.println("**                                                                                                                                **");
        System.out.println("************************************************************************************************************************************");
        System.out.println("**                                                                                                                                **");
        System.out.println("** ID  Flight Num.   Dep.      Dest.       Dep.Time          Dest.Time      Class   Seat Num.  Price       Create Date     Status **");
        for (Order order:orders) {
            System.out.print("** ");
            System.out.printf("%2d)", order.getOrderID());
            System.out.print("   ");
            System.out.printf("%-6s", order.getFlight().getFlightNumber());
            System.out.print("    ");
            System.out.printf("%-8s", order.getFlight().getDepartureAirport().getCityName());
            System.out.print("  ");
            System.out.printf("%-8s", order.getFlight().getArrivalAirport().getCityName());
            System.out.print("  ");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            System.out.print(format.format(order.getFlight().getDepartureTime()));
            System.out.print("  ");
            System.out.print(format.format(order.getFlight().getArrivalTime()));
            System.out.print("  ");
            System.out.printf("%-7s", order.getSeatClass());
            System.out.print("    ");
            System.out.printf("%-3s", order.getSeat());
            System.out.print("     ");
            if (order.getSeatClass() == SeatClass.Economy) {
                System.out.printf("RMB %-4d", order.getFlight().getEconomyPrice());
            }else{
                System.out.printf("RMB %-4d", order.getFlight().getFirstPrice());
            }
            System.out.print("   ");
            System.out.print(format.format(order.getCreateDate()));
            System.out.print("  ");
            System.out.printf("%-7s", order.getStatus());
            System.out.println("**");
        }
        System.out.println("**                                                                                                                                **");
        System.out.println("************************************************************************************************************************************");
        System.out.println("************************************************************************************************************************************");
        System.out.print("Please enter the order ID to cancel: ");
        int input = scanner.nextInt();
        if (input == 0) {
            UserCenter();
        }else{
            try {
                if (mainMethods.unsubscribeFlight(input)) {
                    System.out.println("Order Cancel Succeeded!! Refund will be returned to the original source.");
                    Wait(1);
                    Orders();
                }else{
                    System.out.println("Order Cancel Failed!! Please contact our support team.");
                    Wait(1);
                    Orders();
                }
            }catch (PermissionDeniedException ex) {
                System.out.println("Permission Denied!! Please login.");
                Wait(1);
                Login();
            }
        }
    }

    public static void AdminCenter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                      Admin Center                      **");
        System.out.println("**                                                        **");
        System.out.println("**           Please enter the number to select            **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**      1) Super Query                                    **");
        System.out.println("**      2) Create Flight                                  **");
        System.out.println("**      3) Edit Flight                                    **");
        System.out.println("**      4) Add Administrator                              **");
        System.out.println("**      5) Change Password                                **");
        System.out.println("**      6) Log Out                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 1: Clear(); SuperQuery(); break;
            case 2: Clear(); CreateFlight(); break;
            case 3: Clear(); EditFlight(); break;
            case 4: Clear(); AddAdmin(); break;
            case 5: Clear(); ChangePassword(); break;
            case 6: Clear(); Logout(); break;
            default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); AdminCenter(); break;
        }
    }

    public static void SuperQuery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                    Admin Super Query                   **");
        System.out.println("**                                                        **");
        System.out.println("**           Please enter the number to select            **");
        System.out.println("**                   Enter 0 to go back                   **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**      1) List Orders of a Flight                        **");
        System.out.println("**      2) Query Orders                                   **");
        System.out.println("**      3) Normal Query                                   **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 0: Clear(); AdminCenter(); break;
            case 1: Clear(); SuperQueryOrders(); break;
            case 2: Clear(); QueryOrdersByPassengerId(); break;
            case 3: Clear(); QueryFlight(); break;
            default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); SuperQuery();
        }
    }

    public static void SuperQueryOrders() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the flight number: ");
        String flightNum = scanner.nextLine();
        try{
            ArrayList<Order> orders = mainMethods.superQuery(flightNum);
            System.out.println("*************************************************************************************************************");
            System.out.println("*************************************************************************************************************");
            System.out.println("**                                                                                                         **");
            System.out.println("**                                    Welcome to the flight booking system!!                               **");
            System.out.println("**                                                                                                         **");
            System.out.println("**                                          List Orders of a Flight                                        **");
            System.out.println("**                                                                                                         **");
            System.out.println("**                                             Enter 0 to go back                                          **");
            System.out.println("**                                                                                                         **");
            System.out.println("*************************************************************************************************************");
            System.out.println("**                                                                                                         **");
            System.out.println("**   ID  Flight Num.    Pasgr.Name        Pasgr.IdtID       Class   Seat Num.     Create Date      Status  **");
            for (Order order:orders) {
                System.out.print("**  ");
                System.out.printf("%2d)", order.getOrderID());
                System.out.print("    ");
                System.out.printf("%-6s", order.getFlight().getFlightNumber());
                System.out.print("    ");
                System.out.printf("%16s", order.getPassenger().getRealName());
                System.out.print("   ");
                System.out.printf("%18s", order.getPassenger().getIdentityID());
                System.out.print("  ");
                System.out.printf("%-7s", order.getSeatClass());
                System.out.print("     ");
                System.out.printf("%-3s", order.getSeat());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.print("    ");
                System.out.print(format.format(order.getCreateDate()));
                System.out.print("  ");
                System.out.printf("%-7s", order.getStatus());
                System.out.println(" **");
            }
            System.out.println("**                                                                                                         **");
            System.out.println("*************************************************************************************************************");
            System.out.println("*************************************************************************************************************");
            System.out.print("Enter 0 to go back: ");
            int input = scanner.nextInt();
            if (input == 0) {
                Clear();
                SuperQuery();
            }
        }catch (PermissionDeniedException ex) {
            System.out.println("Permission Denied!!");
            Wait(1);
            Login();
        }
    }

    public static void QueryOrdersByPassengerId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the passenger ID: ");
        int passengerID = scanner.nextInt();
        try{
            ArrayList<Order> orders = mainMethods.superQuery(passengerID);
            System.out.println("*************************************************************************************************************");
            System.out.println("*************************************************************************************************************");
            System.out.println("**                                                                                                         **");
            System.out.println("**                                    Welcome to the flight booking system!!                               **");
            System.out.println("**                                                                                                         **");
            System.out.println("**                                         Query Orders by Passenger ID                                    **");
            System.out.println("**                                                                                                         **");
            System.out.println("**                                              Enter 0 to go back                                         **");
            System.out.println("**                                                                                                         **");
            System.out.println("*************************************************************************************************************");
            System.out.println("**                                                                                                         **");
            System.out.println("**   ID  Flight Num.    Pasgr.Name        Pasgr.IdtID       Class   Seat Num.     Create Date      Status  **");
            for (Order order:orders) {
                System.out.print("**  ");
                System.out.printf("%2d)", order.getOrderID());
                System.out.print("    ");
                System.out.printf("%-6s", order.getFlight().getFlightNumber());
                System.out.print("    ");
                System.out.printf("%16s", order.getPassenger().getRealName());
                System.out.print("   ");
                System.out.printf("%18s", order.getPassenger().getIdentityID());
                System.out.print("  ");
                System.out.printf("%-7s", order.getSeatClass());
                System.out.print("     ");
                System.out.printf("%-3s", order.getSeat());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.print("    ");
                System.out.print(format.format(order.getCreateDate()));
                System.out.print("  ");
                System.out.printf("%-7s", order.getStatus());
                System.out.println(" **");
            }
            System.out.println("**                                                                                                         **");
            System.out.println("*************************************************************************************************************");
            System.out.println("*************************************************************************************************************");
            System.out.print("Enter 0 to go back: ");
            int input = scanner.nextInt();
            if (input == 0) {
                Clear();
                SuperQuery();
            }
        }catch (PermissionDeniedException ex) {
            System.out.println("Permission Denied!!");
            Wait(1);
            Login();
        }
    }

    public static void CreateFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                     Create Flight                      **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the flight number: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Please enter the departure time (Format: yyyy-MM-dd HH:mm): ");
        String depTimeStr = scanner.nextLine();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date depTime = new Date();
        Date destTime = new Date();
        try {
            depTime = format.parse(depTimeStr);
        }catch (ParseException ex) {
            System.out.print("Format Error!");
            Clear();
            Wait(1);
            CreateFlight();
        }
        System.out.print("Please enter the arrival time (Format: yyyy-MM-dd HH:mm): ");
        String destTimeStr = scanner.nextLine();
        try {
            destTime = format.parse(destTimeStr);
        }catch (ParseException ex) {
            System.out.print("Format Error!");
            Clear();
            Wait(1);
            CreateFlight();
        }
        System.out.print("Please enter the departure airport name: ");
        String depAirport = scanner.nextLine();
        System.out.print("Please enter the destination airport name: ");
        String destAirport = scanner.nextLine();
        System.out.print("Please enter the economy class price: ");
        int ecoPrice = scanner.nextInt();
        System.out.print("Please enter the first class price: ");
        int firstPrice = scanner.nextInt();
        try{
            if (mainMethods.createFlight(flightNumber,depTime,destTime,depAirport,destAirport,ecoPrice,firstPrice)) {
                System.out.println("Create Successfully!!");
                Wait(1);
                Clear();
                AdminCenter();
            }
        }catch (PermissionDeniedException ex) {
            System.out.println("Permission Denied!!");
            Wait(1);
            Clear();
            Login();
        }
    }

    public static void EditFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                      Edit Flight                       **");
        System.out.println("**                                                        **");
        System.out.println("**           Please enter the number to select            **");
        System.out.println("**                   Enter 0 to go back                   **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**      1) Update Flight Information                      **");
        System.out.println("**      2) Delete Flight                                  **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 0: Clear(); AdminCenter(); break;
            case 1: Clear(); UpdateFlight(); break;
            case 2: Clear(); DeleteFlight(); break;
            default: System.out.println("Incorrect Input!!"); Wait(1); Clear(); EditFlight(); break;
        }
    }

    public static void UpdateFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                      Edit Flight                       **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("Please enter the flight ID: ");
        int flightID = scanner.nextInt();
        scanner.nextLine();
        if (Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.UNPUBLISHED) {
            System.out.print("Please enter the new flight number: ");
            String newNum = scanner.nextLine();
            System.out.print("Please enter the new departure time (Format: yyyy-MM-dd HH:mm): ");
            String newDepTimeStr = scanner.nextLine();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDepTime = new Date();
            Date newDestTime = new Date();
            try {
                newDepTime = format.parse(newDepTimeStr);
            }catch (ParseException ex) {
                System.out.print("Format Error!");
                Clear();
                Wait(1);
                CreateFlight();
            }
            System.out.print("Please enter the new arrival time (Format: yyyy-MM-dd HH:mm): ");
            String newDestTimeStr = scanner.nextLine();
            try {
                newDestTime = format.parse(newDestTimeStr);
            }catch (ParseException ex) {
                System.out.print("Format Error!");
                Clear();
                Wait(1);
                CreateFlight();
            }
            System.out.print("Please enter the new departure airport name: ");
            String newDepAirport = scanner.nextLine();
            System.out.print("Please enter the new destination airport name: ");
            String newDestAirport = scanner.nextLine();
            System.out.print("Please enter the new economy class price: ");
            int newEcoPrice = scanner.nextInt();
            System.out.print("Please enter the new first class price: ");
            int newFirstPrice = scanner.nextInt();
            if (mainMethods.updateFlight(flightID,newNum,newDepTime,newDestTime,newDepAirport,newDestAirport,newEcoPrice,newFirstPrice)) {
                System.out.println("Update Successfully!!");
                Wait(1);
                Clear();
                AdminCenter();
            }else{
                System.out.println("Update Failed!!");
                Wait(1);
                Clear();
                EditFlight();
            }
        }else if (Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.FULL || Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.AVAILABLE) {
            System.out.print("Please enter the new economy class price: ");
            int newEcoPrice = scanner.nextInt();
            System.out.print("Please enter the new first class price: ");
            int newFirstPrice = scanner.nextInt();
            if (mainMethods.updateFlight(flightID,newEcoPrice,newFirstPrice)) {
                System.out.println("Update Successfully!!");
                Wait(1);
                Clear();
                AdminCenter();
            }else{
                System.out.println("Update Failed!!");
                Wait(1);
                Clear();
                EditFlight();
            }
        }else{
            System.out.println("Status Unavailable!!");
            Wait(1);
            Clear();
            EditFlight();
        }
    }

    public static void DeleteFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                     Delete Flight                      **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("Please enter the flight ID: ");
        int flightID = scanner.nextInt();
        if (Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.UNPUBLISHED || Flight.getFlightByID(flightID).getFlightStatus() == FlightStatus.TERMINATE) {
            try {
                try {
                    mainMethods.deleteFlight(flightID);
                }catch (StatusUnavailableException ex) {
                    System.out.println("Status Unavailable!!");
                    Wait(1);
                    Clear();
                    AdminCenter();
                }
            }catch (PermissionDeniedException ex) {
                System.out.println("Permission Denied!!");
                Wait(1);
                Clear();
                Login();
            }
        }else{
            System.out.println("Status Unavailable!!");
            Wait(1);
            Clear();
            AdminCenter();
        }
    }

    public static void ChangePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                     Change Password                    **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("Please enter the new password: ");
        String newPass = scanner.nextLine();
        try{
            mainMethods.changePassword(newPass);
        }catch (PermissionDeniedException ex) {
            System.out.println("Permission Denied!!");
            Wait(1);
            Clear();
            Login();
        }
        System.out.println("Password Changed Successfully!");
        Wait(1);
        if (mainMethods.isAdmin()) {
            AdminCenter();
        }else{
            UserCenter();
        }
    }

    public static void AddAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                        Add Admin                       **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("Please enter the username for new administrator: ");
        String username = scanner.nextLine();
        System.out.println("Please enter the password for new administrator: ");
        String password = scanner.nextLine();
        try{
            mainMethods.addAdmin(username,password);
        }catch (PermissionDeniedException ex) {
            System.out.println("Permission Denied!!");
            Wait(1);
            Clear();
            Login();
        }
        System.out.println("Administrator Added Successfully!");
        Wait(1);
        AdminCenter();
    }

    public static void Initialize() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**         Welcome to the flight booking system!!         **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("**                 System Initializing...                 **");
        System.out.println("**                                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the super administrator password: "); // SuperPass
        String password = scanner.nextLine();
        if (User.hashPass(password).equals("b2c1a10e9f29a053bc06279e30a260fd2dd75374")) {
            Data.initialize();
            System.out.println("Initialize Finished!");
            Wait(1);
            Clear();
            main(null);
        }else{
            System.out.println("Permission Denied!!");
            Wait(1);
            Clear();
            main(null);
        }
    }

    public static void Wait(int time) {
        try{
            Thread.sleep(1000 * time);
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    public static void Clear() {
        for (int i = 1; i <= 30; i++) {
            System.out.println();
        }
    }
}
