package Controller;

import DAO.ReservationDAO;
import DAO.ScheduleDAO;
import DAO.SeatDAO;
import DAO.UserTypeDAO;
import Entities.*;
import Helper.ConnectionProvider;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SeatsViewController implements Initializable {
    @FXML
    private TextField phoneNoField;

    @FXML
    private TextField scheduleNoField;
    @FXML
    private TableView<SeatsView> bookingDetailsTableView;

    @FXML
    private TableColumn<SeatsView, Integer> scheduleNoColumn;

    @FXML
    private TableColumn<SeatsView, String> seatNameColumn;

    @FXML
    private TableColumn<SeatsView, String> isBookedColumn;

    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TableColumn<Reservation, Integer> reservationScheduleNoColumn;

    @FXML
    private TableColumn<Reservation, String> phoneNoColumn;

    @FXML
    private TableColumn<Reservation, String> seatNoColumn;

    private ObservableList<SeatsView> seatsViewObservableList;
    private ObservableList<Reservation> reservationObservableList;
    static int myScheduleNo=0;

    public static void setScheduleNo(int scheduleNo){
        myScheduleNo = scheduleNo;
        System.out.println("Selected schedule No: "+myScheduleNo);
    }
    public static  User myUser;
    public static void setUser(User user){
        myUser = user;
    }
    List<String> seatNOList;
    List<Boolean> seatBooked;
    List<Reservation> reservationList;
    List<SeatsView> seatsViewList;
    Seats seats;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seatsViewObservableList = FXCollections.observableArrayList();
        bookingDetailsTableView.setItems(seatsViewObservableList);
        scheduleNoColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleNo"));
        seatNameColumn.setCellValueFactory(new PropertyValueFactory<>("SeatName"));
        isBookedColumn.setCellValueFactory(new PropertyValueFactory<>("Booked"));
        reservationObservableList = FXCollections.observableArrayList();
        reservationTableView.setItems(reservationObservableList);
        reservationScheduleNoColumn.setCellValueFactory(new PropertyValueFactory<>("schedule_No"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNO"));
        seatNoColumn.setCellValueFactory(new PropertyValueFactory<>("seatName"));

        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        seats = seatDAO.getAllSeatsBySheduleNo(myScheduleNo);

        seatNOList =getAllSeatList();
        seatBooked = getAllSeatStatusList(seats);
        seatsViewList = new ArrayList<>();
        for(int i=0;i<seatNOList.size();i++) {
            String booked = "Available";
            if(seatBooked.get(i)==true){
                booked = "Booked";
            }

            SeatsView seatsView = new SeatsView(myScheduleNo,seatNOList.get(i),booked);
            seatsViewObservableList.add(seatsView);
            seatsViewList.add(seatsView);
        }

        ReservationDAO reservationDAO = new ReservationDAO(ConnectionProvider.getConnection());
        reservationList= new ArrayList<>();
        reservationList = reservationDAO.getAllReservationByScheduleAndPhone(myScheduleNo,myUser.getPhoneNo());
        for(Reservation reservation : reservationList){
            reservationObservableList.add(reservation);
        }
        bookingDetailsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reservationTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void handleBookSeatAction(ActionEvent event) {
        SeatsView seatsView = bookingDetailsTableView.getSelectionModel().getSelectedItem();
        System.out.println(seatsView);
        try {
            if (seatsView.isBooked().equals("Available")) {
                Reservation reservation = new Reservation(myScheduleNo, myUser.getPhoneNo(), seatsView.getSeatName());
                ReservationDAO reservationDAO = new ReservationDAO(ConnectionProvider.getConnection());
                SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
                if (reservationDAO.reserveSeat(reservation)) {
                    seatDAO.bookSeat(myScheduleNo, seatsView.getSeatName());
                    System.out.println("Booked this seat for you " + myUser.getFirstName());
                    reservationList.add(reservation);
                    reservationObservableList.add(reservation);
                    seatsView.setBooked("Booked");
                    bookingDetailsTableView.refresh();

                }
                else {
                    throw new Exception();
                }
            }
            else {
                throw new Exception();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }
    @FXML
    void handleDeleteAction(ActionEvent event) {

        ObservableList<SeatsView> seatsViews = bookingDetailsTableView.getItems();
        Reservation reservation = reservationTableView.getSelectionModel().getSelectedItem();

        if(myUser.getPhoneNo().equals(reservation.getPhoneNO())){

        ReservationDAO reservationDAO = new ReservationDAO(ConnectionProvider.getConnection());
        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        if(reservationDAO.cancelReservation(reservation)){
            seatDAO.cancelSeat(reservation.getSchedule_No(),reservation.getSeatName());
            reservationObservableList.remove(reservation);
            reservationList.remove(reservation);
            reservationTableView.refresh();
            for(int i=0;i<seatsViewObservableList.size();i++){
                if(seatsViewObservableList.get(i).getSeatName().equals(reservation.getSeatName())){
                    seatsViewObservableList.get(i).setBooked("Available");
                    bookingDetailsTableView.refresh();
                    break;
                }
            }
            bookingDetailsTableView.refresh();
        }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error !!!");
            alert.setHeaderText("Hey Mr./Mrs.\nThis is not your seat!!");
            alert.setContentText("Please choose your seat!!");
            alert.showAndWait();
        }
    }

    public  List<Boolean> getAllSeatStatusList(Seats seats) {
        List<Boolean> seatBooked = new ArrayList<>();
        seatBooked.add(seats.isA1()); seatBooked.add(seats.isA2()); seatBooked.add(seats.isA3()); seatBooked.add(seats.isA4());
        seatBooked.add(seats.isB1()); seatBooked.add(seats.isB2()); seatBooked.add(seats.isB3()); seatBooked.add(seats.isB4());
        seatBooked.add(seats.isC1()); seatBooked.add(seats.isC2()); seatBooked.add(seats.isC3()); seatBooked.add(seats.isC4());
        seatBooked.add(seats.isD1()); seatBooked.add(seats.isD2()); seatBooked.add(seats.isD3()); seatBooked.add(seats.isD4());
        seatBooked.add(seats.isE1()); seatBooked.add(seats.isE2()); seatBooked.add(seats.isE3()); seatBooked.add(seats.isE4());
        return seatBooked;
    }

    public  List<String> getAllSeatList(){
        List<String> seatNOList = new ArrayList<>();
        seatNOList.add("A1");seatNOList.add("A2");seatNOList.add("A3");seatNOList.add("A4");
        seatNOList.add("B1");seatNOList.add("B2");seatNOList.add("B3");seatNOList.add("B4");
        seatNOList.add("C1");seatNOList.add("C2");seatNOList.add("C3");seatNOList.add("C4");
        seatNOList.add("D1");seatNOList.add("D2");seatNOList.add("D3");seatNOList.add("D4");
        seatNOList.add("E1");seatNOList.add("E2");seatNOList.add("E3");seatNOList.add("E4");
        return  seatNOList;
    }


    @FXML
    void handleGetTicketAction(ActionEvent event) {
        String myUserTypeName = getUserTypeName();
        Schedule schedule = getSchedule();
        Reservation reservation = getReservation(myScheduleNo);
        try{
            RandomAccessFile outputFile = new RandomAccessFile(myUser.getUserId()+myScheduleNo+reservation.getSeatName()+".txt","rw");
           String ticket =
                   "Schedule No : "+schedule.getScheduleNo()+
                           "\tDate : "+schedule.getDate()+
                           "\nFrom : "+schedule.getFromTime()+
                           "\tTo : "+schedule.getToTime()+
                           "\nUser Id : "+myUser.getUserId()+
                           "\tUser Type: "+myUserTypeName+
                   "\nFirst Name : "+myUser.getFirstName() +
                   "\tLast Name : "+myUser.getLastName()+
                   "\nPhone No: "+myUser.getPhoneNo()+
                           "\nSeat No : "+reservation.getSeatName()+
                   "\tPrice : "+schedule.getPriceEachSeat();


            outputFile.writeBytes(ticket);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Ticket CONFRIMED");
            alert.setContentText("Print your ticket Now!");
            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Reservation getReservation(int myScheduleNo) {
        for(Reservation reservation : reservationList){
            if(reservation.getSchedule_No()==myScheduleNo){
                return reservation;
            }
        }
        return null;
    }

    private Schedule getSchedule() {
        ScheduleDAO scheduleDAO = new ScheduleDAO(ConnectionProvider.getConnection());
        List<Schedule> scheduleList = scheduleDAO.getAllScheduleList();
        for(Schedule schedule : scheduleList){
            if(schedule.getScheduleNo()==myScheduleNo){
                return  schedule;
            }
        }
        return null;
    }


    private String getUserTypeName() {
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        List<UserType> userTypeList = userTypeDAO.getAllUserTypeList();
        for(UserType userType : userTypeList){
            if(userType.getTypeCode().equals(myUser.getTypeCode())){
                return userType.getTypeName();
            }
        }
        return "Not Matched";
    }

    @FXML
    void handleSearchBothAction(ActionEvent event) {
        bookingDetailsTableView.getItems().removeAll(seatsViewObservableList);
        reservationTableView.getItems().removeAll(reservationObservableList);
        seatsViewObservableList.removeAll(seatsViewObservableList);
        reservationObservableList.removeAll(reservationObservableList);


        int scheduleNo = Integer.parseInt(scheduleNoField.getText());
        String phoneNo = phoneNoField.getText();
        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        seats = seatDAO.getAllSeatsBySheduleNo(scheduleNo);

        //seatNOList = getAllSeatList();
        seatBooked = getAllSeatStatusList(seats);
        seatsViewList = new ArrayList<>();
        for(int i=0;i<seatNOList.size();i++) {
            String booked = "Available";
            if(seatBooked.get(i)==true){
                booked = "Booked";
            }

            SeatsView seatsView = new SeatsView(scheduleNo,seatNOList.get(i),booked);
            seatsViewObservableList.add(seatsView);
            seatsViewList.add(seatsView);
        }

        ReservationDAO reservationDAO = new ReservationDAO(ConnectionProvider.getConnection());
        reservationList= new ArrayList<>();
        reservationList = reservationDAO.getAllReservationByScheduleAndPhone(scheduleNo,phoneNo);
        for(Reservation reservation : reservationList){
            reservationObservableList.add(reservation);
        }
    }

    @FXML
    void handleSearchByPhoneAction(ActionEvent event) {
        String phoneNumber = phoneNoField.getText();

        reservationTableView.getItems().removeAll(reservationObservableList);
        reservationObservableList.removeAll(reservationObservableList);
        reservationTableView.refresh();

        ReservationDAO reservationDAO = new ReservationDAO(ConnectionProvider.getConnection());
        List<Reservation> reservationList = reservationDAO.getReservationListByPhone(phoneNumber);

        for(Reservation reservation: reservationList){
            if(reservation.getPhoneNO().equals(phoneNumber)){
                reservationObservableList.add(reservation);
            }
        }
        reservationTableView.refresh();
    }

    @FXML
    void handleSearchBySchedule(ActionEvent event) {
        int scheduleNumber = Integer.parseInt(scheduleNoField.getText());

        reservationTableView.getItems().removeAll(reservationObservableList);
        reservationObservableList.removeAll(reservationObservableList);
        reservationTableView.refresh();

        ReservationDAO reservationDAO = new ReservationDAO(ConnectionProvider.getConnection());
        List<Reservation> reservationList = reservationDAO.getReservationListBySchedule(scheduleNumber);

        for(Reservation reservation: reservationList){
            if(reservation.getSchedule_No()==scheduleNumber){
                reservationObservableList.add(reservation);
            }
        }
        reservationTableView.refresh();
    }

    @FXML
    void handleRefreshAction(ActionEvent event) {
        seatsViewObservableList.removeAll(seatsViewObservableList);
        seatNOList = getAllSeatList();
        seatBooked = getAllSeatStatusList(seats);

        for(int i=0;i<seatNOList.size();i++) {
            String booked = "Available";
            if(seatBooked.get(i)==true){
                booked = "Booked";
            }
            SeatsView seatsView = new SeatsView(myScheduleNo,seatNOList.get(i),booked);
            seatsViewObservableList.add(seatsView);
        }
        reservationObservableList.removeAll(reservationObservableList);
        for(Reservation reservation : reservationList){
            reservationObservableList.add(reservation);
        }
        bookingDetailsTableView.refresh();
        reservationTableView.refresh();
    }



}
