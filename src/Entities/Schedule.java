package Entities;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
    private  int scheduleNo;
    private String busId;
    private String driverId;
    private String routeId;
    private Time fromTime;
    private Time toTime;
    private Date date;
   // private int seatAvailable;
    private  double priceEachSeat;

    public Schedule(int scheduleNo, String busId, String driverId, String routeId, Time fromTime, Time toTime, Date date, double priceEachSeat) {
        this.scheduleNo = scheduleNo;
        this.busId = busId;
        this.driverId = driverId;
        this.routeId = routeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.date = date;
        this.priceEachSeat = priceEachSeat;
    }

    public int getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    public Time getToTime() {
        return toTime;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPriceEachSeat() {
        return priceEachSeat;
    }

    public void setPriceEachSeat(double priceEachSeat) {
        this.priceEachSeat = priceEachSeat;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleNo=" + scheduleNo +
                ", busId='" + busId + '\'' +
                ", driverId='" + driverId + '\'' +
                ", routeId='" + routeId + '\'' +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", date=" + date +
                ", priceEachSeat=" + priceEachSeat +
                '}';
    }
}
