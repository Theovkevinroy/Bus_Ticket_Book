package Controller;

import DAO.BusDAO;
import Helper.ConnectionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Entities.Bus;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BusController implements Initializable {

    @FXML
    private TextField busIdField;

    @FXML
    private TextField busModelField;

    @FXML
    private TextField seatsField;

    @FXML
    private TextField priceEachSeatField;

    @FXML
    private TableView<Bus> busTableView;

    @FXML
    private TableColumn<Bus,String> busIdColumn;

    @FXML
    private TableColumn<Bus,String> busModelColumn;

    @FXML
    private TableColumn<Bus,Integer> seatsColumn;

    @FXML
    private TableColumn<Bus,Double> priceEachColumn;


    private ObservableList<Bus> busInfoObservableList;
    int currentIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentIndex = 0;
        busInfoObservableList = FXCollections.observableArrayList();
        busTableView.setItems(busInfoObservableList);
        busIdColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        busModelColumn.setCellValueFactory(new PropertyValueFactory<>("busModel"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        priceEachColumn.setCellValueFactory(new PropertyValueFactory<>("priceEachSeat"));
        List<Bus> busList = new ArrayList<>();
        BusDAO busDAO = new BusDAO(ConnectionProvider.getConnection());
        busList = busDAO.getAllBus();
        for(Bus bus : busList){
            busInfoObservableList.add(bus);
        }


    }

    void clearAll(){
        busIdField.clear();
        busModelField.clear();
        priceEachSeatField.clear();
        seatsField.clear();
    }
    @FXML
    void handleResetAction(ActionEvent event) {
       clearAll();
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
        String busId = busIdField.getText();
        String busModel = busModelField.getText();
        int seats = Integer.parseInt(seatsField.getText());
        double priceEachSeat = Double.parseDouble(priceEachSeatField.getText());
        Bus bus = new Bus(busId,busModel,seats,priceEachSeat);
        BusDAO busDAO = new BusDAO(ConnectionProvider.getConnection());
       if(busDAO.createBus(bus)){
           busInfoObservableList.add(bus);
           busTableView.refresh();
           clearAll();
       }else {

       }

    }

    @FXML
    void handleUpdateAction(ActionEvent event) {
        String prevBusId = busInfoObservableList.get(currentIndex).getBusId();
        String busId = busIdField.getText();
        String busModel = busModelField.getText();
        int seats = Integer.parseInt(seatsField.getText());
        double priceEachSeat = Double.parseDouble(priceEachSeatField.getText());
        Bus bus  = new Bus(busId,busModel, seats,priceEachSeat);
        BusDAO busDAO = new BusDAO(ConnectionProvider.getConnection());
        try{
        if(!busId.equals(prevBusId)) throw new Exception("BUS ID CANNOT BE CHANGED FROM HERE");
        if(busDAO.updateBus(bus)){
            busInfoObservableList.remove(currentIndex);
            busTableView.getSelectionModel().clearSelection();
            busInfoObservableList.add(bus);
            busTableView.refresh();
            System.out.println("Successfully updated "+bus);
        }
        }catch (Exception e){
            System.err.println(e);
        }
        busTableView.refresh();

    }

    @FXML
    void handleDeleteAction(ActionEvent event) {
        Bus bus = busTableView.getSelectionModel().getSelectedItem();
        BusDAO busDAO = new BusDAO(ConnectionProvider.getConnection());
        if(busDAO.deleteBus(bus)){
            System.out.println("Bus "+ bus+" is deleted form database");
            busInfoObservableList.remove(currentIndex);
            busTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void handleMouseClickedAction(MouseEvent event) {
        currentIndex = busTableView.getSelectionModel().getSelectedIndex();
        display();
    }
    void display(){
        String busId = busInfoObservableList.get(currentIndex).getBusId();
        String  busModel = busInfoObservableList.get(currentIndex).getBusModel();
        Double priceEachSeat = busInfoObservableList.get(currentIndex).getPriceEachSeat();
        int seats = busInfoObservableList.get(currentIndex).getSeats();
        busIdField.setText(busId);
        busModelField.setText(busModel);
        priceEachSeatField.setText(priceEachSeat+"");
        seatsField.setText(seats+"");
    }


}
