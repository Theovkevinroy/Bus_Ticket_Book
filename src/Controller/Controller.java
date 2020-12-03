package Controller;

import DAO.UserDAO;
import Entities.User;
import Helper.ConnectionProvider;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
import com.sun.javafx.geom.AreaOp;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView myImageView;

   // static HashMap<String, String> userAuth;
    static List<User> userList;
    @FXML
    private FontAwesomeIconView closeIcon;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  userAuth = new HashMap<>();
        Image image = new Image("Resources/bus.jpg");
        myImageView.setImage(image);
        new FadeInRight(myImageView).play();
        //new FadeInLeft(rootPane).play();
        userList = new ArrayList<>();
            UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
            userList = userDAO.getAllUsers();
            for (User user : userList) {    
                System.out.println(user.getUserName() + " " + user.getPassword()+" "+user.getTypeCode());
              //  userAuth.put(user.getUserName(), user.getPassword());
            }
    }


    @FXML
    void handleLogInAction(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
       // UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
        User user = vaidateUser(userName,password);
        if(!user.equals(null)){
            try{
                HomeController.getUser(user);
                Parent window1; //we need to load the layout that we want to swap
                window1 = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Stage window1Stage = Main.homeStage;
                window1Stage.setTitle("Welcome "+userName);
                window1Stage.setScene(new Scene(window1));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        userNameField.setText("");
        passwordField.setText("");

    }
    User vaidateUser(String userName, String password){
//        for(Map.Entry m : userAuth.entrySet()){
//            if(m.getKey().equals(userName)){
//                if(m.getValue().equals(password)){
//                    System.out.println("Valid");
//                    try{
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    return  true;
//                }
//            }
//        }
        User myUser = null;
        for(User user : userList){
            if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                System.out.println("VALID");
                ScheduleController.setUser(user);
                myUser = user;
            }
        }
        return myUser;
    }

    @FXML
    void handleCreateAccountAction(ActionEvent event) {

        try {
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Welcome User");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void handleAdminAction(ActionEvent event) {
        try{
            Parent window1; //we need to load the layout that we want to swap
            window1 = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage window1Stage = Main.homeStage;
            window1Stage.setTitle("Welcome Admin");
            window1Stage.setScene(new Scene(window1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void mouseClickedIconAction(MouseEvent event) {
        if(event.getSource().equals(closeIcon)){
            System.exit(0);
        }
    }




}

