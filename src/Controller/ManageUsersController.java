package Controller;

import DAO.UserDAO;
import DAO.UserTypeDAO;
import Entities.User;
import Entities.UserType;
import Helper.ConnectionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {
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

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> phoneNoColumn;

    @FXML
    private TableColumn<User, String> typeColumn;


    ObservableList<User> userObservableList;
    ObservableList<String> typeCodes;
    ObservableList<String> genders;
    List<User> userList;
    int userCount;
    List<UserType> userTypeList;
    int currentIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentIndex=0;
        userCount=0;
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
        userList = userDAO.getAllUsers();
        for(User u:userList){
            userCount++;
        }

        userObservableList = FXCollections.observableArrayList();
        userObservableList.addAll(userList);
        genders = FXCollections.observableArrayList();
        typeCodes = FXCollections.observableArrayList();
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        userTypeList = userTypeDAO.getAllUserTypeList();
        for(UserType userType: userTypeList){
            typeCodes.add(userType.getTypeName());
        }
        userTypeComboBox.setItems(typeCodes);
        userTableView.setItems(userObservableList);
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeCode"));
        genders.add("Male");
        genders.add("Female");
        genders.add("Others");
        genderComboBox.setItems(genders);
        genderComboBox.setValue("Select");
        userList = new ArrayList<>();



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
        String typeName = userTypeComboBox.getValue();
        String typeCode = "";
        for(UserType userType:userTypeList){
            if(typeName.equals(userType.getTypeName())){
                typeCode = userType.getTypeCode();
            }

        }
        User user = new User(userId,firstName,lastName,userName,password,phoneNo, date, gender,typeCode);
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());

        if(userDAO.createUser(user)){
            userList.add(user);
            userObservableList.add(user);
            userCount++;
            userTableView.refresh();
            //Controller.userAuth.put(userName,password);
        }else {

        }
    }

    @FXML
    void handleDeleteAction(ActionEvent event) {
        String userId = userObservableList.get(currentIndex).getUserId();
        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String phoneNo = phoneNoField.getText();
        LocalDate dob = dobPicker.getValue();
        Date date = Date.valueOf(dob);
        String gender = genderComboBox.getValue();
        String typeName = userTypeComboBox.getValue();
        String typeCode = "";
        for(UserType userType:userTypeList){
            if(typeName.equals(userType.getTypeName())){
                typeCode = userType.getTypeCode();
            }

        }
        User user = new User(userId,firstName,lastName,userName,password,phoneNo, date, gender,typeCode);
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());

        if(userDAO.deleteUser(user)){
            userList.remove(user);
            userObservableList.remove(user);
            userCount--;
            userTableView.getSelectionModel().clearSelection();
            userTableView.refresh();
            //Controller.userAuth.put(userName,password);
        }else {

        }
    }

    @FXML
    void handleResetAction(ActionEvent event) {
        clearAll();
    }

    @FXML
    void handleUpdateAction(ActionEvent event) {
        String userId = userObservableList.get(currentIndex).getUserId();
        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String phoneNo = phoneNoField.getText();
        LocalDate dob = dobPicker.getValue();
        Date date = Date.valueOf(dob);
        String gender = genderComboBox.getValue();
        String typeName = userTypeComboBox.getValue();
        String typeCode = "";
        for(UserType userType:userTypeList){
            if(typeName.equals(userType.getTypeName())){
                typeCode = userType.getTypeCode();
            }
        }
        User user = new User(userId,firstName,lastName,userName,password,phoneNo, date, gender,typeCode);
        UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
        if(userDAO.updateUser(user)){
            userObservableList.remove(currentIndex);
            userTableView.getSelectionModel().clearSelection();
            userObservableList.add(user);
            userTableView.refresh();
            System.out.println("Successfully Updated");
        }
        userTableView.refresh();
    }

    @FXML
    void handleMouseClickedAction(MouseEvent event) {
        currentIndex = userTableView.getSelectionModel().getSelectedIndex();
        display();
    }
    void display(){
        String userId = userObservableList.get(currentIndex).getUserId();
        String userName = userObservableList.get(currentIndex).getUserName();
        String firstName = userObservableList.get(currentIndex).getFirstName();
        String lastName = userObservableList.get(currentIndex).getLastName();
        String password = userObservableList.get(currentIndex).getPassword();
        String phoneNo = userObservableList.get(currentIndex).getPhoneNo();
        LocalDate dob = LocalDate.parse(userObservableList.get(currentIndex).getDob().toString());
        //Date date = Date.valueOf(dob);
        dobPicker.setValue(dob);
        String gender = userObservableList.get(currentIndex).getGender();
        genderComboBox.setValue(gender);
        String typeName = "";
        String typeCode = userObservableList.get(currentIndex).getTypeCode();
        for(UserType userType:userTypeList){
            if(typeCode.equals(userType.getTypeCode())){
                typeName = userType.getTypeName();
            }
        }
        userTypeComboBox.setValue(typeName);
        firstNameField.setText(firstName);
        userNameField.setText(userName);
        lastNameField.setText(lastName);
        passwordField.setText(password);
        phoneNoField.setText(phoneNo);

     }
    void clearAll(){

    }


}
