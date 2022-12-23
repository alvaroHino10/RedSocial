package frontend.controladores;

import backend.serviciointeres.Interes;
import backend.serviciointeres.ServicioInteres;
import backend.serviciointerespublicacion.ServicioInteresPublicacion;
import backend.serviciointeresusuario.ServicioInteresUsuario;
import backend.serviciousuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NuevaPublicacionController {

    @FXML
    private TextArea textPublicacion;
    @FXML
    private ComboBox<String> interesesPubliCombo;
    @FXML
    private Button btnPublicar;
    @FXML
    private VBox interesesVBox;
    @FXML
    private Label labelInteresesAgregados;

    private ServicioUsuarios servicioUsuarios;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioInteres servicioInteres;
    private ServicioInteresPublicacion servicioInteresPublicacion;
    private ServicioInteresUsuario servicioInteresUsuario;
    private MuroController muroController;
    private String contenidoPubli;

    private int idUsrActual;
    private ServicioReacciones servicioReacciones;
    private int idPubliActual;

    public void obtenerPublicacion(KeyEvent keyEvent) {
        this.contenidoPubli = textPublicacion.getText();
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader publicacionLoader = new FXMLLoader();
        publicacionLoader.setLocation(getClass().getResource("/frontend/publicacion.fxml"));
        Parent parent = publicacionLoader.load();
        PublicacionController publicacionController = publicacionLoader.getController();
        publicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                servicioInteres, servicioInteresPublicacion, servicioInteresUsuario);
        Stage stage = (Stage) btnPublicar.getScene().getWindow();
        stage.close();
        this.idPubliActual = servicioPublicaciones.agregarPublicacion(idUsrActual, contenidoPubli);
        publicacionController.actualizarDatos(idUsrActual, this.idPubliActual);
        muroController.cargarPublicaciones();
        muroController.actualizarUsrOCandidato();
    }

    public void cargarIntereses() {
        List<Integer> intereses = servicioInteres.listarIntereses();
        List<String> interesesNombre = new ArrayList<>();
        for (Integer idInteres : intereses) {
            Interes interes = servicioInteres.buscarInteres(idInteres);
            interesesNombre.add(interes.getNombreInteres());
        }
        interesesPubliCombo.getItems().addAll(interesesNombre);

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

    public void agregarControllerMuro(MuroController muroController) {
        this.muroController = muroController;
    }

    public void recibirUsuario(int idUsrActual) {
        this.idUsrActual = idUsrActual;
        ocultarIntereses();
    }

    private void ocultarIntereses() {
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsrActual);
        if (!usuario.esUsuario()) {
            interesesVBox.setVisible(false);
        }
    }

    @FXML
    public void agregarInteres(ActionEvent event) {
        String interesAgregado = interesesPubliCombo.getSelectionModel().getSelectedItem();
        String interesesActuales = labelInteresesAgregados.getText();
        int idInteres = buscarInteres(interesAgregado);
        servicioInteresPublicacion.agregarInteresPublicacion(idInteres, this.idPubliActual);
        if (interesesActuales.isEmpty()) {
            labelInteresesAgregados.setText(interesAgregado);
        } else {
            if (!interesesActuales.contains(interesAgregado)){
                labelInteresesAgregados.setText(interesesActuales + ", " + interesAgregado);
            }
        }
        interesesPubliCombo.getSelectionModel().clearSelection();
    }

    private int buscarInteres(String interesAgregado) {
        List<Integer> idsInteres = servicioInteres.listarIntereses();
        for (Integer idInteres : idsInteres) {
            Interes interes = servicioInteres.buscarInteres(idInteres);
            if (interes.getNombreInteres().equals(interesAgregado)) {
                return idInteres;
            }
        }
        return -1;
    }
}
