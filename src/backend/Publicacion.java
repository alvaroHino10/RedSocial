package backend;

import java.util.HashMap;

public class Publicacion {
    public int idUsuario;
    public String fecha;
    public String contenido;
    private final int numComentarios;
    private final int idPublicacion;


    public Publicacion(int idPublicacion, int idUsuario, String contenido, String fecha) {
        this.idPublicacion =  idPublicacion;
        this.idUsuario = idUsuario;
        this.contenido = contenido;
        this.fecha = fecha;
        this.numComentarios = 0;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public String getFecha() {
        return this.fecha;
    }

    public String getContenido() {
        return this.contenido;
    }
}