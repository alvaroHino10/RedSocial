package backend;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Publicacion {
    public Usuario usuario;
    public String fecha;

    public HashMap<String, Integer> reacciones;
    public String contenido;
    private int totalReaciones;
    private int numComentarios;
    private int idPublicacion;


    public Publicacion(Usuario usuario, String contenido) {
        idPublicacion =  0;
        this.usuario = usuario;
        this.contenido = contenido;
        this.fecha = new Date().toString();
        this.reacciones = new HashMap<>();
        agregarReacciones();
        this.totalReaciones = 0;
        this.numComentarios = 0;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public String getFecha() {
        return this.fecha;
    }

    public int getComentarios() {
        return this.numComentarios;
    }

    public void setComentarios(int comentario) {
        this.numComentarios += comentario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTotalReaciones(int reacciones) {
        this.totalReaciones = reacciones;
    }

    public int getTotalReaciones() {
        return this.totalReaciones;
    }

    public String getContenido() {
        return this.contenido;
    }

    public HashMap<String, Integer> getReacciones() {
        return this.reacciones;
    }

    public void setReacciones(HashMap<String, Integer> listaReacciones) {
        this.reacciones = listaReacciones;
    }

    public int getId() {
        return idPublicacion;
    }

    public void setId(int id) {
        this.idPublicacion = id;
    }

    private void agregarReacciones() {
        reacciones.put("Like", 0);
        reacciones.put("Love", 0);
        reacciones.put("Sad", 0);
        reacciones.put("Happy", 0);
        reacciones.put("Mad",0);
        reacciones.put("Surprise", 0);
        reacciones.put("Care",0);
        reacciones.put("Don't care", 0);
        reacciones.put("Please explain", 0);
    }

    public String toString(){
        return "\"" + idPublicacion + "\"," + "\"" + usuario.getId() + "\"," + "\""+ contenido + "\"," + "\"" + fecha + "\"";
    }
}