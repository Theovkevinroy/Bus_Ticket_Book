package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlControllerHere {
    private Pane view;
    public Pane getPage(String fileName){
        try{
            //URL fileUrl = Controller.Main.class.getResource(fileName+".fxml");
            view = new FXMLLoader().load( Main.class.getResource(fileName+".fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
