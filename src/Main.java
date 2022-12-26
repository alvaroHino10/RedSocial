import backend.serviciointeres.ServicioInteres;
import backend.serviciopublicaciones.ServicioPublicaciones;
import backend.servicioreacciones.ServicioReacciones;
import backend.serviciorelacionador.ServicioRelacionador;
import backend.serviciousuarios.ServicioUsuarios;
import frontend.IU;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        ServicioPublicaciones servicioPublicaciones = new  ServicioPublicaciones();
        ServicioUsuarios servicioUsuarios = new ServicioUsuarios();
        ServicioReacciones servicioReacciones = new ServicioReacciones();
        ServicioInteres servicioInteres = new ServicioInteres();
        ServicioRelacionador servicioInteresPublicacion = new ServicioRelacionador("InteresPublicacion");
        ServicioRelacionador servicioInteresUsuario = new ServicioRelacionador("InteresUsuario");
        IU IU = new IU(servicioPublicaciones, servicioReacciones, servicioUsuarios, servicioInteres,
                servicioInteresPublicacion, servicioInteresUsuario);
        IU.iniciar();
    }
}

