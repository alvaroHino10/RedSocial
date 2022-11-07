package frontend.controladores;


import backend.Publicacion;
import frontend.Reaccion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PublicacionController {

    @FXML
    private ImageView love;

    @FXML
    private VBox vBoxPrincipal;

    @FXML
    private ImageView surprise;

    @FXML
    private ImageView imgUsuario;

    @FXML
    private ImageView imgPublicacion;

    @FXML
    private HBox hBoxReaccionesview;

    @FXML
    private ImageView dontCare;

    @FXML
    private HBox hBoxReacciones;

    @FXML
    private ImageView imgMeGusta;

    @FXML
    private ImageView like;

    @FXML
    private HBox hBoxReaccionar;

    @FXML
    private ImageView happy;

    @FXML
    private Label labelMeGusta;

    @FXML
    private Label nombreUsuario;

    @FXML
    private ImageView thinking;

    @FXML
    private Label labelComentarios;

    @FXML
    private Label labelComentar;

    @FXML
    private Label descripcionPubli;

    @FXML
    private Label labelReaccionesCont;

    @FXML
    private ImageView mad;

    @FXML
    private Label horaPublicacion;

    @FXML
    private ImageView sad;

    @FXML
    private ImageView imgComentar;

    @FXML
    private ImageView care;


    private Reaccion reaccion;
    private Publicacion publicacion;
    private long tiempo;


    public void actualizarDatos(Publicacion publicacion) {
        this.publicacion = publicacion;
        labelReaccionesCont.setText(String.valueOf(publicacion.getTotalReaciones()));
        nombreUsuario.setText(publicacion.getUsuario().getNombre());
        horaPublicacion.setText(publicacion.getFecha());
        descripcionPubli.setText(publicacion.getContenido());
        labelComentarios.setText(publicacion.getComentarios() + " Comentarios");
        reaccion = Reaccion.SINREACCION;
    }

    public void actualizarReaccion(Reaccion reaccion) {
        Image image = new Image(reaccion.getImgSrc());
        imgMeGusta.setImage(image);
        labelMeGusta.setText(reaccion.getNombre());

        if (this.reaccion == Reaccion.SINREACCION) {
            this.publicacion.setTotalReaciones(this.publicacion.getTotalReaciones() + 1);
        }
        this.reaccion = reaccion;
        if (this.reaccion == Reaccion.SINREACCION) {
            this.publicacion.setTotalReaciones(this.publicacion.getTotalReaciones() - 1);
        }
        labelReaccionesCont.setText(String.valueOf(this.publicacion.getTotalReaciones()));
    }


    @FXML
    public void reaccionar(MouseEvent mouseEvent) {
        hBoxReacciones.setVisible(true);

    }
    public void reaccionarLike(MouseEvent mouseEvent) {
        if (this.reaccion == Reaccion.SINREACCION) {
            actualizarReaccion(Reaccion.LIKE);
        } else {
            actualizarReaccion(Reaccion.SINREACCION);
        }
    }

    public void reaccionEspecial(MouseEvent mouseEvent) {

    }
}