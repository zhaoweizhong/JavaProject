package main;

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

public class Main {
    public static MainMethods mainMethods = new MainMethods();
    public static void main(String[] args) {
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
            if (mainMethods.isLogin()) {
                if (Flight.getFlightByID(input2).getFlightStatus() != FlightStatus.AVAILABLE) {
                    System.out.println("Sorry, the flight you select is not available at present.");
                    Wait(1);
                    QueryFlightByFlightNum();
                }else{
                    MakeOrder(input2);
                }
            } else {
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
                if (mainMethods.isLogin()){
                    if (Flight.getFlightByID(input).getFlightStatus() != FlightStatus.AVAILABLE) {
                        System.out.println("Sorry, the flight you select is not available at present.");
                        Wait(1);
                        QueryFlightByDepAndDest();
                    }else{
                        MakeOrder(input);
                    }
                }else{
                    System.out.println("Sorry, you are not logged in, please login(1) or register(2) first.");
                    System.out.print("Please enter the number to select: ");
                    int input2 = scanner.nextInt();
                    switch (input2) {
                        case 1: Clear(); Login(); break;
                        case 2: Clear(); Register(); break;
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
                    case 1: mainMethods.reserveFlight(flightID, "Economy"); System.out.println("Order Successful!!"); Wait(1); UserCenter(); break;
                    case 2: mainMethods.reserveFlight(flightID, "First"); System.out.println("Order Successful!!"); Wait(1); UserCenter(); break;
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
            System.out.println("Login Successful!");
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
            System.out.println("Register Successful!");
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
            System.out.println("Log Out Successful!");
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
        System.out.println("**      3) Log Out                                        **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 1: Clear(); QueryFlight(); break;
            case 2: Clear(); Orders(); break;
            case 3: Clear(); Logout(); break;
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
            System.out.println(" **");
        }
        System.out.println("**                                                                                                                                **");
        System.out.println("************************************************************************************************************************************");
        System.out.println("************************************************************************************************************************************");
        System.out.print("Please enter the order ID to cancel: ");
        int input = scanner.nextInt();
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
        System.out.println("**      1) Query Flight                                   **");
        System.out.println("**      2) Create Flight                                  **");
        System.out.println("**      3) Edit Flight                                    **");
        System.out.println("**      4) User Manage                                    **");
        System.out.println("**      5) Add Admin                                      **");
        System.out.println("**                                                        **");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.print("Please enter the number to select: ");
        int input = scanner.nextInt();
        switch (input) {
            case 1: Clear(); /* TODO: Super Query */
            case 2: Clear(); /* TODO: Create Flight */
            case 3: Clear(); /* TODO: Edit Flight */
            case 4: Clear(); /* TODO: User Manage */
            case 5: Clear(); /* TODO: Add Admin */
        }
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
