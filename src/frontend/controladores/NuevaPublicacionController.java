package frontend.controladores;

import backend.Publicacion;
import backend.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class NuevaPublicacionController {

    @FXML
    private TextArea textPublicacion;
    @FXML
    private TextField textUsuario;
    @FXML
    private Button btnPublicar;
    @FXML
    private Label labelUsuario;
    @FXML
    private Label labelPublicacion;

    private Usuario usuario;
    private Publicacion publicacion;
    private MuroController muroController;


    public void obtenerUsuario(KeyEvent keyEvent) {
        String nombreUsuario = textUsuario.getText();
        usuario = new Usuario(nombreUsuario);
    }

    public void obtenerPublicacion(KeyEvent keyEvent) {
        String contenidoPubli = textPublicacion.getText();
        publicacion = new Publicacion(usuario, contenidoPubli);
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader publicacionLoader = new FXMLLoader();
        publicacionLoader.setLocation(getClass().getResource("/frontend/publicacion.fxml"));
        Parent parent = publicacionLoader.load();
        PublicacionController publicacionController = publicacionLoader.getController();
        publicacionController.actualizarDatos(publicacion);
        Node source = (Node)actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        muroController.agregarNuevaPublicacion(parent);
    }

    public void transicionVentana(MuroController muroController) {
        this.muroController = muroController;
    }
}
