package Controller;

import DAO.DriverDAO;
import Entities.Driver;
import Helper.ConnectionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DriverController implements Initializable {


    @FXML
    private ImageView profileImageView;

    @FXML
    private TextField driverIdField;

    @FXML
    private TextField driverNameField;

    @FXML
    private TextArea driverAddressArea;

    @FXML
    private TextField phoneNoField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TableView<Entities.Driver> driverTableView;

    @FXML
    private TableColumn<Entities.Driver,String> driverIdColumn;

    @FXML
    private TableColumn<Entities.Driver, String> driverNameColumn;

    @FXML
    private TableColumn<Entities.Driver, String> addressColumn;

    @FXML
    private TableColumn<Entities.Driver,String> genderColumn;

    @FXML
    private TableColumn<Entities.Driver, String> dobColumn;

    @FXML
    private TableColumn<Entities.Driver, String> phoneNoColumn;

    private ObservableList<Entities.Driver> driverObservableList;
    private ObservableList<String> genderObservableList;

    int currentIndex ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentIndex =0;
        driverObservableList = FXCollections.observableArrayList();

        genderObservableList = FXCollections.observableArrayList();
        genderObservableList.add("Male");
        genderObservableList.add("Female");
        genderObservableList.add("Other");
        genderComboBox.setItems(genderObservableList);
        genderComboBox.setValue("Select");
        driverTableView.setItems(driverObservableList);
        driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        driverNameColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        List<Driver> driverList = new ArrayList<>();
        DriverDAO driverDAO = new DriverDAO(ConnectionProvider.getConnection());
        driverList = driverDAO.getALlDriverList();
        for(Driver driver:driverList){
            driverObservableList.add(driver);
        }
    }

    boolean isExist(String driverId){
        boolean exist = false;
        for(Driver driver: driverObservableList){
            if(driver.getDriverId().equals(driverId)) {
                exist = true;
            }
        }
        return  exist;
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
        String driverId = driverIdField.getText();
        if(isExist(driverId)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("Driver exists!!!");
            alert.setContentText("Try adding another...");
            alert.showAndWait();
        }
        else {
            String driverName = driverNameField.getText();
            String address = driverAddressArea.getText();
            String gender = genderComboBox.getValue();
            String phoneNo = phoneNoField.getText();
            LocalDate dob = dobPicker.getValue();
            Driver driver = new Driver(driverId, driverName, address, gender, dob, phoneNo);
            DriverDAO driverDAO = new DriverDAO(ConnectionProvider.getConnection());
            if (driverDAO.createDriver(driver)) {
                driverObservableList.add(driver);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error !!!");
                alert.setHeaderText("Driver cannot added!!!");
                alert.setContentText("Try adding another...");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void handleResetAction(ActionEvent event) {

    }
    @FXML
    void handleDeleteAction(ActionEvent event) {
        String driverId = driverIdField.getText();
        DriverDAO driverDAO = new DriverDAO(ConnectionProvider.getConnection());
        if(driverDAO.deleteDriver(driverId)){
            driverObservableList.remove(currentIndex);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error !!!");
            alert.setHeaderText("Driver Deleted!!!");
            alert.setContentText("Try another action...");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("Driver cannot deleted!!!");
            alert.setContentText("Try doing another...");
            alert.showAndWait();
        }
    }

    @FXML
    void handleUpdateAction(ActionEvent event) {
        String driverId = driverIdField.getText();
        String driverName = driverNameField.getText();
        String address = driverAddressArea.getText();
        String gender = genderComboBox.getValue();
        String phoneNo = phoneNoField.getText();
        LocalDate dob = dobPicker.getValue();
        Driver driver = new Driver(driverId, driverName, address, gender, dob, phoneNo);
        DriverDAO driverDAO = new DriverDAO(ConnectionProvider.getConnection());
        if (driverDAO.updateDriver(driver)) {
            driverObservableList.remove(currentIndex);
            driverObservableList.add(driver);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("Driver updated!!!");
            alert.setContentText("Try another action...");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("Driver cannot updated!!!");
            alert.setContentText("Try updating another...");
            alert.showAndWait();
        }
    }

    void display(){
        driverIdField.setText(driverObservableList.get(currentIndex).getDriverId());
        driverNameField.setText(driverObservableList.get(currentIndex).getDriverName());
        phoneNoField.setText(driverObservableList.get(currentIndex).getPhoneNo());
        driverAddressArea.setText(driverObservableList.get(currentIndex).getAddress());
        genderComboBox.setValue(driverObservableList.get(currentIndex).getGender());
        dobPicker.setValue(driverObservableList.get(currentIndex).getDateOfBirth());
    }

    @FXML
    void handleMouseClickedAction(MouseEvent event) {
        currentIndex = driverTableView.getSelectionModel().getSelectedIndex();
        display();
    }


}
