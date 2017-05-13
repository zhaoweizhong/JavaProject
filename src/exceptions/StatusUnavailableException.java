package exceptions;

import flight.FlightStatus;

public class StatusUnavailableException extends Exception {

    private FlightStatus status;

    public StatusUnavailableException(FlightStatus status) {
        this.status = status;
    }

    public StatusUnavailableException() {
        status = null;
    }

    public FlightStatus getStatusMessage() {
        return status;
    }
}
