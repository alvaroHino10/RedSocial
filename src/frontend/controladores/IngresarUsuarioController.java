package frontend.controladores;

import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class IngresarUsuarioController {

    @FXML
    private TextField textUsuario;
    @FXML
    private Button botonIngresar;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioReacciones servicioReacciones;
    private String nombreUsuario;
    private int idUsrActual;

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
    }

    public void obtenerUsuario(KeyEvent keyEvent){
        this.nombreUsuario = this.textUsuario.getText();
    }

    @FXML
    public void ingresarUsuario(ActionEvent event) throws IOException {
        this.idUsrActual = servicioUsuarios.agregarUsuario(nombreUsuario);
        FXMLLoader muroLoader = new FXMLLoader();
        muroLoader.setLocation(getClass().getResource("/frontend/muro.fxml"));
        Parent parent = muroLoader.load();
        MuroController muroController = muroLoader.getController();
        muroController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones);
        Stage stage = (Stage) botonIngresar.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(parent));
        muroController.actualizarUsuario(idUsrActual);
        muroController.cargarPublicaciones();
        stage.show();
    }
}
