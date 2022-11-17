package backend.serviciopublicaciones;

public class Publicacion {
    public int idUsuario;
    public String fecha;
    public String contenido;
    private final int numComentarios;
    private final int idPublicacion;


    public Publicacion(int idPublicacion, int idUsuario, String contenido, String fecha) {
        this.idPublicacion = idPublicacion;
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

    @Override
    public boolean equals(Object publicacion) {
        if (publicacion instanceof Publicacion) {
            return (idPublicacion == ((Publicacion) publicacion).idPublicacion) && (idUsuario == ((Publicacion) publicacion).idPublicacion)
                    && (contenido.equals(((Publicacion) publicacion).contenido));
        }
        return false;
    }

    public String toString() {
        return idPublicacion + "," + idUsuario + "," + "\"" + contenido + "\"" + "," + fecha;
    }
}