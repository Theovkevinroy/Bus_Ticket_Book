package DAO;

import Entities.Schedule;
import Helper.ConnectionProvider;

import javax.swing.plaf.nimbus.State;
import java.lang.ref.PhantomReference;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private Connection con;

    public ScheduleDAO(Connection con) {
        this.con = con;
    }

    public List<Schedule> getAllScheduleList(){
        List<Schedule> scheduleList = new ArrayList<>();
        try{
            String query = "Select * from Schedule";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);
            while(rst.next()){
                int scheduleNo = rst.getInt("ScheduleNo");
                String busId = rst.getString("busId");
                String driverId = rst.getString("driverId");
                String routeId = rst.getString("routeId");
                Time fromTime = rst.getTime("fromTime");
                Time toTime = rst.getTime("toTime");
                Date date = rst.getDate("date");
                double priceEachSeat = rst.getDouble("priceEachSeat");
                Schedule schedule = new Schedule(scheduleNo,busId,driverId,routeId,fromTime,toTime,date,priceEachSeat);
                scheduleList.add(schedule);
                System.out.println(schedule);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return scheduleList;
    }

    public boolean createSchedule(Schedule schedule){
        boolean isSaved = false;
        try{
            String query = "Insert into schedule(scheduleNo,busId,routeId,driverId,fromTime,toTime,Date,priceEachSeat)" +
                    " values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,schedule.getScheduleNo());
            pst.setString(2,schedule.getBusId());
            pst.setString(3,schedule.getRouteId());
            pst.setString(4,schedule.getDriverId());
            pst.setTime(5,schedule.getFromTime());
            pst.setTime(6,schedule.getToTime());
            pst.setDate(7,schedule.getDate());
            pst.setDouble(8,schedule.getPriceEachSeat());
            int count = pst.executeUpdate();
            System.out.println(count +" rows effected");
            isSaved = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  isSaved;
    }

    public boolean updateSchedule(Schedule schedule){
        boolean isUpdated = false;
        try{
            String query = "Update schedule set busId=?, driverId=?, routeId=?, fromTime=?, toTime=?, priceEachSeat=?, date=? where scheduleNo=?";
            PreparedStatement pst  = con.prepareStatement(query);
            pst.setString(1,schedule.getBusId());
            pst.setString(2,schedule.getDriverId());
            pst.setString(3,schedule.getRouteId());
            pst.setTime(4,schedule.getFromTime());
            pst.setTime(5,schedule.getToTime());
            pst.setDouble(6,schedule.getPriceEachSeat());
            pst.setDate(7,schedule.getDate());
            pst.setInt(8,schedule.getScheduleNo());
            int count  = pst.executeUpdate();
            if(count>0) isUpdated= true;
            System.out.println(count+" rows effected");

        }catch (Exception e){
            e.printStackTrace();
        }

        return isUpdated;
    }

    public boolean deleteSchedule(int scheduleNo){
        boolean isDeleted = false;
        try{
            String query = "Delete from schedule where scheduleNo ="+scheduleNo;
            Statement st = con.createStatement();
            int count = st.executeUpdate(query);
            if(count>0) isDeleted = true;
            System.out.println(count+" rows effected");


        }catch (Exception e){
            e.printStackTrace();
        }

        return isDeleted;
    }

    public List<Schedule> getScheduleBySearch(String fromPlace, String toPlace, Date date){
        List<Schedule> scheduleList = new ArrayList<>();
        try{
            if(date!=null && fromPlace!=null && toPlace!=null) {
                System.out.println("first if");
                String queryAll = "SELECT *  FROM SCHEDULE\n" +
                    "inner JOIN route\n" +
                    "ON route.routeId = schedule.routeId\n" +
                    "where  fromPlace = ? and toPlace=? and date=?  ";
                PreparedStatement pstAll = con.prepareStatement(queryAll);
                pstAll.setString(1, fromPlace);
                pstAll.setString(2, toPlace);
                pstAll.setDate(3, date);
                ResultSet rstAll = pstAll.executeQuery();
                while (rstAll.next()) {
                    int scheduleNo = rstAll.getInt("ScheduleNo");
                    String busId = rstAll.getString("busId");
                    String driverId = rstAll.getString("driverId");
                    String routeId = rstAll.getString("routeId");
                    Time fromTime = rstAll.getTime("fromTime");
                    Time toTime = rstAll.getTime("toTime");
                    Date myDate = rstAll.getDate("date");
                    double priceEachSeat = rstAll.getDouble("priceEachSeat");
                    Schedule schedule = new Schedule(scheduleNo, busId, driverId, routeId, fromTime, toTime, myDate, priceEachSeat);
                    scheduleList.add(schedule);
                }
            }else if(date!=null && fromPlace==null && toPlace==null) {
                System.out.println("second if");
                String queryAll = "SELECT *  FROM SCHEDULE\n" +
                    "inner JOIN route\n" +
                    "ON route.routeId = schedule.routeId\n" +
                    "where  " +
                      // "fromPlace = ? and toPlace=? " +
                        "date=?  ";
                PreparedStatement pstAll = con.prepareStatement(queryAll);
              //  pstAll.setString(1, fromPlace);
               // pstAll.setString(2, toPlace);
                pstAll.setDate(1, date);
                ResultSet rstAll = pstAll.executeQuery();
                while (rstAll.next()) {
                    int scheduleNo = rstAll.getInt("ScheduleNo");
                    String busId = rstAll.getString("busId");
                    String driverId = rstAll.getString("driverId");
                    String routeId = rstAll.getString("routeId");
                    Time fromTime = rstAll.getTime("fromTime");
                    Time toTime = rstAll.getTime("toTime");
                    Date myDate = rstAll.getDate("date");
                    double priceEachSeat = rstAll.getDouble("priceEachSeat");
                    Schedule schedule = new Schedule(scheduleNo, busId, driverId, routeId, fromTime, toTime, myDate, priceEachSeat);
                    scheduleList.add(schedule);
                }
            }
            else if(date==null){
                System.out.println("third if");
                String queryRoute = "SELECT * FROM SCHEDULE\n" +
                    "inner JOIN route\n" +
                    "ON route.routeId = schedule.routeId\n" +
                    "where fromPlace=? and toPlace=?";
                PreparedStatement pstRoute = con.prepareStatement(queryRoute);
                pstRoute.setString(1,fromPlace);
                pstRoute.setString(2,toPlace);
                ResultSet rstRoute = pstRoute.executeQuery();
                while (rstRoute.next()){
                    int scheduleNo = rstRoute.getInt("ScheduleNo");
                    String busId = rstRoute.getString("busId");
                    String driverId = rstRoute.getString("driverId");
                    String routeId = rstRoute.getString("routeId");
                    Time fromTime = rstRoute.getTime("fromTime");
                    Time toTime = rstRoute.getTime("toTime");
                    Date myDate = rstRoute.getDate("date");
                    double priceEachSeat = rstRoute.getDouble("priceEachSeat");
                    Schedule schedule = new Schedule(scheduleNo, busId, driverId, routeId, fromTime, toTime, myDate, priceEachSeat);
                    scheduleList.add(schedule);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  scheduleList;
    }




}
