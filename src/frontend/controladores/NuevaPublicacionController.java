package frontend.controladores;

import backend.serviciointereses.Interes;
import backend.serviciointereses.ServicioIntereses;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.Usuario;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    @FXML
    private Button agregarNuevoInteres;
    @FXML
    private TextField nuevoInteresText;

    private ServicioUsuarios servicioUsuarios;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;
    private MuroController muroController;
    private String contenidoPubli;

    private int idUsrActual;
    private ServicioReacciones servicioReacciones;
    private int idPubliActual;
    private String nuevoInteres;
    private List<Integer> interesesPorRegistrar;

    public void obtenerPublicacion(KeyEvent keyEvent) {
        this.contenidoPubli = textPublicacion.getText();
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader publicacionLoader = new FXMLLoader();
        publicacionLoader.setLocation(getClass().getResource("/frontend/publicacion.fxml"));
        Parent parent = publicacionLoader.load();
        PublicacionController publicacionController = publicacionLoader.getController();
        publicacionController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones,
                servicioIntereses, servicioInteresPublicacion, servicioInteresUsuario);
        Stage stage = (Stage) btnPublicar.getScene().getWindow();
        stage.close();
        this.idPubliActual = servicioPublicaciones.agregarPublicacion(idUsrActual, contenidoPubli);
        if (interesesPorRegistrar == null) interesesPorRegistrar = new ArrayList<>();
        agregarInteresesPublicacion();
        publicacionController.actualizarDatos(idUsrActual, this.idPubliActual);
        muroController.cargarPublicaciones();
        muroController.actualizarUsrOCandidato();
    }

    @FXML
    public void agregarNuevoInteres(ActionEvent event) {
        this.nuevoInteres = this.nuevoInteres.toLowerCase();
        int existeInteres = buscarInteres(this.nuevoInteres);
        if (existeInteres == -1){
            this.nuevoInteresText.clear();
            this.nuevoInteresText.setDisable(true);
            this.agregarNuevoInteres.setDisable(true);
            this.nuevoInteresText.setPromptText("Solo puedes agregar un interes nuevo");
            int idInteres = this.servicioIntereses.agregarInteres(this.nuevoInteres);
            actualizarInteresesLabel(labelInteresesAgregados.getText(), this.nuevoInteres);
            if (interesesPorRegistrar == null) interesesPorRegistrar = new ArrayList<>();
            interesesPorRegistrar.add(idInteres);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("El interes ya existe por favor ingresa otro");
            alert.setTitle("Nuevo Interes");
            alert.showAndWait();
            this.nuevoInteresText.clear();
        }
    }

    @FXML
    void obtenerNuevoInteres(KeyEvent keyEvent) {
        this.nuevoInteres = this.nuevoInteresText.getText();
    }

    public void cargarIntereses() {
        List<Integer> intereses = servicioIntereses.listarIntereses();
        List<String> interesesNombre = new ArrayList<>();
        for (Integer idInteres : intereses) {
            Interes interes = servicioIntereses.buscarInteres(idInteres);
            interesesNombre.add(interes.getNombreInteres());
        }
        interesesPubliCombo.getItems().addAll(interesesNombre);

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

    public void agregarControllerMuro(MuroController muroController) {
        this.muroController = muroController;
    }

    public void recibirUsuario(int idUsrActual) {
        this.idUsrActual = idUsrActual;
        ocultarIntereses();
    }

    @FXML
    public void agregarInteres(ActionEvent event) {
        String interesAgregado = interesesPubliCombo.getSelectionModel().getSelectedItem();
        String interesesActuales = labelInteresesAgregados.getText();
        if (interesesPorRegistrar == null) interesesPorRegistrar = new ArrayList<>();
        int idInteres = buscarInteres(interesAgregado);
        if (idInteres != -1){
            interesesPorRegistrar.add(idInteres);
        }
        actualizarInteresesLabel(interesesActuales, interesAgregado);
        interesesPubliCombo.setPromptText("Lista de intereses");
    }

    private void agregarInteresesPublicacion() {
        for (Integer integer : interesesPorRegistrar) {
            servicioInteresPublicacion.agregarInteresRelacionado(integer, this.idPubliActual);
        }
    }

    private void ocultarIntereses() {
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsrActual);
        if (!usuario.esUsuario()) {
            interesesVBox.setVisible(false);
        }
    }

    private void actualizarInteresesLabel(String interesesActuales, String interesAgregado) {
        if (interesesActuales.isEmpty()) {
            labelInteresesAgregados.setText(interesAgregado);
        } else {
            if (!interesesActuales.contains(interesAgregado)){
                labelInteresesAgregados.setText(interesesActuales + ", " + interesAgregado);
            }
        }
    }

    private int buscarInteres(String interesAgregado) {
        List<Integer> idsInteres = servicioIntereses.listarIntereses();
        for (Integer idInteres : idsInteres) {
            Interes interes = servicioIntereses.buscarInteres(idInteres);
            if (interes.getNombreInteres().equals(interesAgregado)) {
                return idInteres;
            }
        }
        return -1;
    }
}
