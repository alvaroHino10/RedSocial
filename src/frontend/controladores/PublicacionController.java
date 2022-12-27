package frontend.controladores;


import backend.serviciointereses.Interes;
import backend.serviciointereses.ServicioIntereses;
import backend.servicioreacciones.Emocion;
import backend.serviciopublicaciones.Publicacion;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.ServicioUsuarios;
import backend.serviciousuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.List;
import java.util.Map;

public class PublicacionController {

    @FXML
    private Label CareCont;
    @FXML
    private Label CareText;
    @FXML
    private Label ExplainCont;
    @FXML
    private Label ExplainText;
    @FXML
    private Label HappyCont;
    @FXML
    private Label HappyText;
    @FXML
    private Label IndifferentCont;
    @FXML
    private Label IndifferentText;
    @FXML
    private Label LikeCont;
    @FXML
    private Label LikeText;
    @FXML
    private Label LoveCont;
    @FXML
    private Label LoveText;
    @FXML
    private Label MadCont;
    @FXML
    private Label MadText;
    @FXML
    private Label SadCont;
    @FXML
    private Label SadText;
    @FXML
    private Label SurpriseCont;
    @FXML
    private Label SurpriseText;
    @FXML
    private Label descripcionPubli;
    @FXML
    private Label horaPublicacion;
    @FXML
    private Label interesesPublicacion;
    @FXML
    private Label nombreUsuario;


    private ServicioReacciones servicioReacciones;
    private ServicioPublicaciones servicioPublicaciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;
    private int idPubliActual;
    private MuroController muroController;


    public void actualizarDatos(int idUsuario, int idPubliActual) {
        this.idPubliActual = idPubliActual;
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsuario);
        Publicacion publicacion = servicioPublicaciones.buscarPublicacion(this.idPubliActual);
        nombreUsuario.setText(usuario.getNombre());
        horaPublicacion.setText(publicacion.getFecha());
        descripcionPubli.setText(publicacion.getContenido());
        String intereses = obtenerInteresesPublicacion(this.idPubliActual);
        interesesPublicacion.setText(intereses);
        actualizarContadorReacciones(this.idPubliActual);
    }

    private String obtenerInteresesPublicacion(int idPub) {
        List<Integer> interesesPublicacion = servicioInteresPublicacion.listarInteresesRelacionados(idPub);
        StringBuilder intereses = new StringBuilder();
        for (int i = 0; i < interesesPublicacion.size(); i++) {
            Interes interes = servicioIntereses.buscarInteres(interesesPublicacion.get(i));
            if (i == interesesPublicacion.size() - 1)
                intereses.append(interes.getNombreInteres());
            else
                intereses.append(interes.getNombreInteres()).append(",");
        }
        return String.valueOf(intereses);
    }


    public void reaccion(ActionEvent actionEvent) {
        String button = ((Button) actionEvent.getSource()).getId();
        int idUsrReaccion = muroController.getIdUsrActual();
        Usuario dueno = buscarDueno();
        for (Emocion emocion : Emocion.values()) {
            if (emocion.name().equals(button)) {
                servicioReacciones.agregarReaccion(idPubliActual, idUsrReaccion, emocion);
                break;
            }
        }
        if (!dueno.esUsuario()){
            contarReaccionesPublicacion(idPubliActual);
        }
        actualizarContadorReacciones(idPubliActual);
    }

    public void contarReaccionesPublicacion(int idPubli) {
        Publicacion publicacion = servicioPublicaciones.buscarPublicacion(idPubli);
        int idUsrDueno = publicacion.getIdUsuario();
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsrDueno);
        boolean tieneTres = servicioReacciones.tieneMasDeTresReacciones(idPubli);
        if (tieneTres) {
            String nombreUsr = servicioUsuarios.cambiarAUsuario(idUsrDueno);
            if (usuario.esUsuario()){
                muroController.agregarCambioUsuario(nombreUsr);
            }
        }
    }

    public void actualizarContadorReacciones(int idPublicacion) {
        Map<Emocion, Integer> resumenReacciones = servicioReacciones.listarResumenReacciones(idPublicacion);
        if (resumenReacciones.get(Emocion.Like) > 0) {
            LikeCont.setText(resumenReacciones.get(Emocion.Like).toString());
            LikeText.setVisible(true);
        }
        if (resumenReacciones.get(Emocion.Love) > 0) {
            LoveCont.setText(resumenReacciones.get(Emocion.Love).toString());
            LoveText.setVisible(true);

        }
        if (resumenReacciones.get(Emocion.Surprise) > 0) {
            SurpriseCont.setText(resumenReacciones.get(Emocion.Surprise).toString());
            SurpriseText.setVisible(true);

        }
        if (resumenReacciones.get(Emocion.Explain) > 0) {
            ExplainCont.setText(resumenReacciones.get(Emocion.Explain).toString());
            ExplainText.setVisible(true);

        }
        if (resumenReacciones.get(Emocion.Sad) > 0) {
            SadCont.setText(resumenReacciones.get(Emocion.Sad).toString());
            SadText.setVisible(true);

        }
        if (resumenReacciones.get(Emocion.Mad) > 0) {
            MadCont.setText(resumenReacciones.get(Emocion.Mad).toString());
            MadText.setVisible(true);

        }
        if (resumenReacciones.get(Emocion.Indifferent) > 0) {
            IndifferentCont.setText(resumenReacciones.get(Emocion.Indifferent).toString());
            IndifferentText.setVisible(true);

        }
        if (resumenReacciones.get(Emocion.Care) > 0) {
            CareCont.setText(resumenReacciones.get(Emocion.Care).toString());
            CareText.setVisible(true);
        }
        if (resumenReacciones.get(Emocion.Happy) > 0) {
            HappyCont.setText(resumenReacciones.get(Emocion.Happy).toString());
            HappyText.setVisible(true);
        }
    }

    public void iniciarServicios(ServicioUsuarios servicioUsuarios, ServicioPublicaciones servicioPublicaciones,
                                 ServicioReacciones servicioReacciones, ServicioIntereses servicioIntereses,
                                 ServicioRelacionador servicioInteresUsuario, ServicioRelacionador servicioInteresPublicacion) {
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

    private Usuario buscarDueno() {
        Publicacion publicacion = servicioPublicaciones.buscarPublicacion(idPubliActual);
        int idUsrDueno = publicacion.getIdUsuario();
        return servicioUsuarios.buscarUsuario(idUsrDueno);
    }
}