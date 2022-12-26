package backend.serviciousuarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioUsuarios {
    private final SortedMap<Integer, Usuario> datosUsuario;
    private int contIds;

    public ServicioUsuarios() {
        this.datosUsuario = new TreeMap<>();
        leerDatosUsuario();
        this.contIds = datosUsuario.size();
    }

    public int agregarUsuario(String nombre) {
        Usuario usuario = new Usuario(contIds + 1, nombre);
        int existeUsuario = existeUsuario(usuario);
        if (existeUsuario == -1){
            int idActual = contIds + 1;
            datosUsuario.put(idActual, usuario);
            guardarDatosUsuario();
            contIds = datosUsuario.size();
            return idActual;
        }
        return existeUsuario;
    }

    private int existeUsuario(Usuario usuario) {
        for (int i = 0; i < datosUsuario.keySet().size(); i++) {
            Usuario usuarioEncontrado = datosUsuario.get(i + 1);
            if (usuarioEncontrado.equals(usuario)){
                return i + 1;
            }
        }
        return -1;
    }

    public void eliminarUsuario(String nombre){}

    public Usuario buscarUsuario(int idUsuario) {
        return datosUsuario.get(idUsuario);
    }

    public List<Integer> listarUsuarios() {
        return new ArrayList<>(datosUsuario.keySet());
    }

    public void cambiarAUsuario(int idUsr){
        Usuario usuario = datosUsuario.get(idUsr);
        String nombre = usuario.cambiarAUsuario();
        System.out.println(nombre + " ahora es un USUARIO");
        guardarDatosUsuario();
    }

    private void guardarDatosUsuario() {
        try {
            FileWriter fileWriter = new FileWriter("Usuarios.csv");
            for (Map.Entry<Integer, Usuario> entry : datosUsuario.entrySet()) {
                fileWriter.write(entry.getValue().toString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leerDatosUsuario() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Usuarios.csv"));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] datosArreglo = data.split(",");
                Usuario usuario = new Usuario(Integer.parseInt(datosArreglo[0]), datosArreglo[1]);
                datosUsuario.put(Integer.parseInt(datosArreglo[0]), usuario);
                if (datosArreglo[2].equals(TipoUsuario.USUARIO.name())){
                    usuario.cambiarAUsuario();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
