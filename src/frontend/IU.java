package frontend;

import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import javafx.application.Platform;
import javafx.stage.Stage;

public class IU{

    private ServicioReacciones servicioReacciones;
    private ServicioUsuarios servicioUsuarios;
    private ServicioPublicaciones servicioPublicaciones;

    public IU(ServicioPublicaciones servicioPublicaciones, ServicioReacciones servicioReacciones, ServicioUsuarios servicioUsuarios) {
        this.servicioReacciones = servicioReacciones;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPublicaciones = servicioPublicaciones;
    }

    public void iniciar(){
        final Interfaz interfaz = new Interfaz(servicioPublicaciones, servicioReacciones, servicioUsuarios);
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