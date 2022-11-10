package backend;

public class Usuario {
    private String nombre;
    private int idUsuario;

    public Usuario(int idUsuario, String nombre) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String toString(){
        return idUsuario + nombre;
    }
}