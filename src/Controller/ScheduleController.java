package Controller;

import DAO.*;
import Entities.*;
import Helper.ConnectionProvider;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {


    @FXML
    private ButtonBar manageScheduleBtnBar;

    @FXML
    private GridPane trialPane;


    @FXML
    public  Button deleteBtn;

    @FXML
    public  Button updateBtn;

    @FXML
    public  Button addScheduleBtn;

    @FXML
    private ComboBox<String> busIdComboBox;

    @FXML
    private ComboBox<String> driverIdComboBox;

    @FXML
    private ComboBox<String> routeIdComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField fromTimeField;

    @FXML
    private TextField toTimeField;

    @FXML
    private TableView<Schedule> scheduleTableView;

    @FXML
    private TableColumn<Schedule, Integer> scheduleNoColumn;

    @FXML
    private TableColumn<Schedule, String> busIdColumn;

    @FXML
    private TableColumn<Schedule, String> driverIdColumn;

    @FXML
    private TableColumn<Schedule, String> routeIdColumn;

    @FXML
    private TableColumn<Schedule, String> fromTimeColumn;

    @FXML
    private TableColumn<Schedule, String> toTimeColumn;

    @FXML
    private TableColumn<Schedule, String> dateColumn;


    @FXML
    private TableColumn<Schedule, Double> priceColumn;

    @FXML
    private TableView<SeatAvailabality> seatAvailablityTableView;

    @FXML
    private TableColumn<SeatAvailabality, Integer> seatScheduleNoColumn;

    @FXML
    private TableColumn<SeatAvailabality, Integer> bookedColumn;

    @FXML
    private TableColumn<SeatAvailabality, Integer> availableColumn;

    @FXML
    private AnchorPane addSchedulePage;

    @FXML
    private ComboBox<String> fromWhereComboBox;

    @FXML
    private ComboBox<String> toWhereComboBox;

    @FXML
    private DatePicker SearchDatePicker;


    private ObservableList<String> busIdObservableList;
    private ObservableList<String> driverIdObservableList;
    private ObservableList<String> routeIdObservableList;
    //  private ObservableList<String> dayObservableList;

    ObservableList<Schedule> scheduleObservableList;
    ObservableList<String> fromWhereList;
    ObservableList<String> toWhereList;
    //  private ObservableList<Integer> availableSeatObservableList;

    public List<Double> priceList;
    public List<Bus> busList;
    //  public List<Seats> seatsList;

    ObservableList<SeatAvailabality> availabalityObservableList;
    public Integer maxScheduleNo;
    int currentIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageScheduleBtnBar = new ButtonBar();
        updateBtn = new Button();
        addScheduleBtn = new Button();
        deleteBtn = new Button();
        currentIndex = 0;
        maxScheduleNo = Integer.MIN_VALUE;
        priceList = new ArrayList<>();

        fromWhereList = FXCollections.observableArrayList();
        fromWhereComboBox.setItems(fromWhereList);
        toWhereList = FXCollections.observableArrayList();
        toWhereComboBox.setItems(toWhereList);
        scheduleObservableList = FXCollections.observableArrayList();
        busIdObservableList = FXCollections.observableArrayList();
        driverIdObservableList = FXCollections.observableArrayList();
        routeIdObservableList = FXCollections.observableArrayList();
        //dayObservableList = FXCollections.observableArrayList();
        busIdComboBox.setItems(busIdObservableList);
        driverIdComboBox.setItems(driverIdObservableList);
        routeIdComboBox.setItems(routeIdObservableList);
        // dayComboBox.setItems(dayObservableList);
        scheduleTableView.setItems(scheduleObservableList);
        scheduleNoColumn.setCellValueFactory(new PropertyValueFactory<>("ScheduleNo"));
        busIdColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        routeIdColumn.setCellValueFactory(new PropertyValueFactory<>("routeId"));
        fromTimeColumn.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        toTimeColumn.setCellValueFactory(new PropertyValueFactory<>("toTime"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        //  avaiableSeatColumn.setCellValueFactory(new PropertyValueFactory<>("seatAvailable"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceEachSeat"));
        scheduleTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ScheduleDAO scheduleDAO = new ScheduleDAO(ConnectionProvider.getConnection());
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList = scheduleDAO.getAllScheduleList();
        List<Integer> availableSchedeuleNoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleObservableList.add(schedule);
            int temp = schedule.getScheduleNo();
            availableSchedeuleNoList.add(temp);
            if (temp > maxScheduleNo) maxScheduleNo = temp;
        }

        busList = new ArrayList<>();
        BusDAO busDAO = new BusDAO(ConnectionProvider.getConnection());
        busList = busDAO.getAllBus();
        for (Bus bus : busList) {
            busIdObservableList.add(bus.getBusId());
        }

        List<Driver> driverList = new ArrayList<>();
        DriverDAO driverDAO = new DriverDAO(ConnectionProvider.getConnection());
        driverList = driverDAO.getALlDriverList();
        for (Driver driver : driverList) {
            driverIdObservableList.add(driver.getDriverId());
        }

        List<Route> routeList = new ArrayList<>();
        RouteDAO routeDAO = new RouteDAO(ConnectionProvider.getConnection());
        routeList = routeDAO.getAllRouteList();
        for (Route route : routeList) {
            routeIdObservableList.add(route.getRouteId());
            fromWhereList.add(route.getFromPlace());
            toWhereList.add(route.getToPlace());
        }
        availabalityObservableList = FXCollections.observableArrayList();
        seatAvailablityTableView.setItems(availabalityObservableList);
        seatScheduleNoColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleNo"));
        bookedColumn.setCellValueFactory(new PropertyValueFactory<>("booked"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        for (int i = 0; i < availableSchedeuleNoList.size(); i++) {
            int scheduleNo = availableSchedeuleNoList.get(i);
            int booked = seatDAO.bookedCount(scheduleNo);
            int available = 20 - booked;
            SeatAvailabality seatAvailabality = new SeatAvailabality(scheduleNo, booked, available);
            availabalityObservableList.add(seatAvailabality);
            // System.out.println(seatAvailabality);
            validateForBtnBar();
            validateForPane();
        }


    }


    void validateForPane(){
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        List<UserType> userTypeList = userTypeDAO.getAllUserTypeList();
        for(UserType userType : userTypeList){
            if(userType.getTypeCode().equals(myUser.getTypeCode()) && userType.getTypeName().equals("CUSTOMER")){
                trialPane.setDisable(true);
                trialPane.setVisible(false);
            }
        }

    }

    void validateForBtnBar(){
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        List<UserType> userTypeList = userTypeDAO.getAllUserTypeList();
        for(UserType userType : userTypeList){
            if(userType.getTypeCode().equals(myUser.getTypeCode()) && userType.getTypeName().equals("CUSTOMER")){

            }
        }

    }

    void clearAll(){
        busIdComboBox.setValue("Select");
        driverIdComboBox.setValue("Select");
        routeIdComboBox.setValue("Select");
        fromTimeField.setText("");
        toTimeField.setText("");
        fromWhereComboBox.setValue("Select");
        toWhereComboBox.setValue("Select");
    }
    @FXML
    void handleResetAction(ActionEvent event) {
        clearAll();
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
        scheduleTableView.getSelectionModel().clearSelection();
        seatAvailablityTableView.getSelectionModel().clearSelection();
        String busId = busIdComboBox.getValue();
        String routeId = routeIdComboBox.getValue();
        String driverId = driverIdComboBox.getValue();
        LocalDate date = datePicker.getValue();
        Date date1 = Date.valueOf(date);
        String fromTime = fromTimeField.getText();
        Time fromTime1 = Time.valueOf(fromTime);
        String toTime = toTimeField.getText();
        Time toTime1 = Time.valueOf(toTime);
        double priceEachSeat = 0;

        // getting pricelist from bus table
        for (Bus bus : busList) {
            if (busId.equals(bus.getBusId())) {
                priceEachSeat = bus.getPriceEachSeat();
            }
        }

        maxScheduleNo++;
        Schedule schedule = new Schedule(maxScheduleNo, busId, driverId, routeId, fromTime1, toTime1, date1, priceEachSeat);
        ScheduleDAO scheduleDAO = new ScheduleDAO(ConnectionProvider.getConnection());
        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        if (scheduleDAO.createSchedule(schedule)) {
            scheduleObservableList.add(schedule);
            if (seatDAO.createBusSeat(maxScheduleNo, busId)) {
                SeatAvailabality seatAvailabality = new SeatAvailabality(maxScheduleNo, 0, 20);
                availabalityObservableList.add(seatAvailabality);
            }
            new ZoomOut(addSchedulePage).play();
        } else {

        }
    }

    static User myUser;

    public static void setUser(User user) {
        myUser = user;
    }

    @FXML
    void handleBookAction(ActionEvent event) {
        Schedule schedule = scheduleTableView.getSelectionModel().getSelectedItem();
        System.out.println("got this from selection :" + schedule);
        SeatsViewController.setScheduleNo(schedule.getScheduleNo());

        TextInputDialog dialog = new TextInputDialog("01853501668");
        dialog.setTitle("Authorize Yourself!!");
        dialog.setHeaderText("Enter to go to next page");
        dialog.setContentText("Please enter your Phone No:");
        Optional<String> result = dialog.showAndWait();
        // TODO FIX PHONE NO
        if (result.isPresent() && myUser.getPhoneNo().equals(result.get())) {
            String phoneNo = result.get();
            System.out.println("Your phone No: " + phoneNo);
            SeatsViewController.setUser(myUser);
            try {
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatsView.fxml"));
                SeatsViewController seatsViewController = loader.getController();
                root = (Parent) loader.load();
                // Parent root = null;
                //root = FXMLLoader.load(getClass().getResource("Seats.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Welcome ");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }

    }

    @FXML
    void handleAddScheduleAction(ActionEvent event) {
        scheduleTableView.getSelectionModel().clearSelection();
        seatAvailablityTableView.getSelectionModel().clearSelection();
        clearAll();
        if (validateForButton()) {
            addSchedulePage.toFront();
            new ZoomIn(addSchedulePage).play();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, You are not eligible for this action");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }

    @FXML
    void handleCloseAction(ActionEvent event) {
        //addSchedulePage.setDisable(true);
        //addSchedulePage.toBack();
        scheduleTableView.getSelectionModel().clearSelection();
        seatAvailablityTableView.getSelectionModel().clearSelection();
        currentIndex = 0;
        new ZoomOut(addSchedulePage).play();
    }

    @FXML
    void handleUpdateAction(ActionEvent event) {
        scheduleTableView.getSelectionModel().clearSelection();
        seatAvailablityTableView.getSelectionModel().clearSelection();
        if (validateForButton()) {
            addSchedulePage.toFront();
            new ZoomIn(addSchedulePage).play();
            display();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, You are not eligible for this action");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }

    void display() {
        int scheduleId = scheduleObservableList.get(currentIndex).getScheduleNo();
        busIdComboBox.setValue(scheduleObservableList.get(currentIndex).getBusId());
        driverIdComboBox.setValue(scheduleObservableList.get(currentIndex).getDriverId());
        routeIdComboBox.setValue(scheduleObservableList.get(currentIndex).getRouteId());
        fromTimeField.setText(scheduleObservableList.get(currentIndex).getFromTime() + "");
        toTimeField.setText(scheduleObservableList.get(currentIndex).getToTime() + "");
        datePicker.setValue(LocalDate.parse(scheduleObservableList.get(currentIndex).getDate() + ""));
    }

    @FXML
    void handleMouseClickedAction(MouseEvent event) {
        currentIndex = scheduleTableView.getSelectionModel().getSelectedIndex();
        // display();
    }

    @FXML
    void handleDeleteAction(ActionEvent event) {
        scheduleTableView.getSelectionModel().clearSelection();
        seatAvailablityTableView.getSelectionModel().clearSelection();
        if (validateForButton()) {
            int scheduleId = scheduleObservableList.get(currentIndex).getScheduleNo();
            ScheduleDAO scheduleDAO = new ScheduleDAO(ConnectionProvider.getConnection());
            SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
            if (scheduleDAO.deleteSchedule(scheduleId)) {
                if (seatDAO.deleteBusSeat(scheduleId)) {
                    scheduleObservableList.remove(currentIndex);
                    scheduleTableView.getSelectionModel().clearSelection(currentIndex);
                    for (SeatAvailabality seatAvailabality : availabalityObservableList) {
                        if (seatAvailabality.getScheduleNo() == scheduleId) {
                            availabalityObservableList.remove(seatAvailabality);
                        }
                    }
                    scheduleTableView.refresh();
                    seatAvailablityTableView.refresh();
                }
            }
            scheduleTableView.refresh();
            seatAvailablityTableView.refresh();
            scheduleTableView.getSelectionModel().clearSelection();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, You are not eligible for this action");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }

    @FXML
    void handleUpdateScheduleAction(ActionEvent event) {
        int scheduleNo = scheduleObservableList.get(currentIndex).getScheduleNo();

        String busId = busIdComboBox.getValue();
        String routeId = routeIdComboBox.getValue();
        String driverId = driverIdComboBox.getValue();
        LocalDate date = datePicker.getValue();
        Date date1 = Date.valueOf(date);
        String fromTime = fromTimeField.getText();
        Time fromTime1 = Time.valueOf(fromTime);
        String toTime = toTimeField.getText();
        Time toTime1 = Time.valueOf(toTime);
        double priceEachSeat = 0;

        // getting pricelist from bus table
        for (Bus bus : busList) {
            if (busId.equals(bus.getBusId())) {
                priceEachSeat = bus.getPriceEachSeat();
            }
        }

        //maxScheduleNo++;
        Schedule schedule = new Schedule(scheduleNo, busId, driverId, routeId, fromTime1, toTime1, date1, priceEachSeat);
        ScheduleDAO scheduleDAO = new ScheduleDAO(ConnectionProvider.getConnection());
        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        if (scheduleDAO.updateSchedule(schedule)) {
            scheduleObservableList.remove(currentIndex);
            scheduleTableView.getSelectionModel().clearSelection();
            scheduleObservableList.add(schedule);
            scheduleTableView.refresh();
            if (seatDAO.updateBusSeat(scheduleNo, busId)) {
                seatAvailablityTableView.refresh();
            }
            new ZoomOut(addSchedulePage).play();
        } else {

        }
        scheduleTableView.refresh();
    }

    boolean validateForButton() {
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        List<UserType> userTypeList = userTypeDAO.getAllUserTypeList();
        for (UserType userType : userTypeList) {
            if (userType.getTypeCode().equals(myUser.getTypeCode()) && userType.getTypeName().equals("CUSTOMER")) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void handleSearchScheduleAction(ActionEvent event) {
        try {
            String from = fromWhereComboBox.getValue();
            String to = toWhereComboBox.getValue();
            LocalDate date = null;
            Date myDate = null;
            if ((date = SearchDatePicker.getValue()) != null) {
                myDate = Date.valueOf(date);
            }
            System.out.println("SO this is : "+ from+" "+to+" "+myDate);
            ScheduleDAO scheduleDAO = new ScheduleDAO(ConnectionProvider.getConnection());
            List<Schedule> scheduleList = scheduleDAO.getScheduleBySearch(from, to, myDate);
            scheduleObservableList.removeAll(scheduleObservableList);
            scheduleObservableList.addAll(scheduleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
