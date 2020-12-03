package Controller;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URLClassLoader;

public class Main extends Application {
    public static Stage homeStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        homeStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        homeStage.setTitle("Log In");
        homeStage.setScene(new Scene(root));
        homeStage.initStyle(StageStyle.UNDECORATED);
        homeStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
