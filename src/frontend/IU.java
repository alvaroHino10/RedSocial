package frontend;

import backend.serviciointeres.ServicioInteres;
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

    private ServicioInteres servicioInteres;
    private ServicioRelacionador servicioInteresPublicacion;
    private ServicioRelacionador servicioInteresUsuario;

    public IU(ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones,
              ServicioUsuarios servicioUsuarios, ServicioInteres servicioInteres,
              ServicioRelacionador servicioInteresPublicacion, ServicioRelacionador servicioInteresUsuario) {
        this.servicioReacciones = servicioReacciones;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
        this.servicioInteres = servicioInteres;
        this.servicioInteresPublicacion = servicioInteresPublicacion;
        this.servicioInteresUsuario = servicioInteresUsuario;
    }

    public void iniciar(){
        final Interfaz interfaz = new Interfaz(servicioPublicaciones, servicioReacciones, servicioUsuarios,
                servicioInteres, servicioInteresPublicacion, servicioInteresUsuario);
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