package frontend.controladores;

import backend.serviciointereses.ServicioIntereses;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciousuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class IngresarUsuarioController {

    @FXML
    private TextField textUsuario;
    @FXML
    private Button botonIngresar;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioReacciones servicioReacciones;
    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;
    private String nombreUsuario;
    private int idUsrActual;

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones,
                                 ServicioReacciones servicioReacciones, ServicioIntereses servicioIntereses,
                                 ServicioRelacionador servicioInteresPublicacion, ServicioRelacionador servicioInteresUsuario) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
        this.servicioIntereses = servicioIntereses;
        this.servicioInteresPublicacion = servicioInteresPublicacion;
        this.servicioInteresUsuario = servicioInteresUsuario;
    }

    @FXML
    public void ingresarUsuario(ActionEvent event) throws IOException {
        this.idUsrActual = servicioUsuarios.agregarUsuario(nombreUsuario);
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsrActual);
        List<Integer> listaIntereses = servicioInteresUsuario.listarInteresesRelacionados(idUsrActual);
        FXMLLoader muroLoader = new FXMLLoader();
        Parent parent;
        if (usuario.esUsuario() && listaIntereses.isEmpty()) {
            muroLoader.setLocation(getClass().getResource("/frontend/intereses.fxml"));
            parent = muroLoader.load();
            InteresesController interesesController = muroLoader.getController();
            interesesController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                    servicioIntereses, servicioInteresPublicacion, servicioInteresUsuario);
            interesesController.cargarIntereses();
            interesesController.recibirUsuario(idUsrActual);
        } else {
            muroLoader.setLocation(getClass().getResource("/frontend/muro.fxml"));
            parent = muroLoader.load();
            MuroController muroController = muroLoader.getController();
            muroController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                    servicioIntereses, servicioInteresPublicacion, servicioInteresUsuario);
            muroController.recibirUsuario(idUsrActual);
            muroController.cargarPublicaciones();
        }

        Stage stage = (Stage) botonIngresar.getScene().getWindow();
        stage.close();
        stage.show();
        stage.setScene(new Scene(parent));
    }

    public void obtenerUsuario(KeyEvent keyEvent) {
        this.nombreUsuario = this.textUsuario.getText();
    }
}
