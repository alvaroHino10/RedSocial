package frontend.controladores;

import backend.Publicacion;
import backend.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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



    public void obtenerUsuario(KeyEvent keyEvent) {
        String nombreUsuario = textUsuario.getText();
        usuario = new Usuario(nombreUsuario);
    }

    public void obtenerPublicacion(KeyEvent keyEvent) {
        String contenidoPubli = textPublicacion.getText();
        publicacion = new Publicacion(usuario, contenidoPubli);
    }
}
