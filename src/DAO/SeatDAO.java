package DAO;

import Entities.Reservation;
import Entities.Seats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    private Connection con;

    public SeatDAO(Connection con) {
        this.con = con;
    }

    public List<Seats> getAllSeats() {
        List<Seats> seatsList = new ArrayList<>();
        try {
            String query = "Select * from Seats";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);
            while(rst.next()){
                int scheduleNo = Integer.parseInt(rst.getString("Schedule_No"));
                String busId = rst.getString("busId");
                boolean a1 = rst.getBoolean("A1");
                boolean a2 = rst.getBoolean("A2");
                boolean a3 = rst.getBoolean("A3");
                boolean a4 = rst.getBoolean("A4");
                boolean b1 = rst.getBoolean("B1");
                boolean b2 = rst.getBoolean("B2");
                boolean b3 = rst.getBoolean("B3");
                boolean b4 = rst.getBoolean("B4");
                boolean c1 = rst.getBoolean("C1");
                boolean c2 = rst.getBoolean("C2");
                boolean c3 = rst.getBoolean("C3");
                boolean c4 = rst.getBoolean("C4");
                boolean d1 = rst.getBoolean("D1");
                boolean d2 = rst.getBoolean("D2");
                boolean d3 = rst.getBoolean("D3");
                boolean d4 = rst.getBoolean("D4");
                boolean e1 = rst.getBoolean("E1");
                boolean e2 = rst.getBoolean("E2");
                boolean e3 = rst.getBoolean("E3");
                boolean e4 = rst.getBoolean("E4");
                Seats seats = new Seats(scheduleNo,busId,a1,a2,a3,a4,b1,b2,b3,b4,
                        c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4);
                seatsList.add(seats);
                System.out.println(seats);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatsList;
    }
    public Seats getAllSeatsBySheduleNo(int scheduleNo) {
        //List<Seats> seatsList = new ArrayList<>();
        Seats seats = null;
        try {
            String query = "Select * from Seats where schedule_No=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,scheduleNo);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                int myScheduleNo = Integer.parseInt(rst.getString("Schedule_No"));
                String busId = rst.getString("busId");
                boolean a1 = rst.getBoolean("A1");
                boolean a2 = rst.getBoolean("A2");
                boolean a3 = rst.getBoolean("A3");
                boolean a4 = rst.getBoolean("A4");
                boolean b1 = rst.getBoolean("B1");
                boolean b2 = rst.getBoolean("B2");
                boolean b3 = rst.getBoolean("B3");
                boolean b4 = rst.getBoolean("B4");
                boolean c1 = rst.getBoolean("C1");
                boolean c2 = rst.getBoolean("C2");
                boolean c3 = rst.getBoolean("C3");
                boolean c4 = rst.getBoolean("C4");
                boolean d1 = rst.getBoolean("D1");
                boolean d2 = rst.getBoolean("D2");
                boolean d3 = rst.getBoolean("D3");
                boolean d4 = rst.getBoolean("D4");
                boolean e1 = rst.getBoolean("E1");
                boolean e2 = rst.getBoolean("E2");
                boolean e3 = rst.getBoolean("E3");
                boolean e4 = rst.getBoolean("E4");
                 seats = new Seats(myScheduleNo,busId,a1,a2,a3,a4,b1,b2,b3,b4,
                        c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }




    public boolean getSeatStatus(int scheduleNo, String seatNo){
        boolean seatStatus = false;
        try{
            String query = "Select (?) from Seats where schedule_No = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,seatNo);
            pst.setInt(2,scheduleNo);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                seatStatus = (boolean)rst.getBoolean(seatNo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(scheduleNo+" : "+seatNo+" : "+seatStatus);

        return seatStatus;
    }

    public boolean createBusSeat(int scheduleNo, String busId){
        boolean isSaved = false;
        try{
            String query = "Insert into Seats(schedule_No,BusId) values(?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,scheduleNo);
            pst.setString(2,busId);
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
            isSaved = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return isSaved;
    }

    public boolean updateBusSeat(int scheduleNo, String busId){
        boolean isUpdated  = false;
        try{
            String query = "Update seats set busId=? where schedule_No=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,busId);
            pst.setInt(2,scheduleNo);
            int count  = pst.executeUpdate();
            if(count>0) isUpdated=true;
            System.out.println(count+" rows effected");
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean updateSeats(Seats seats){
        boolean isSaved = false;
        try{
            String query = "select SL from seats  where(SCHEDULE_NO=? and BUSID=?)";
            System.out.println(query);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,seats.getScheduleNo());
            pst.setString(2,seats.getBusId());
            ResultSet rst = pst.executeQuery();
            int SL;
            while(rst.next()){
                SL = rst.getInt("SL");
            }
            //TODO START BOOKING SEAT FROM HERE PLEASE
            String query2 = "UPDATE seats SET `A1` = '1', `B1` = '0',`B4` = '1' WHERE (sl = ?);";

        }catch (Exception e){
            e.printStackTrace();
        }


        return  isSaved;
    }
    public boolean bookSeats(Seats seats, List<String> seatsList){
        int SL = getSerialNo(seats.getScheduleNo(),seats.getBusId());
        boolean isSaved = false;
        int size = seatsList.size();
        for(int i=0;i<size;i++){
            String query = "";

        }


        return  isSaved;
    }

    public boolean bookSeat(int scheduleNum, String seatNum){
        boolean isSaved = false;
        try{
            //TODO fix this first
            String query = "Update seats set " +
                    seatNum+
                    "=1 where schedule_no = ?";
            PreparedStatement pst = con.prepareStatement(query);
            //pst.setString(1,seatNum);
            pst.setInt(1,scheduleNum);
            System.out.println(pst.toString());
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
           if(count>0) isSaved = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return isSaved;
    }

    public  boolean cancelSeat(int scheduleNum, String seatNum){
        boolean isDeleted = true;
        try{
            String query = "Update seats set " +
                    seatNum+
                    "=0 where schedule_no = ?";
            PreparedStatement pst = con.prepareStatement(query);
            //pst.setString(1,seatNum);
            pst.setInt(1,scheduleNum);
            System.out.println(pst.toString());
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");
            if(count>0) isDeleted = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  isDeleted;
    }

    private int getSerialNo(int scheduleNo, String busId) {

        int SL=0;
        try{
            String query = "select SL from seats  where(SCHEDULE_NO=? and BUSID=?)";
            System.out.println(query);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,scheduleNo);
            pst.setString(2,busId);
            ResultSet rst = pst.executeQuery();

            while(rst.next()){
                SL = rst.getInt("SL");
            }
            //TODO START BOOKING SEAT FROM HERE PLEASE
            String query2 = "UPDATE seats SET `A1` = '1', `B1` = '0',`B4` = '1' WHERE (sl = ?);";

        }catch (Exception e){
            e.printStackTrace();
        }
        return  SL;
    }

    public int bookedCount(int scheduleNo){
        int totalBooked = 0;
        try{
            //System.out.println(scheduleNo);
            String query = "Select schedule_no, sum(A1+A2+A3+A4+B1+B2+B3+B4+C1+C2+C3+C4+D1+D2+D3+D4+E1+E2+E3+E4) as totalBooked from seats where schedule_no=? group By  schedule_no;";
            PreparedStatement pst  = con.prepareStatement(query);
            pst.setInt(1,scheduleNo);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                totalBooked = rst.getInt("totalBooked");
            }
            //System.out.println("Total booked :"+totalBooked);

        }catch (Exception e){
            e.printStackTrace();
        }
        return totalBooked;
    }
    public boolean deleteBusSeat(int scheduleNo){
        boolean isDeleted = false;
        try{
            String query = "Delete from seats where schedule_no = "+scheduleNo;
            Statement st = con.createStatement();
            int count = st.executeUpdate(query);
            if(count>0) isDeleted = true;
            System.out.println(count+" rows effected");

        }catch (Exception e){
            e.printStackTrace();
        }
        return isDeleted;
    }


}
