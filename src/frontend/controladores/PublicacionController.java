package frontend.controladores;


import backend.servicioreacciones.Emocion;
import backend.serviciopublicaciones.Publicacion;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciousuarios.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Map;

public class PublicacionController {

    @FXML
    private ImageView imgUsuario;

    @FXML
    private HBox hBoxReaccionesview;

    @FXML
    private HBox hBoxReacciones;

    @FXML
    private HBox hBoxReaccionar;

    @FXML
    private Label nombreUsuario;

    @FXML
    private Label labelComentarios;

    @FXML
    private Label labelComentar;

    @FXML
    private Label descripcionPubli;

    @FXML
    private Label labelReaccionesCont;

    @FXML
    private Label horaPublicacion;


    private Emocion reaccion;
    private ServicioReacciones servicioReacciones;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private int idPubliActual;
    private int idUsuario;
    private Publicacion publicacion;


    public void actualizarDatos(int idUsuario, String contenido, int idPubliActual) {
        this.idUsuario = idUsuario;
        this.idPubliActual = idPubliActual;
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsuario);
        this.publicacion = servicioPublicaciones.buscarPublicacion(idPubliActual);
        int totalReacciones = getTotalReacciones();
        labelReaccionesCont.setText(String.valueOf(totalReacciones));
        nombreUsuario.setText(usuario.getNombre());
        horaPublicacion.setText(this.publicacion.getFecha());
        descripcionPubli.setText(this.publicacion.getContenido());
        labelComentarios.setText(0 + " Comentarios");
    }

    private int getTotalReacciones() {
        int total = 0;
        Map<Emocion, Integer> emociones = servicioReacciones.listarResumenReacciones(idPubliActual);
        for (int valor : emociones.values()) {
            total += valor;
        }
        return total;
    }

    public void reaccionEspecial(MouseEvent mouseEvent) {
        String imageView = ((ImageView) mouseEvent.getSource()).getId();
        for (Emocion emocion : Emocion.values()) {
            if (emocion.name().equals(imageView)) {
                this.reaccion = emocion;
                servicioReacciones.agregarReaccion(idPubliActual, idUsuario, this.reaccion);
            }
        }
        labelReaccionesCont.setText(String.valueOf(getTotalReacciones()));
    }

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones) {
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioReacciones = servicioReacciones;
    }
}