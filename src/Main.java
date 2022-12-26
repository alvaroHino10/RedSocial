import backend.serviciointereses.ServicioIntereses;
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
        ServicioIntereses servicioIntereses = new ServicioIntereses();
        ServicioRelacionador servicioInteresPublicacion = new ServicioRelacionador("InteresPublicacion");
        ServicioRelacionador servicioInteresUsuario = new ServicioRelacionador("InteresUsuario");
        IU IU = new IU(servicioPublicaciones, servicioReacciones, servicioUsuarios, servicioIntereses,
                servicioInteresPublicacion, servicioInteresUsuario);
        IU.iniciar();
    }
}

