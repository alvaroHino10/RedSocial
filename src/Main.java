import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciousuarios.ServicioUsuarios;
import frontend.IU;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        ServicioPublicaciones servicioPublicaciones = new ServicioPublicaciones();
        ServicioUsuarios servicioUsuarios = new ServicioUsuarios();
        ServicioReacciones servicioReacciones = new ServicioReacciones();
        IU IU = new IU(servicioPublicaciones, servicioReacciones, servicioUsuarios);
        IU.iniciar();
    }
}

