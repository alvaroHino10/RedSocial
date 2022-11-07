package backend;

import java.util.ArrayList;
import java.util.List;

public class RedSocial {
    private Usuario usuario;
    private List<Publicacion> listaPublicaciones;

    public RedSocial(Usuario usuario) {
        this.usuario = usuario;
        this.listaPublicaciones = new ArrayList<>();
    }

    public List<Publicacion> getPublicaciones() {
        return listaPublicaciones;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void agregarPublicacion(Publicacion publicacion) {
        listaPublicaciones.add(publicacion);
    }
}
