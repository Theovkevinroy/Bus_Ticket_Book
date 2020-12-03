package DAO;

import Entities.User;
import Entities.UserType;
import Helper.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserTypeDAO {
    private Connection con;

    public UserTypeDAO(Connection con) {
        this.con = con;
    }

    public List<UserType> getAllUserTypeList(){
        List<UserType> userTypeList = new ArrayList<>();
        try{
            String query = "Select * from user_type";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while (rst.next()){
                 int sl = rst.getInt("sl");
                 String typeCode = rst.getString("type_code");
                 String typeName=rst.getString("type_name");
                 String status = rst.getString("status");
                 UserType userType = new UserType(sl,typeCode,typeName,status);
                 userTypeList.add(userType);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return  userTypeList;
    }
}
