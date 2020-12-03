package Controller;

import DAO.UserDAO;
import Entities.User;
import Entities.UserType;
import Helper.ConnectionProvider;
import animatefx.animation.ZoomIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageProfileController implements Initializable {
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
    public Image myImage;

    @FXML
    private ImageView profileImageView;

    @Override
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
        firstNameField.setText(myUser.getFirstName());
        lastNameField.setText(myUser.getLastName());
        userNameField.setText(myUser.getUserName());
        passwordField.setText(myUser.getPassword());
        phoneNoField.setText(myUser.getPhoneNo());
        genderComboBox.setValue(myUser.getGender());
        dobPicker.setValue(LocalDate.parse(myUser.getDob().toString()));
        myImage = new Image("Resources/icons8_Businessman_200px_1.png");
        profileImageView.setImage(myImage);
    }

    static User myUser;
    static void getUser(User user){
        myUser = user;
    }


    @FXML
    void handleUpdateAction(ActionEvent event) {
        String userId = myUser.getUserId();
        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String phoneNo = phoneNoField.getText();
        LocalDate dob = dobPicker.getValue();
        Date date = Date.valueOf(dob);
        String gender = genderComboBox.getValue();
        String typeCode = myUser.getTypeCode();
        User user = new User(userId,firstName,lastName,userName,password,phoneNo, date, gender,typeCode);
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());

        if(userDAO.updateUser(user)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Updated Successfully");
            alert.setHeaderText("Mr./Mrs. "+firstName+" "+lastName);
            alert.setContentText("Please Log In Again Now!\n  To Continue Further :)");
            alert.showAndWait();
            HomeController.getUser(user);
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
            //            try{
//                Parent window1; //we need to load the layout that we want to swap
//                window1 = FXMLLoader.load(getClass().getResource("sample.fxml"));
//                Stage window1Stage = Main.homeStage;
//                window1Stage.setTitle("Please Log In to Continue!");
//                window1Stage.setScene(new Scene(window1));
//                window1Stage.show();
//                new ZoomIn(window1).play();
//            }catch (Exception e){
//                e.printStackTrace();
//            }

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }

}
