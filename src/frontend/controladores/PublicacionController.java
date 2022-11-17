package frontend.controladores;


import backend.Emocion;
import backend.Publicacion;
import backend.ServicioPublicaciones;
import backend.ServicioReacciones;
import frontend.Reaccion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

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

    private long tiempo;

    private ServicioReacciones servicioReacciones;
    private ServicioPublicaciones servicioPublicaciones;
    private int idPublicacion;
    private int idUsuario;
    private Publicacion publicacion;


    public void actualizarDatos(int idUsuario, String nombreUsr, String contenido) {
        servicioReacciones = new ServicioReacciones();
        servicioPublicaciones = new ServicioPublicaciones();
        this.idUsuario = idUsuario;
        this.idPublicacion = getPublicacion(contenido);
        this.publicacion = servicioPublicaciones.buscarPublicacion(idPublicacion);
        int totalReacciones = getTotalReacciones();
        labelReaccionesCont.setText(String.valueOf(totalReacciones));
        nombreUsuario.setText(nombreUsr);
        horaPublicacion.setText(this.publicacion.getFecha());
        descripcionPubli.setText(this.publicacion.getContenido());
        labelComentarios.setText(0 + " Comentarios");
        reaccion = Reaccion.MeGusta;
    }

    private int getTotalReacciones() {
        int total = 0;
        Map<Emocion, Integer> emociones = servicioReacciones.listarResumenReacciones(idPublicacion);
        for (int valor : emociones.values()) {
            total += valor;
        }
        return total;
    }

    private int getPublicacion(String contenido) {
        List<Integer> publicacions = servicioPublicaciones.listarPublicaciones();
        for (int idPub : publicacions) {
            Publicacion actual = new Publicacion(idPub, idUsuario, contenido, "");
            Publicacion publicacion = servicioPublicaciones.buscarPublicacion(idPub);
            if (publicacion.equals(actual)) {
                return idPub;
            }
        }
        return 0;
    }

    public void actualizarReaccion(Emocion emocion, Reaccion reaccion) {
        Image image = new Image(reaccion.getImgSrc());
        imgMeGusta.setImage(image);
        labelMeGusta.setText(emocion.name());

        if (this.reaccion == Reaccion.MeGusta) {
            servicioReacciones.agregarReaccion(this.idPublicacion, this.idUsuario, emocion);
        }
        this.reaccion = reaccion;
        if (this.reaccion == Reaccion.MeGusta) {
            //this.publicacion.setTotalReaciones(this.publicacion.getTotalReaciones() - 1);
        }
        labelReaccionesCont.setText(String.valueOf(getTotalReacciones()));
    }


    @FXML
    public void reaccionar(MouseEvent mouseEvent) {
        hBoxReacciones.setVisible(true);

    }

    public void reaccionarLike(MouseEvent mouseEvent) {
        if (this.reaccion == Reaccion.MeGusta) {
            actualizarReaccion(Emocion.Like, Reaccion.Like);
        } else {
            actualizarReaccion(Emocion.Like, Reaccion.MeGusta);
        }
    }

    public void reaccionEspecial(MouseEvent mouseEvent) {

    }
}