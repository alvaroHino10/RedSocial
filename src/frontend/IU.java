package frontend;

import backend.serviciointereses.ServicioIntereses;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.ServicioUsuarios;
import javafx.application.Platform;
import javafx.stage.Stage;

public class IU{

    private ServicioReacciones servicioReacciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioPublicaciones servicioPublicaciones;

    private ServicioIntereses servicioIntereses;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;

    public IU(ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones,
              ServicioUsuarios servicioUsuarios, ServicioIntereses servicioIntereses,
              ServicioRelacionador servicioInteresUsuario, ServicioRelacionador servicioInteresPublicacion) {
        this.servicioReacciones = servicioReacciones;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioIntereses = servicioIntereses;
        this.servicioInteresPublicacion = servicioInteresPublicacion;
        this.servicioInteresUsuario = servicioInteresUsuario;
    }

    public void iniciar(){
        final Interfaz interfaz = new Interfaz(servicioPublicaciones, servicioReacciones, servicioUsuarios,
                servicioIntereses, servicioInteresUsuario, servicioInteresPublicacion);
        try {
            interfaz.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Platform.startup(()->{
            Stage stage = new Stage();
            try {
                interfaz.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }
}