package frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IU extends Application {

    private FXMLLoader inicio;
    private Parent parent;
    public IU(){
        inicio = new FXMLLoader((getClass().getResource("muro.fxml")));
    }

    @Override
    public void start(Stage stage) throws Exception {
        parent = inicio.load();
        stage.setTitle("RED SOCIAL");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void iniciar(){
        Platform.startup(()->{});
        launch();
    }
}
