package Entities;

public class SeatAvailabality {
    private  int scheduleNo;
    private int booked;
    private int available;

    public SeatAvailabality(int scheduleNo, int booked, int available) {
        this.scheduleNo = scheduleNo;
        this.booked = booked;
        this.available = available;
    }

    public int getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailablel(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "SeatAvailabality{" +
                "scheduleNo=" + scheduleNo +
                ", booked=" + booked +
                ", available=" + available +
                '}';
    }
}
