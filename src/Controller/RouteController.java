package Controller;

import DAO.RouteDAO;
import Entities.Route;
import Helper.ConnectionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RouteController implements Initializable {

    @FXML
    private TextField routeIdField;

    @FXML
    private TextField distanceField;

    @FXML
    private TextField fromWhereField;

    @FXML
    private TextField toWhereField;

    @FXML
    private Button handleSaveAction;

    @FXML
    private Button handleResetAction;

    @FXML
    private TableView<Route> routeTableView;

    @FXML
    private TableColumn<Route, String> routeIdColumn;

    @FXML
    private TableColumn<Route, String> fromWhereColumn;

    @FXML
    private TableColumn<Route, String> toWhereColumn;

    @FXML
    private TableColumn<Route, Double> distanceColumn;

    private ObservableList<Route> routeObservableList;

    int currentIndex = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        routeObservableList = FXCollections.observableArrayList();
        routeTableView.setItems(routeObservableList);
        routeIdColumn.setCellValueFactory(new PropertyValueFactory<>("routeId"));
        fromWhereColumn.setCellValueFactory(new PropertyValueFactory<>("fromPlace"));
        toWhereColumn.setCellValueFactory(new PropertyValueFactory<>("ToPlace"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        RouteDAO routeDAO = new RouteDAO(ConnectionProvider.getConnection());
        List<Route> routeList = new ArrayList<>();
        routeList = routeDAO.getAllRouteList();
        for(Route route:routeList){
            routeObservableList.add(route);
        }
        routeIdField.setEditable(false);
    }


    @FXML
    void handleSaveAction(ActionEvent event) {
        //String routeId = routeIdField.getText();

        String fromPlace = fromWhereField.getText();
        String toPlace = toWhereField.getText();
        String routeId = fromPlace+" to "+toPlace;
        Double distance = Double.parseDouble(distanceField.getText());
        Route route = new Route(routeId,fromPlace,toPlace,distance);
        RouteDAO routeDAO = new RouteDAO(ConnectionProvider.getConnection());
        if(routeDAO.createRoute(route)){
            routeObservableList.add(route);
        }else{

        }

    }

    void clearALl(){
        distanceField.clear();
        fromWhereField.clear();
        routeIdField.clear();
        toWhereField.clear();
    }
    @FXML
    void handleResetAction(ActionEvent event) {
        clearALl();
    }

    @FXML
    void handleDeleteAction(ActionEvent event) {
        String routeId = routeIdField.getText();
        RouteDAO routeDAO= new RouteDAO(ConnectionProvider.getConnection());
        if(routeDAO.deleteRoute(routeId)){
            routeTableView.getSelectionModel().clearSelection(currentIndex);
            routeObservableList.remove(currentIndex);
        }

    }

    @FXML
    void handleUpdateAction(ActionEvent event) {
        String fromPlace = fromWhereField.getText();
        String toPlace = toWhereField.getText();
        String routeId = routeIdField.getText();
        Double distance = Double.parseDouble(distanceField.getText());
        Route route = new Route(routeId,fromPlace,toPlace,distance);
        RouteDAO routeDAO = new RouteDAO(ConnectionProvider.getConnection());
        if(routeDAO.updateRoute(route)){
            routeTableView.getSelectionModel().clearSelection(currentIndex);
            routeObservableList.remove(currentIndex);
            routeObservableList.add(route);
            routeTableView.refresh();
        }else{

        }
    }

    void display(){
        toWhereField.setText(routeObservableList.get(currentIndex).getToPlace());
        fromWhereField.setText(routeObservableList.get(currentIndex).getFromPlace());
        routeIdField.setText(routeObservableList.get(currentIndex).getRouteId());
        distanceField.setText(routeObservableList.get(currentIndex).getDistance()+"");
    }
    @FXML
    void handleMouseClickAction(MouseEvent event) {
        currentIndex = routeTableView.getSelectionModel().getSelectedIndex();
        display();
    }

}
