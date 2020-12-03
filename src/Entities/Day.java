package Entities;

public class Day {
    private  String day;

    public Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Day{" +
                "day='" + day + '\'' +
                '}';
    }
}
