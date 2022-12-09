package frontend.controladores;

import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import backend.serviciousuarios.ServicioUsuarios;

import java.io.IOException;

public class NuevaPublicacionController {

    @FXML
    private TextArea textPublicacion;
    @FXML
    private Button btnPublicar;

    private ServicioUsuarios servicioUsuarios;
    private ServicioPublicaciones servicioPublicaciones;
    private MuroController muroController;
    private String contenidoPubli;

    private int idUsrActual;
    private ServicioReacciones servicioReacciones;

    public void obtenerPublicacion(KeyEvent keyEvent) {
        this.contenidoPubli = textPublicacion.getText();
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader publicacionLoader = new FXMLLoader();
        publicacionLoader.setLocation(getClass().getResource("/frontend/publicacion.fxml"));
        Parent parent = publicacionLoader.load();
        PublicacionController publicacionController = publicacionLoader.getController();
        publicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones);
        Stage stage = (Stage) btnPublicar.getScene().getWindow();
        stage.close();
        int idPubliActual = servicioPublicaciones.agregarPublicacion(idUsrActual, contenidoPubli);
        publicacionController.actualizarDatos(idUsrActual, idPubliActual);
        muroController.cargarPublicaciones();
        muroController.actualizarUsrOCandidato();
    }

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
    }

    public void agregarControllerMuro(MuroController muroController){
        this.muroController = muroController;
    }

    public void recibirUsuario(int idUsrActual){
        this.idUsrActual = idUsrActual;
    }
}
