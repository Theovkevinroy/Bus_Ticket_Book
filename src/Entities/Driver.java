package Entities;

import java.time.LocalDate;

public class Driver {
    private String driverId;
    private String driverName;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNo;

    public Driver(String driverId, String driverName, String address, String gender, LocalDate dateOfBirth, String phoneNo) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNo = phoneNo;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
