package main;

import data.Data;
import flight.Airport;
import flight.Flight;
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
        System.out.println();
        System.out.print("Please select the Departure Airport: ");
        int destAirportNum = scanner.nextInt();
        System.out.println();
        System.out.print("Please enter the departure date (Format: yyyy-MM-dd): ");
        String depDateStr = scanner.nextLine();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date depDate = format1.parse(depDateStr);
            ArrayList<Flight> flights = mainMethods.queryFlight(Airport.getAirportByID(depAirportNum).getAirportName(), Airport.getAirportByID(destAirportNum).getAirportName(), depDate);
            System.out.println("******************************************************************************");
            System.out.println("******************************************************************************");
            System.out.println("**                                                                          **");
            System.out.println("**                  Welcome to the flight booking system!!                  **");
            System.out.println("**                                                                          **");
            System.out.println("**                    Please enter the number to select                     **");
            System.out.println("**                           Enter 0 to go back                             **");
            System.out.println("**                                                                          **");
            System.out.println("******************************************************************************");
            System.out.println("**                                                                          **");
            System.out.println("** ID.  Num.     Dep.      Dest.         Dep.Time            Dest.Time      **");
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
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.printf("%-8s", format.format(flight.getDepartureTime()));
                System.out.print("  ");
                System.out.printf("%-8s", format.format(flight.getArrivalTime()));
                System.out.println(" **");
            }
            System.out.println("**                                                                          **");
            System.out.println("******************************************************************************");
            System.out.println("******************************************************************************");

        }catch (ParseException ex) {
            System.out.print("The date format is incorrect!");
            Wait(1);
            Clear();
            QueryFlightByDepAndDest();
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
