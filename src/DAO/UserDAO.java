package DAO;

import Entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO  {
    private Connection con;
    public UserDAO(Connection con) {
        this.con = con;
    }

    public List<User> getAllUsers(){
        List<User> usersList = new ArrayList<>();
        try{
            String query = "SELECT * FROM USERINFO";
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()){
                String userId = resultSet.getString("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String userName = resultSet.getString("userName");
                String password = resultSet.getString("password");
                String phoneNo = resultSet.getString("phoneNo");
                Date dob = resultSet.getDate("DateOfBirth");
                String gender = resultSet.getString("gender");
                String typeCode = resultSet.getString("Type_code");
                User user = new User(userId,firstName,lastName,userName,password,phoneNo,dob,gender,typeCode);
                usersList.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return usersList;
    }

    public boolean createUser(User user){
        boolean isSaved = false;
        String query = " insert into userInfo(userId,firstName,lastName,gender,dateOfBirth,userName,password,phoneNo,type_code) values(?,?,?,?,?,?,?,?,?) ";

        try{
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1,user.getUserId());
            st.setString(2,user.getFirstName());
            st.setString(3,user.getLastName());
            st.setString(4,user.getGender());
            st.setDate(5, (Date) user.getDob());
            st.setString(6,user.getUserName());
            st.setString(7,user.getPassword());
            st.setString(8,user.getPhoneNo());
            st.setString(9,user.getTypeCode());
            int count = st.executeUpdate();
            if(count>0) isSaved = true;
            System.out.println(count+" row's Effectetd");

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return isSaved;
    }
    public boolean deleteUser(User user){
        boolean isDeleted= false;
        try{
            String query = "Delete from userInfo where phoneNo = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,user.getPhoneNo());
            int count = pst.executeUpdate();
            if(count>0) isDeleted = true;
            System.out.println(count+" rows effected");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  isDeleted;
    }
    public boolean updateUser(User user){
        boolean isUpdated = false;
        String query = " Update  userInfo set firstName=?,lastName=?,gender=?,dateOfBirth=?,userName=?,password=?,phoneNo=?,type_code=? where userId = ? ";
        try{
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1,user.getFirstName());
            st.setString(2,user.getLastName());
            st.setString(3,user.getGender());
            st.setDate(4, (Date) user.getDob());
            st.setString(5,user.getUserName());
            st.setString(6,user.getPassword());
            st.setString(7,user.getPhoneNo());
            st.setString(8,user.getTypeCode());
            st.setString(9,user.getUserId());
            int count = st.executeUpdate();
            if(count>0) isUpdated = true;
            System.out.println(count+" row's Effectetd");

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return isUpdated;
    }

    public  User getUserByPhone(String phoneNum){
        User user = null;
        try{
            String query = "Select userId,firstName,lastName,gender,dateOfBirth,userName,password,phoneNo,type_code from user where phoneNo=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,phoneNum);
            ResultSet rst = pst.executeQuery();
            while (rst.next()){
                String userId = rst.getString("userId");
                String firstName = rst.getString("firstName");
                String lastName = rst.getString("lastName");
                String userName = rst.getString("userName");
                String password = rst.getString("password");
                String phoneNo = rst.getString("phoneNo");
                Date dob = rst.getDate("DateOfBirth");
                String gender = rst.getString("gender");
                String typeCode = rst.getString("Type_code");
                user = new User(userId,firstName,lastName,userName,password,phoneNo,dob,gender,typeCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
