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
        int idActual = contIds + 1;
        datosUsuario.put(idActual, usuario);
        guardarDatosUsuario();
        contIds = datosUsuario.size();
        return idActual;
    }

    public void eliminarUsuario(String nombre){}

    public Usuario buscarUsuario(int idUsuario) {
        return datosUsuario.get(idUsuario);
    }

    public List<Integer> listarUsuarios() {
        return new ArrayList<>(datosUsuario.keySet());
    }

    private void guardarDatosUsuario() {
        try {
            FileWriter fileWriter = new FileWriter("Usuarios.csv");
            for (Map.Entry entry : datosUsuario.entrySet()) {
                fileWriter.write(entry.getValue().toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void leerDatosUsuario() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Usuarios.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] datosArreglo = data.split(",");
            Usuario usuario = new Usuario(Integer.parseInt(datosArreglo[0]), datosArreglo[1]);
            datosUsuario.put(Integer.parseInt(datosArreglo[0]), usuario);
        }
    }






}
