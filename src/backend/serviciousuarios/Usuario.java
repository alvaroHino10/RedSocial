package backend.serviciousuarios;

public class Usuario {
    private final String nombre;
    private final int idUsuario;

    private TipoUsuario tipoUsuario;

    public Usuario(int idUsuario, String nombre) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        tipoUsuario = TipoUsuario.CANDIDATO;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean esUsuario(){
        return TipoUsuario.USUARIO == this.tipoUsuario;
    }

    public void cambiarAUsuario(){
        this.tipoUsuario = TipoUsuario.USUARIO;
    }

    public String toString() {
        return idUsuario + "," + nombre + "," + tipoUsuario + "\n";
    }

    public boolean equals(Object usuario){
        if (usuario instanceof  Usuario){
            return this.nombre.equals(((Usuario) usuario).nombre);
        }
        return false;
    }
}