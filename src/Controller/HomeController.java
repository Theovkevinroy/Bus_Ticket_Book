package Controller;

import DAO.UserTypeDAO;
import Entities.User;
import Entities.UserType;
import Helper.ConnectionProvider;
import animatefx.animation.ZoomIn;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public  AnchorPane rootPane;

    @FXML
    public  BorderPane mainPane;

    @FXML
    public  ImageView myImageView;

    public   Image image;

    @FXML
    private Menu manageMenu;

    List<UserType> userTypeList;
    @FXML
    private FontAwesomeIconView maximizeIcon;

    @FXML
    private FontAwesomeIconView minimizeIcon;

    @FXML
    private FontAwesomeIconView signOutIcon;

    @FXML
    private FontAwesomeIconView closeIcon;
    @FXML
    private FontAwesomeIconView homeIcon;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
           // getConnection();
            image = new Image("Resources/bus.jpg");
            myImageView.setImage(image);
            mainPane.setCenter(myImageView);

        }catch (Exception e){
            e.printStackTrace();
        }


        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        userTypeList = userTypeDAO.getAllUserTypeList();
        validateForMenu();

    }

    public static  User myUser;
    public static void getUser(User user){
        myUser=user;
       // System.out.println(myUser);
    }

    @FXML
    void handleBookTicketAction(ActionEvent event) {

    }

    @FXML
    void handleManageBusAction(ActionEvent event) {
        if(validateForButton()) {
            FxmlControllerHere object = new FxmlControllerHere();
            Pane view = object.getPage("Bus");
            mainPane.setCenter(view);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("You are not eligible for this type action");
            alert.setContentText("Please mind your own business");
            alert.showAndWait();
        }
    }
    boolean validateForButton(){
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        List<UserType> userTypeList = userTypeDAO.getAllUserTypeList();
        for(UserType userType : userTypeList){
            if(userType.getTypeCode().equals(myUser.getTypeCode()) && userType.getTypeName().equals("CUSTOMER")){
                return  false;
            }
        }
        return  true;
    }
 void validateForMenu(){
        UserTypeDAO userTypeDAO = new UserTypeDAO(ConnectionProvider.getConnection());
        List<UserType> userTypeList = userTypeDAO.getAllUserTypeList();
        for(UserType userType : userTypeList){
            if(userType.getTypeCode().equals(myUser.getTypeCode()) && userType.getTypeName().equals("CUSTOMER")){
               manageMenu.setVisible(false);
            }
        }

    }

    @FXML
    void handleManageCustomerAction(ActionEvent event) {
//        try{
//            Parent window1; //we need to load the layout that we want to swap
//            window1 = FXMLLoader.load(getClass().getResource("Driver.fxml"));
//            Stage window1Stage = Main.homeStage;
//            //window1Stage.setTitle("Welcome "+userName);
//            window1Stage.setScene(new Scene(window1));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @FXML
    void handleManageDriverAction(ActionEvent event) {
//        try{
//            Parent window1; //we need to load the layout that we want to swap
//            window1 = FXMLLoader.load(getClass().getResource("Driver"));
//            Stage window1Stage = Main.homeStage;
//            //window1Stage.setTitle("Welcome "+userName);
//            window1Stage.setScene(new Scene(window1));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if(validateForButton()) {
            FxmlControllerHere object = new FxmlControllerHere();
            Pane view = object.getPage("Driver");
            mainPane.setCenter(view);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("You are not eligible for this type action");
            alert.setContentText("Please mind your own business");
            alert.showAndWait();
        }
    }

    @FXML
    void handleManageRouteAction(ActionEvent event) {
        if(validateForButton()) {
            FxmlControllerHere object = new FxmlControllerHere();
            Pane view = object.getPage("Route");
            mainPane.setCenter(view);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("You are not eligible for this type action");
            alert.setContentText("Please mind your own business");
            alert.showAndWait();
        }
    }

    @FXML
    void handleManageUserAction(ActionEvent event) {
        if(validateForButton()) {
            FxmlControllerHere object = new FxmlControllerHere();
            Pane view = object.getPage("ManageUsers");
            mainPane.setCenter(view);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("You are not eligible for this type action");
            alert.setContentText("Please mind your own business");
            alert.showAndWait();
        }
    }

    @FXML
    void handleManageBusSeatsAction(ActionEvent event) {
        if(validateForButton()) {
            FxmlControllerHere object = new FxmlControllerHere();
            Pane view = object.getPage("Seats");
            mainPane.setCenter(view);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("You are not eligible for this type action");
            alert.setContentText("Please mind your own business");
            alert.showAndWait();
        }
    }
    @FXML
    void handleManageScheduleAction(ActionEvent event) {
        if(validateForButton()) {
            ScheduleController.setUser(myUser);
            FxmlControllerHere object = new FxmlControllerHere();
            Pane view = object.getPage("Schedule");
            mainPane.setCenter(view);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !!!");
            alert.setHeaderText("You are not eligible for this type action");
            alert.setContentText("Please mind your own business");
            alert.showAndWait();
        }
    }
    @FXML
     void handleLogOutAction(ActionEvent event){
        try{
            Parent window1; //we need to load the layout that we want to swap
            window1 = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage window1Stage = Main.homeStage;
            window1Stage.setTitle("Please Log In to Continue!");
            window1Stage.setScene(new Scene(window1));
            window1Stage.show();
            new ZoomIn(window1).play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleReturnHomeAction(ActionEvent event) {
        mainPane.setCenter(myImageView);
    }

    @FXML
    void handleCloseAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void manageProfileAction(ActionEvent event){
        ManageProfileController.getUser(myUser);
        FxmlControllerHere object = new FxmlControllerHere();
        Pane view = object.getPage("ManageProfile");
        mainPane.setCenter(view);
    }

    @FXML
    void handleShowScheduleAction(ActionEvent event){
        ScheduleController.setUser(myUser);
        FxmlControllerHere object = new FxmlControllerHere();
        Pane view = object.getPage("Schedule");
        mainPane.setCenter(view);
    }
    @FXML
    void handleSearchScheduleAction(ActionEvent event){

    }


    @FXML
    void mouseIconClicked(MouseEvent event) {
        if(event.getSource().equals(closeIcon)){
            try{
               System.exit(0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(event.getSource().equals(signOutIcon)){
            try{
                Parent window1; //we need to load the layout that we want to swap
                window1 = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Stage window1Stage = Main.homeStage;
                window1Stage.setTitle("Please Log In to Continue!");
                window1Stage.setScene(new Scene(window1));
                window1Stage.show();
                new ZoomIn(window1).play();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(event.getSource().equals(minimizeIcon)){
            try{
                Main.homeStage.setIconified(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(event.getSource().equals(maximizeIcon)){
            try{
                Main.homeStage.setMaximized(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(event.getSource().equals(homeIcon)){
            try{
                mainPane.setCenter(myImageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void menuAboutAction(ActionEvent event) {
        FxmlControllerHere object = new FxmlControllerHere();
        Pane view = object.getPage("About");
        mainPane.setCenter(view);
    }

}
