package Entities;

public class SeatsView {
    private int scheduleNo;
    private String SeatName;
    private String Booked;

    public SeatsView(int scheduleNo, String seatName, String Booked) {
        this.scheduleNo = scheduleNo;
        SeatName = seatName;
        this.Booked = Booked;
    }

    public int getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getSeatName() {
        return SeatName;
    }

    public void setSeatName(String seatName) {
        SeatName = seatName;
    }

    public String isBooked() {
        return Booked;
    }

    public void setBooked(String booked) {
        Booked = booked;
    }

    @Override
    public String toString() {
        return "SeatsView{" +
                "scheduleNo=" + scheduleNo +
                ", SeatName='" + SeatName + '\'' +
                ", isBooked=" + Booked +
                '}';
    }
}
