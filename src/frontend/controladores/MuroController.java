package frontend.controladores;

import backend.serviciointereses.ServicioIntereses;
import backend.serviciopublicaciones.Publicacion;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciousuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button cerrarSesion;
    @FXML
    private VBox vBoxPublicaciones;

    private ServicioReacciones servicioReacciones;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;
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
                servicioIntereses, servicioInteresPublicacion, servicioInteresUsuario);
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
                        servicioIntereses, servicioInteresPublicacion, servicioInteresUsuario);
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
                                 ServicioReacciones servicioReacciones, ServicioIntereses servicioIntereses,
                                 ServicioRelacionador servicioInteresPublicacion, ServicioRelacionador servicioInteresUsuario) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
        this.servicioIntereses = servicioIntereses;
        this.servicioInteresPublicacion = servicioInteresPublicacion;
        this.servicioInteresUsuario = servicioInteresUsuario;
    }

    public void recibirUsuario(int idUsrActual) {
        this.idUsrActual = idUsrActual;
        actualizarUsrOCandidato();
    }

    @FXML
    public void cerrarSesion(ActionEvent actionEvent) throws IOException {
        actualizarUsrOCandidato();
        FXMLLoader ingresarUsuario = new FXMLLoader();
        ingresarUsuario.setLocation(getClass().getResource("/frontend/ingresarUsuario.fxml"));
        Parent parent = ingresarUsuario.load();
        IngresarUsuarioController ingresarUsuarioController = ingresarUsuario.getController();
        ingresarUsuarioController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                servicioIntereses, servicioInteresPublicacion, servicioInteresUsuario);
        Stage stage = (Stage) cerrarSesion.getScene().getWindow();
        stage.setScene(new Scene(parent));
        stage.close();
        stage.show();
    }
    public int getIdUsrActual(){
        return idUsrActual;
    }
}