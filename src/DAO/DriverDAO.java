package DAO;

import Entities.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    private Connection con;

    public DriverDAO(Connection con) {
        this.con = con;
    }

    public List<Driver> getALlDriverList(){
        List<Driver> driverList = new ArrayList<>();
        try{
            String query = "select * from driver";
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while(resultSet.next()){
                int i = resultSet.getInt("SL");
                String driverId = resultSet.getString("DriverId");
                String driverName = resultSet.getString("DriverName");
                String address = resultSet.getString("Address");
                String gender = resultSet.getString("Gender");
                String dob = resultSet.getString("DateOfBirth");
                LocalDate date = LocalDate.parse(dob);
                String phoneNo = resultSet.getString("PhoneNo");
                Driver driver = new Driver(driverId,driverName,address,gender,date,phoneNo);
                driverList.add(driver);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  driverList;
    }

    public boolean createDriver(Driver driver){
        boolean isSaved = false;
        try{
            String query = "Insert into driver(driverId,driverName,address,gender,dateOfBirth,phoneNO) values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,driver.getDriverId());
            pst.setString(2,driver.getDriverName());
            pst.setString(3,driver.getAddress());
            pst.setString(4,driver.getGender());
            pst.setDate(5, Date.valueOf(driver.getDateOfBirth()));
            pst.setString(6,driver.getPhoneNo());
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
            isSaved = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return isSaved;
    }
    public boolean updateDriver(Driver driver){
        boolean isSaved = false;
        try{
            String query1 = "Select sl from driver where driverId =?";
            PreparedStatement pst1 = con.prepareStatement(query1);
            pst1.setString(1,driver.getDriverId());
            ResultSet rst1 = pst1.executeQuery();
            int sl = -1;
            while (rst1.next()){
                sl = rst1.getInt("sl");
            }
            String query = "Update driver set driverId = ?, driverName = ?, address=?, gender=?, dateOfBirth=?, phoneNo=?  where sl = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,driver.getDriverId());
            pst.setString(2,driver.getDriverName());
            pst.setString(3,driver.getAddress());
            pst.setString(4,driver.getGender());
            pst.setDate(5, Date.valueOf(driver.getDateOfBirth()));
            pst.setString(6,driver.getPhoneNo());
            pst.setInt(7,sl);
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
           if(count>0) isSaved = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return isSaved;
    }
    public  boolean deleteDriver(String driverId){
        boolean isDeleted = false;
        try{
            String query = "Delete from driver where driverId =?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,driverId);
            int count = pst.executeUpdate();
            if(count>0) isDeleted = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return isDeleted;
    }

}
