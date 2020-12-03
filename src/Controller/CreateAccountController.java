package Controller;

import DAO.UserDAO;
import Helper.ConnectionProvider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import Entities.User;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNoField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    List<User> userList;
    ObservableList<String> genders;
    int userCount;

    public void initialize(URL location, ResourceBundle resources) {
        userCount=0;
        genders = FXCollections.observableArrayList();
        genders.add("Male");
        genders.add("Female");
        genders.add("Others");
        genderComboBox.setItems(genders);
        genderComboBox.setValue("Select");
        userList = new ArrayList<>();
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
        userList = userDAO.getAllUsers();
        for(User u:userList){
            userCount++;
       }

    }

    @FXML
    private FontAwesomeIconView closeIcon;

    @FXML
    void mouseClickedIcon(MouseEvent event) {
        if(event.getSource().equals(closeIcon)){
            Stage stage = (Stage) closeIcon.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }

    @FXML
    void handleCreateAction(ActionEvent event) {
        String userId = userCount+"";
        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String phoneNo = phoneNoField.getText();
        LocalDate dob = dobPicker.getValue();
        Date date = Date.valueOf(dob);
        String gender = genderComboBox.getValue();
        String typeCode = "C001";
        User user = new User(userId,firstName,lastName,userName,password,phoneNo, date, gender,typeCode);
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());

        if(userDAO.createUser(user)){
            userList.add(user);
            Controller.userList.add(user);
            userCount++;
            Stage stage = (Stage) closeIcon.getScene().getWindow();
            // do what you have to do
            stage.close();
            //Controller.userAuth.put(userName,password);
        }else {

        }

    }


}
