package DAO;

import Entities.Day;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DayDAO {
    private Connection con;

    public DayDAO(Connection con) {
        this.con = con;
    }

    public List<Day> getAllDays(){
        List<Day> dayList =new ArrayList<>();
        try{
            String query = "Select * from Days";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);
            while(rst.next()){
                String day = rst.getString("Day");
                Day day1 = new Day(day);
                dayList.add(day1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return dayList;
    }


}
