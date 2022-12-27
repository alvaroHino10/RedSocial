package frontend;

import backend.serviciointereses.ServicioIntereses;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
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
    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;

    public Interfaz(ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones,
                    ServicioUsuarios servicioUsuarios, ServicioIntereses servicioIntereses,
                    ServicioRelacionador servicioInteresUsuario, ServicioRelacionador servicioInteresPublicacion) {
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioReacciones = servicioReacciones;
        this.servicioIntereses = servicioIntereses;
        this.servicioInteresPublicacion = servicioInteresPublicacion;
        this.servicioInteresUsuario = servicioInteresUsuario;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader ingresarUsuarioLoader = new FXMLLoader(getClass().getResource("/frontend/ingresarUsuario.fxml"));
        Parent parent = ingresarUsuarioLoader.load();
        IngresarUsuarioController ingresarUsuarioController = ingresarUsuarioLoader.getController();
        ingresarUsuarioController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones, servicioIntereses, servicioInteresUsuario, servicioInteresPublicacion);
        stage.setTitle("RED SOCIAL");
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
