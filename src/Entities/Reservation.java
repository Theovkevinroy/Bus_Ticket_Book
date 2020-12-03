package Entities;

public class Reservation {
    private int schedule_No;
    private String phoneNO;
    private String seatName;

    public Reservation(int schedule_No, String phoneNO, String seatName) {
        this.schedule_No = schedule_No;
        this.phoneNO = phoneNO;
        this.seatName = seatName;
    }

    public int getSchedule_No() {
        return schedule_No;
    }

    public void setSchedule_No(int schedule_No) {
        this.schedule_No = schedule_No;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "schedule_No=" + schedule_No +
                ", phoneNO='" + phoneNO + '\'' +
                ", seatName='" + seatName + '\'' +
                '}';
    }
}
