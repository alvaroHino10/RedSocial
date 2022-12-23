package frontend.controladores;

import backend.serviciointeres.Interes;
import backend.serviciointeres.ServicioInteres;
import backend.serviciointerespublicacion.ServicioInteresPublicacion;
import backend.serviciointeresusuario.ServicioInteresUsuario;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private ServicioInteres servicioInteres;
    private ServicioInteresPublicacion servicioInteresPublicacion;
    private ServicioInteresUsuario servicioInteresUsuario;
    private String primerInteres;
    private String segundoInteres;
    private String tercerInteres;
    private int idUsrActual;

    @FXML
    public void guardarIntereses(ActionEvent event) throws IOException {
        if (!primerInteres.equals("")){
            int idInteres = servicioInteres.agregarInteres(primerInteres);
            servicioInteresUsuario.agregarInteresUsuario(idInteres, idUsrActual);
        }
        if (!segundoInteres.equals("")){
            int idInteres = servicioInteres.agregarInteres(segundoInteres);
            servicioInteresUsuario.agregarInteresUsuario(idInteres, idUsrActual);
        }
        if (!tercerInteres.equals("")){
            int idInteres = servicioInteres.agregarInteres(tercerInteres);
            servicioInteresUsuario.agregarInteresUsuario(idInteres, idUsrActual);
        }
        FXMLLoader muroLoader = new FXMLLoader();
        muroLoader.setLocation(getClass().getResource("/frontend/muro.fxml"));
        Parent parent = muroLoader.load();;
        MuroController muroController = muroLoader.getController();
        muroController.iniciarServicios(servicioUsuarios, servicioPublicaciones, servicioReacciones, servicioInteres,
                servicioInteresPublicacion, servicioInteresUsuario);
        muroController.recibirUsuario(idUsrActual);
        muroController.cargarPublicaciones();
        Stage stage = (Stage) guardarIntereses.getScene().getWindow();
        stage.close();
        stage.show();
        stage.setScene(new Scene(parent));
    }

    @FXML
    public void agregarInteresesExistentes(ActionEvent event) {
        this.primerInteres = this.primerInteresCombo.getSelectionModel().getSelectedItem();
        this.segundoInteres = this.segundoInteresCombo.getSelectionModel().getSelectedItem();
        this.tercerInteres = this.tercerInteresCombo.getSelectionModel().getSelectedItem();
        if (primerInteres != null){
            primerInteresText.setText(this.primerInteres);
            primerInteresText.setDisable(true);
            primerInteresCombo.setPromptText("Primer Interes");
        }
        if (segundoInteres != null){
            segundoInteresText.setText(this.segundoInteres);
            segundoInteresText.setDisable(true);
            segundoInteresCombo.setPromptText("Segundo Interes");
        }
        if (tercerInteres != null){
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

    public void cargarIntereses(){
        cargarIntereses(primerInteresCombo);
        cargarIntereses(segundoInteresCombo);
        cargarIntereses(tercerInteresCombo);
    }

    private void cargarIntereses(ComboBox<String> interesesCombo) {
        List<Integer> intereses = servicioInteres.listarIntereses();
        List<String> interesesNombre = new ArrayList<>();
        for (Integer idInteres : intereses) {
            Interes interes = servicioInteres.buscarInteres(idInteres);
            interesesNombre.add(interes.getNombreInteres());
        }
        interesesCombo.getItems().addAll(interesesNombre);

    }
    public void recibirUsuario(int idUsrActual) {
        this.idUsrActual = idUsrActual;
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
}
