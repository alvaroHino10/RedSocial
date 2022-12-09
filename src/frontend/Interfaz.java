package frontend;

import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import frontend.controladores.IngresarUsuarioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioReacciones servicioReacciones;

    public Interfaz(ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones, ServicioUsuarios servicioUsuarios) {
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioReacciones = servicioReacciones;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader ingresarUsuarioLoader = new FXMLLoader(getClass().getResource("/frontend/ingresarUsuario.fxml"));
        Parent parent = ingresarUsuarioLoader.load();
        IngresarUsuarioController ingresarUsuarioController = ingresarUsuarioLoader.getController();
        ingresarUsuarioController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones);
        stage.setTitle("RED SOCIAL");
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
