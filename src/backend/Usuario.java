package backend;

public class Usuario {
    private String nombre;
    private int idUsuario;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.idUsuario = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return idUsuario;
    }

    public void setId(int id) {
        this.idUsuario = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return idUsuario + nombre;
    }
}