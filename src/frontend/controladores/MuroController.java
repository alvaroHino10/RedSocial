package frontend.controladores;

import backend.serviciointeres.ServicioInteres;
import backend.serviciointerespublicacion.ServicioInteresPublicacion;
import backend.serviciointeresusuario.ServicioInteresUsuario;
import backend.serviciopublicaciones.Publicacion;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciousuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MuroController {

    @FXML
    private Label labelMsjUsr;
    @FXML
    private Label labelUsuarioActual;
    @FXML
    private Button btnNuevaPubli;
    @FXML
    private Button cambiarUsuario;
    @FXML
    private VBox vBoxPublicaciones;
    @FXML
    private TextField textCambiarUsuario;

    private ServicioReacciones servicioReacciones;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioInteres servicioInteres;
    private ServicioInteresPublicacion servicioInteresPublicacion;
    private ServicioInteresUsuario servicioInteresUsuario;
    private int idUsrActual;
    private String nombreUsr;

    public void agregarNuevaPublicacion(Parent parent) {
        vBoxPublicaciones.getChildren().add(parent);
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader nuevaPublicacionLoader = new FXMLLoader();
        nuevaPublicacionLoader.setLocation(getClass().getResource("/frontend/nuevaPublicacion.fxml"));
        Parent parent = nuevaPublicacionLoader.load();
        NuevaPublicacionController nuevaPublicacionController = nuevaPublicacionLoader.getController();
        nuevaPublicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                servicioInteres, servicioInteresPublicacion, servicioInteresUsuario);
        nuevaPublicacionController.recibirUsuario(idUsrActual);
        nuevaPublicacionController.agregarControllerMuro(this);
        nuevaPublicacionController.cargarIntereses();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    private boolean tienePublicacion() {
        List<Integer> idsPublicaciones = servicioPublicaciones.listarPublicaciones();
        for (Integer idsPublicacion : idsPublicaciones) {
            Publicacion publicacion = servicioPublicaciones.buscarPublicacion(idsPublicacion);
            if (publicacion.getIdUsuario() == idUsrActual) {
                return true;
            }
        }
        return false;
    }

    public void actualizarUsrOCandidato() {
        Usuario usuario = this.servicioUsuarios.buscarUsuario(idUsrActual);
        if (!usuario.esUsuario()){
            this.btnNuevaPubli.setDisable(tienePublicacion());
            this.labelMsjUsr.setText("Eres un CANDIDATO");
        }
        else this.labelMsjUsr.setText("Eres un USUARIO");
    }

    public void cargarPublicaciones() {
        vBoxPublicaciones.getChildren().clear();
        List<Integer> listaIdPub = this.servicioPublicaciones.listarPublicaciones();
        try {
            for (Integer idPub : listaIdPub) {
                Publicacion publicacion = this.servicioPublicaciones.buscarPublicacion(idPub);
                FXMLLoader publicacionLoader = new FXMLLoader(getClass().getResource("/frontend/publicacion.fxml"));
                Parent contPublicacion = publicacionLoader.load();
                PublicacionController publicacionController = publicacionLoader.getController();
                publicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                        servicioInteres, servicioInteresPublicacion, servicioInteresUsuario);
                publicacionController.actualizarDatos(publicacion.getIdUsuario(), idPub);
                publicacionController.agregarControllerMuro(this);
                agregarNuevaPublicacion(contPublicacion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsrActual);
        this.labelUsuarioActual.setText(usuario.getNombre());
    }

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones,
                                 ServicioReacciones servicioReacciones, ServicioInteres servicioInteres,
                                 ServicioInteresPublicacion servicioInteresPublicacion, ServicioInteresUsuario servicioInteresUsuario) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
        this.servicioInteres = servicioInteres;
        this.servicioInteresPublicacion = servicioInteresPublicacion;
        this.servicioInteresUsuario = servicioInteresUsuario;
    }

    public void recibirUsuario(int idUsrActual) {
        this.idUsrActual = idUsrActual;
        actualizarUsrOCandidato();
    }

    @FXML
    public void obtenerCambioUsuario(KeyEvent event) {
        this.nombreUsr = textCambiarUsuario.getText();
        if (this.nombreUsr != null) {
            this.cambiarUsuario.setDisable(false);
        }
    }

    @FXML
    public void cambiarUsuario(ActionEvent actionEvent) {
        this.idUsrActual = servicioUsuarios.agregarUsuario(this.nombreUsr);
        this.labelUsuarioActual.setText(this.nombreUsr);
        this.textCambiarUsuario.setText("");
        this.cambiarUsuario.setDisable(true);
        actualizarUsrOCandidato();
        cargarPublicaciones();
    }
    public int getIdUsrActual(){
        return idUsrActual;
    }
}