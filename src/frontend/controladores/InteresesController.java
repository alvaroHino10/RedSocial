package frontend.controladores;

import backend.serviciointereses.Interes;
import backend.serviciointereses.ServicioIntereses;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.ServicioUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InteresesController {

    @FXML
    private Button guardarIntereses;
    @FXML
    private ComboBox<String> primerInteresCombo;
    @FXML
    private ComboBox<String> segundoInteresCombo;
    @FXML
    private ComboBox<String> tercerInteresCombo;
    @FXML
    private TextField primerInteresText;
    @FXML
    private TextField segundoInteresText;
    @FXML
    private TextField tercerInteresText;

    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioReacciones servicioReacciones;
    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;
    private String primerInteres;
    private String segundoInteres;
    private String tercerInteres;
    private int idUsrActual;

    @FXML
    public void guardarIntereses(ActionEvent event) throws IOException {
        if (primerInteres == null) {
            interesesNulos();
        } else {
            if (!primerInteres.equals("")) {
                primerInteres = primerInteres.toLowerCase();
                int idInteres = servicioIntereses.agregarInteres(primerInteres);
                servicioInteresUsuario.agregarInteresRelacionado(idInteres, idUsrActual);
            }
            if (segundoInteres != null) {
                if (!segundoInteres.equals("")) {
                    segundoInteres = segundoInteres.toLowerCase();
                    int idInteres = servicioIntereses.agregarInteres(segundoInteres);
                    servicioInteresUsuario.agregarInteresRelacionado(idInteres, idUsrActual);
                }
            }
            if (segundoInteres != null) {
                if (!tercerInteres.equals("")) {
                    tercerInteres = tercerInteres.toLowerCase();
                    int idInteres = servicioIntereses.agregarInteres(tercerInteres);
                    servicioInteresUsuario.agregarInteresRelacionado(idInteres, idUsrActual);
                }
            }
            FXMLLoader muroLoader = new FXMLLoader();
            muroLoader.setLocation(getClass().getResource("/frontend/muro.fxml"));
            Parent parent = muroLoader.load();
            ;
            MuroController muroController = muroLoader.getController();
            muroController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones, servicioIntereses,
                    servicioInteresPublicacion, servicioInteresUsuario);
            muroController.recibirUsuario(idUsrActual);
            muroController.cargarPublicaciones();
            Stage stage = (Stage) guardarIntereses.getScene().getWindow();
            stage.close();
            stage.show();
            stage.setScene(new Scene(parent));
        }
    }

    private void interesesNulos() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Minimo debes ingresar un interes para continuar navegando en la red social");
        alert.setTitle("Nuevo Interes");
        alert.showAndWait();
    }

    @FXML
    public void agregarInteresesExistentes(ActionEvent event) {
        this.primerInteres = this.primerInteresCombo.getSelectionModel().getSelectedItem();
        this.segundoInteres = this.segundoInteresCombo.getSelectionModel().getSelectedItem();
        this.tercerInteres = this.tercerInteresCombo.getSelectionModel().getSelectedItem();
        if (primerInteres != null) {
            primerInteresText.setText(this.primerInteres);
            primerInteresText.setDisable(true);
            primerInteresCombo.setPromptText("Primer Interes");
        }
        if (segundoInteres != null) {
            segundoInteresText.setText(this.segundoInteres);
            segundoInteresText.setDisable(true);
            segundoInteresCombo.setPromptText("Segundo Interes");
        }
        if (tercerInteres != null) {
            tercerInteresText.setText(this.tercerInteres);
            tercerInteresText.setDisable(true);
            tercerInteresCombo.setPromptText("Tercer Interes");
        }
    }

    public void obtenerIntereses(KeyEvent keyEvent) {
        this.primerInteres = this.primerInteresText.getText();
        this.segundoInteres = this.segundoInteresText.getText();
        this.tercerInteres = this.tercerInteresText.getText();
    }

    public void cargarIntereses() {
        cargarIntereses(primerInteresCombo);
        cargarIntereses(segundoInteresCombo);
        cargarIntereses(tercerInteresCombo);
    }

    private void cargarIntereses(ComboBox<String> interesesCombo) {
        List<Integer> intereses = servicioIntereses.listarIntereses();
        List<String> interesesNombre = new ArrayList<>();
        for (Integer idInteres : intereses) {
            Interes interes = servicioIntereses.buscarInteres(idInteres);
            interesesNombre.add(interes.getNombreInteres());
        }
        interesesCombo.getItems().addAll(interesesNombre);

    }

    public void recibirUsuario(int idUsrActual) {
        this.idUsrActual = idUsrActual;
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
}
