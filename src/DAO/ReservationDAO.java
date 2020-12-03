package DAO;

import Entities.Reservation;
import com.sun.java.browser.plugin2.liveconnect.v1.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection con;

    public ReservationDAO(Connection con) {
        this.con = con;
    }
    public List<Reservation> reservationList(){
        List<Reservation> reservationList = new ArrayList<>();

        try{
            String query = "Select * from reservation";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);
            while(rst.next()){
                int sl = rst.getInt("SL");
                int scheduleNo = rst.getInt("schedule_no");
                String phoneNo = rst.getString("passenger_phone_no");
                String seatNo = rst.getString("seat_No");
                Time bookingTime = rst.getTime("booking_Time");
                Reservation reservation = new Reservation(scheduleNo,phoneNo,seatNo);
                reservationList.add(reservation);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return reservationList;
    }

    public List<Reservation> getReservationListBySchedule(int scheduleNum){
        List<Reservation> reservationList = new ArrayList<>();

        try{
            String query = "Select * from reservation where schedule_no = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,scheduleNum);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                int sl = rst.getInt("SL");
                int scheduleNo = rst.getInt("schedule_no");
                String phoneNo = rst.getString("passenger_phone_no");
                String seatNo = rst.getString("seat_No");
                Time bookingTime = rst.getTime("booking_Time");
                Reservation reservation = new Reservation(scheduleNo,phoneNo,seatNo);
                reservationList.add(reservation);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return reservationList;
    }public List<Reservation> getReservationListByPhone(String phoneNumber){
        List<Reservation> reservationList = new ArrayList<>();

        try{
            String query = "Select * from reservation where passenger_phone_no = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,phoneNumber);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                int sl = rst.getInt("SL");
                int scheduleNo = rst.getInt("schedule_no");
                String phoneNo = rst.getString("passenger_phone_no");
                String seatNo = rst.getString("seat_No");
                Time bookingTime = rst.getTime("booking_Time");
                Reservation reservation = new Reservation(scheduleNo,phoneNo,seatNo);
                reservationList.add(reservation);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return reservationList;
    }

    public List<Reservation> getAllReservationByScheduleAndPhone(int shceduleNum, String phoneNum){
        List<Reservation> reservationList = new ArrayList<>();
        try{
            String query = "Select * from reservation where schedule_no =? and passenger_phone_no=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,shceduleNum);
            pst.setString(2,phoneNum);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                int sl = rst.getInt("SL");
                int scheduleNo = rst.getInt("schedule_no");
                String phoneNo = rst.getString("passenger_phone_no");
                String seatNo = rst.getString("seat_No");
                Time bookingTime = rst.getTime("booking_Time");
                Reservation reservation = new Reservation(scheduleNo,phoneNo,seatNo);
                reservationList.add(reservation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return reservationList;
    }

    public boolean reserveSeat(Reservation reservation){
        boolean isSaved = false;
        try{
            String query = "INSERT INTO reservation (schedule_no, passenger_phone_no, seat_no)\n" +
                    "SELECT * FROM (SELECT ?, ?, ?) AS tmp\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT seat_no FROM reservation WHERE schedule_no = ?  and seat_no = ? \n" +
                    ") LIMIT 1;";
//            String query ="insert into reservation(schedule_no,passenger_phone_no,seat_no) values(?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,reservation.getSchedule_No());
            pst.setString(2,reservation.getPhoneNO());
            pst.setString(3,reservation.getSeatName());
            pst.setInt(4,reservation.getSchedule_No());
            pst.setString(5,reservation.getSeatName());
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
            if(count>0) isSaved = true;

        }catch ( Exception e){
            e.printStackTrace();
        }
        return isSaved;
    }

    public  boolean cancelReservation(Reservation reservation){
        boolean isDeleted = false;
        try{
            String query = " Delete from reservation where  schedule_no=? and PASSENGER_PHONE_NO=? and SEAT_NO=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,reservation.getSchedule_No());
            pst.setString(2,reservation.getPhoneNO());
            pst.setString(3,reservation.getSeatName());
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
            isDeleted = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  isDeleted;
    }

}
