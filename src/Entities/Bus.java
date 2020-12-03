package Entities;

public class Bus {
    private String busId;
    private String busModel;
    private int seats;
    private double priceEachSeat;

    public Bus(String busId, String busModel, int seats, double priceEachSeat) {
        this.busId = busId;
        this.busModel = busModel;
        this.seats = seats;
        this.priceEachSeat = priceEachSeat;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getPriceEachSeat() {
        return priceEachSeat;
    }

    public void setPriceEachSeat(double priceEachSeat) {
        this.priceEachSeat = priceEachSeat;
    }


    @Override
    public String toString() {
        return "Bus{" +
                "busId='" + busId + '\'' +
                ", busModel='" + busModel + '\'' +
                ", seats=" + seats +
                ", priceEachSeat=" + priceEachSeat +
                '}';
    }
}
