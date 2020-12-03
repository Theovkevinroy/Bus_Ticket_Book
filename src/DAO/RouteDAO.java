package DAO;

import Entities.Route;
import sun.awt.windows.WPrinterJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    private Connection con;

    public RouteDAO(Connection con) {
        this.con = con;
    }

    public List<Route> getAllRouteList() {

        List<Route> routeList = new ArrayList<>();
        try {
            String query = "Select * from Route";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                String routeId = rst.getString("RouteId");
                String fromPlace = rst.getString("fromPlace");
                String toPlace = rst.getString("toPlace");
                Double distance = rst.getDouble("distanceKm");
                Route route = new Route(routeId, fromPlace, toPlace, distance);
                // System.out.println(route);
                routeList.add(route);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return routeList;
    }

    public boolean createRoute(Route route) {
        boolean isSaved = false;
        try {
            String query = "Insert into Route values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, route.getRouteId());
            pst.setString(2, route.getFromPlace());
            pst.setString(3, route.getToPlace());
            pst.setDouble(4, route.getDistance());
            int count = pst.executeUpdate();
            System.out.println(count + " rows effected");
            isSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteRoute(String routeId) {
        boolean isDeleted = false;
        try {
            String query = "Delete from Route where routeId = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, routeId);
            int count = pst.executeUpdate();
            if (count > 0) isDeleted = true;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    public  boolean updateRoute(Route route){
        boolean isUpdated = false;
        try{
            String query = "Update route set fromPlace=?, toPlace=?, distanceKM=? where routeId=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,route.getFromPlace());
            pst.setString(2,route.getToPlace());
            pst.setDouble(3,route.getDistance());
            pst.setString(4,route.getRouteId());
            int count = pst.executeUpdate();
            if(count>0) isUpdated = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  isUpdated;
    }

}
