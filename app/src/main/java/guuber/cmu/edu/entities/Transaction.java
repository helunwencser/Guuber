package guuber.cmu.edu.entities;

/**
 * Created by lunwenh on 4/8/2016.
 */

/**
 * This class defines one transaction
 * */
public class Transaction {
    public Transaction(int transactionID, String driver, String passenger, String startTime, String endTime, String startLocation, String endLocation, int cost) {
        this.transactionID = transactionID;
        this.driver = driver;
        this.passenger = passenger;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    private int transactionID;
    private String driver;
    private String passenger;
    private String startTime;
    private String endTime;
    private String startLocation;
    private String endLocation;
    private int cost;

    public Transaction(
            String driver,
            String passenger,
            String startTime,
            String endTime,
            String startLocation,
            String endLocation,
            int cost
    ) {
        this.driver = driver;
        this.passenger = passenger;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
    }
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
