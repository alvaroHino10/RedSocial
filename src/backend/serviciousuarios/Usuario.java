package backend.serviciousuarios;

public class Usuario {
    private final String nombre;
    private final int idUsuario;

    public Usuario(int idUsuario, String nombre) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String toString() {
        return idUsuario + "," + nombre + "\n";
    }
}