package frontend.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MuroController {

    @FXML
    private ScrollPane panelPublicaciones;

    @FXML
    private Button btnNuevaPubli;

    @FXML
    private VBox vBoxPublicaciones;

    public void agregarNuevaPublicacion(Parent parent){
        vBoxPublicaciones.getChildren().add(parent);
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader nuevaPublicacionLoader = new FXMLLoader();
        nuevaPublicacionLoader.setLocation(getClass().getResource("/frontend/nuevaPublicacion.fxml"));
        Parent parent = nuevaPublicacionLoader.load();
        NuevaPublicacionController nuevaPublicacionController = nuevaPublicacionLoader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        nuevaPublicacionController.transicionVentana(this);
    }


}