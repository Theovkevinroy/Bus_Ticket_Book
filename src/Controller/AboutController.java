package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    private ImageView ronyImageView;

    @FXML
    private ImageView tanvirImageView;

    public Image rony;
    public Image tanvir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rony = new Image("Resources/Rony.jpg");
        tanvir = new Image("Resources/tanvir.jpg");
        ronyImageView.setImage(rony);
        tanvirImageView.setImage(tanvir);

    }
}
