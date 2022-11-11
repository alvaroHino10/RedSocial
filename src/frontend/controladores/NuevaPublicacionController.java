package frontend.controladores;

import backend.Publicacion;
import backend.ServicioPublicacion;
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
import backend.ServicioUsuarios;

import java.io.IOException;
import java.util.List;

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

    private final ServicioUsuarios servicioUsuarios;
    private final ServicioPublicacion servicioPublicacion;
    private String nombreUsuario;
    private MuroController muroController;
    private String contenidoPubli;

    public NuevaPublicacionController() {
        this.servicioUsuarios = new ServicioUsuarios();
        this.servicioPublicacion =  new ServicioPublicacion();

    }

    public void obtenerUsuario(KeyEvent keyEvent) {
        this.nombreUsuario = textUsuario.getText();
    }

    public void obtenerPublicacion(KeyEvent keyEvent) {
        this.contenidoPubli = textPublicacion.getText();
    }

    public void publicar(ActionEvent actionEvent) throws IOException {
        FXMLLoader publicacionLoader = new FXMLLoader();
        publicacionLoader.setLocation(getClass().getResource("/frontend/publicacion.fxml"));
        Parent parent = publicacionLoader.load();
        PublicacionController publicacionController = publicacionLoader.getController();
        Node source = (Node)actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        servicioUsuarios.agregarUsuario(nombreUsuario);
        int idUsuario = buscarUsuarioId();
        servicioPublicacion.agregarPublicacion(idUsuario, contenidoPubli);
        publicacionController.actualizarDatos(idUsuario, nombreUsuario, contenidoPubli);
        muroController.agregarNuevaPublicacion(parent);
    }

    private int buscarUsuarioId(){
        List<Integer> idsUsuarios = servicioUsuarios.listarUsuarios();
        for (int idActual : idsUsuarios) {
            Usuario usuario = servicioUsuarios.buscarUsuario(idActual);
            if (usuario.getNombre().equals(this.nombreUsuario)) {
                return idActual;
            }
        }
        return 0;
    }

    public void transicionVentana(MuroController muroController) {
        this.muroController = muroController;
    }
}
