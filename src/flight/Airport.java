package flight;

public class Airport {
    private static int airportQuantity = 0;
    private int airportID;
    private String airportName;
    private String cityName;

    public Airport(String airportName, String cityName) {
        this.airportName = airportName;
        this.cityName = cityName;
        airportQuantity++;
        airportID = airportQuantity;
    }

    public Airport(int airportQuantity, int airportID, String airportName, String cityName) {
        this.airportName = airportName;
        this.cityName = cityName;
        Airport.airportQuantity = airportQuantity;
        this.airportID = airportID;
    }

    public int getAirportID() {
        return airportID;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
