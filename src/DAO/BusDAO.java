package DAO;

import Entities.Bus;
import Helper.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    private Connection con;

    public BusDAO(Connection con) {
        this.con = con;
    }

    public List<Bus> getAllBus(){
        List<Bus>  busList = new ArrayList<>();
     try{
        String query = "SELECT * FROM Bus";
        Statement st = con.createStatement();
        ResultSet resultSet =  st.executeQuery(query);

        while(resultSet.next()){
            int id = resultSet.getInt("SL");
            String busId = resultSet.getString("BusId");
            String busModel = resultSet.getString("BusModel");
            int seats = resultSet.getInt("seats");
            double priceEachSeat = resultSet.getDouble("priceEachSeat");
            Bus bus = new Bus(busId,busModel,seats,priceEachSeat);
           // System.out.println(bus);
            busList.add(bus);
        }
    }catch (Exception e){
        e.printStackTrace();
    }

        return busList;
    }

    public boolean createBus(Bus bus){
        boolean isSaved =  false;
        try{
            String query = "insert into bus(busId,busModel,seats,priceEachSeat) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,bus.getBusId());
            pst.setString(2,bus.getBusModel());
            pst.setInt(3,bus.getSeats());
            pst.setDouble(4,bus.getPriceEachSeat());
            int count = pst.executeUpdate();
            System.out.println(count+" rows effected");

        }catch (Exception e){
            e.printStackTrace();
        }
        return isSaved;
    }
    public  boolean deleteBus(Bus bus){
        boolean isDeleted = false;
        try{
            String query = "Delete from bus where busId= ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,bus.getBusId());
            int count = pst.executeUpdate();
            if(count>0) isDeleted = true;
            System.out.println(count+" rows effected");
        }catch (Exception e){
            e.printStackTrace();
        }

        return isDeleted;
    }

    public  boolean updateBus(Bus bus){
        boolean isUpdated = false;
        try{
            String query = " Update bus set busModel=?, seats=?, priceEachSeat=? where busId =?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,bus.getBusModel());
            pst.setInt(2,bus.getSeats());
            pst.setDouble(3,bus.getPriceEachSeat());
            pst.setString(4,bus.getBusId());
            int count = pst.executeUpdate();
            if(count>0) isUpdated = true;
            System.out.println(count+" rows effected");

        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdated;
    }

}
