package frontend.controladores;

import backend.serviciopublicaciones.Publicacion;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciousuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

public class MuroController {

    @FXML
    private ScrollPane panelPublicaciones;

    @FXML
    private Button btnNuevaPubli;

    @FXML
    private VBox vBoxPublicaciones;
    private ServicioReacciones servicioReacciones;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;

    public void agregarNuevaPublicacion(Parent parent) {
        vBoxPublicaciones.getChildren().add(parent);
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader nuevaPublicacionLoader = new FXMLLoader();
        nuevaPublicacionLoader.setLocation(getClass().getResource("/frontend/nuevaPublicacion.fxml"));
        Parent parent = nuevaPublicacionLoader.load();
        NuevaPublicacionController nuevaPublicacionController = nuevaPublicacionLoader.getController();
        nuevaPublicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones);
        nuevaPublicacionController.agregarControllerMuro(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void cargarPublicaciones() {
        List<Integer> listaIdPub = this.servicioPublicaciones.listarPublicaciones();
        try {
            for (Integer idPub : listaIdPub) {
                Publicacion publicacion = this.servicioPublicaciones.buscarPublicacion(idPub);
                Usuario usuario = this.servicioUsuarios.buscarUsuario(publicacion.getIdUsuario());
                FXMLLoader publicacionLoader = new FXMLLoader(getClass().getResource("/frontend/publicacion.fxml"));
                Parent contPublicacion = publicacionLoader.load();
                PublicacionController publicacionController = publicacionLoader.getController();
                publicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones);
                publicacionController.actualizarDatos(publicacion.getIdUsuario(), usuario.getNombre(), publicacion.getContenido(), idPub);
                agregarNuevaPublicacion(contPublicacion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
    }

    public void cambiarUsuario(ActionEvent actionEvent) {

    }
}