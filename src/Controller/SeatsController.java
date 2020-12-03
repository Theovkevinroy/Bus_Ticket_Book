package Controller;

import DAO.BusDAO;
import DAO.SeatDAO;
import Entities.Bus;
import Entities.Schedule;
import Entities.Seats;
import Entities.User;
import Helper.ConnectionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ClientInfoStatus;
import java.util.*;

public class SeatsController implements Initializable {
    @FXML
    private Label yourIdLabel;

    @FXML
    private Label busIdLabel;

    @FXML
    private Label scheduleNoLabel;

    @FXML
    private Button A1;
    @FXML
    private Button A2;
    @FXML
    private Button A3;
    @FXML
    private Button A4;
    @FXML
    private Button B1;
    @FXML
    private Button B2;
    @FXML
    private Button B3;
    @FXML
    private Button B4;
    @FXML
    private Button C1;
    @FXML
    private Button C2;
    @FXML
    private Button C3;
    @FXML
    private Button C4;
    @FXML
    private Button D1;
    @FXML
    private Button D2;
    @FXML
    private Button D3;
    @FXML
    private Button D4;

    public static Schedule selectedSchedule;

    int seatsArr[][] ;
    int n = 4;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("3 "+selectedSchedule);
        scheduleNoLabel.setText("HI");
        scheduleNoLabel.setText(selectedSchedule.getScheduleNo()+"");
        busIdLabel.setText(selectedSchedule.getBusId()+"");
        List<Seats> seatsList = new ArrayList<>();
        SeatDAO seatDAO = new SeatDAO(ConnectionProvider.getConnection());
        seatsList = seatDAO.getAllSeats();

        Seats mySeat = null;
        for(Seats seats:seatsList){
            if(selectedSchedule.getScheduleNo()==seats.getScheduleNo()){
                mySeat = seats;
            }
        }

        List<String> seatNOList = new ArrayList<>();
        seatNOList.add("A1");seatNOList.add("A2");seatNOList.add("A3");seatNOList.add("A4");
        seatNOList.add("B1");seatNOList.add("B2");seatNOList.add("B3");seatNOList.add("B4");
        Map<String,Boolean> seatNoMap = new HashMap<>();
        for(int i =0; i<seatNOList.size();i++){
            String seatName = seatNOList.get(i);
            //seatNoMap.put(seatName,seatDAO.getSeatStatus(mySeat.getScheduleNo(),seatName));
        }
        for(Map.Entry m : seatNoMap.entrySet()){
           // if()
        }


        seatsArr = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                seatsArr[i][j]=-1;
            }
        }

    }  //INITIALIZE

    public static void passSelectedSchedule(Schedule schedule){
        selectedSchedule = schedule;
        System.out.println("4 "+ schedule);

    }



    @FXML
    void handleSaveAction(ActionEvent event) {
        List<String> seatsList = fetchSeats();

    }



    @FXML
    void handleSeatAction(ActionEvent event) {
        String seatNumber = ((Button) event.getSource()).getText();
        System.out.println(seatNumber+" is clicked");
        bookSeat(seatNumber);
    }

    private void bookSeat(String seatNumber) {
        if(seatNumber.equals("A1")){
            seatsArr[0][0]=-seatsArr[0][0];
        }
        else if(seatNumber.equals("A2")){
            seatsArr[0][1]=-seatsArr[0][1];
        }
        else if(seatNumber.equals("A3")){
            seatsArr[0][2]=-seatsArr[0][2];
        }
        else if(seatNumber.equals("A4")){
            seatsArr[0][3]=-seatsArr[0][3];
        }
        else if(seatNumber.equals("B1")){
            seatsArr[1][0]=-seatsArr[1][0];
        }
        else if(seatNumber.equals("B2")){
            seatsArr[1][1]=-seatsArr[1][1];
        }
        else if(seatNumber.equals("B3")){
            seatsArr[1][2]=-seatsArr[1][2];
        }
        else if(seatNumber.equals("B4")){
            seatsArr[1][3]=-seatsArr[1][3];
        }
        else if(seatNumber.equals("C1")){
            seatsArr[2][0]=-seatsArr[2][0];
        }
        else if(seatNumber.equals("C2")){
            seatsArr[2][1]=-seatsArr[2][1];
        }
        else if(seatNumber.equals("C3")){
            seatsArr[2][2]=-seatsArr[2][2];
        }
        else if(seatNumber.equals("C4")){
            seatsArr[2][3]=-seatsArr[2][3];
        }
        else if(seatNumber.equals("D1")){
            seatsArr[3][0]=-seatsArr[3][0];
        }
        else if(seatNumber.equals("D2")){
            seatsArr[3][1]=-seatsArr[3][1];
        }
        else if(seatNumber.equals("D3")){
            seatsArr[3][2]=-seatsArr[3][2];
        }
        else if(seatNumber.equals("D4")){
            seatsArr[3][3]=-seatsArr[3][3];
        }
    }

    private List<String> fetchSeats() {
        List<String> seatsList =  new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(seatsArr[i][j]==1){
                    seatsList.add("A1");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("A2");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("A3");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("A4");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("B1");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("B2");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("B3");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("B4");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("C1");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("C2");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("C3");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("C4");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("D1");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("D2");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("D3");
                }
                else if(seatsArr[i][j]==1){
                    seatsList.add("D4");
                }
            }
        }
        return seatsList;
    }
    @FXML
    void handleResetActiion(ActionEvent event) {

    }

    static User user;
    public static void getUser(User user){
        user = user;
    }


}
