package backend;

import java.util.UUID;

public class Usuario {
    private String nombre;
    private String idUsuario;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.idUsuario = UUID.randomUUID().toString();
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return idUsuario;
    }

    public void setId(String id) {
        this.idUsuario = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}