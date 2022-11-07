package backend;

import frontend.Reaccion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ListaPublicacion {
    private List<Publicacion> datosPublicacion;
    private List<Usuario> datosUsuario;

    public ListaPublicacion() {
        this.datosPublicacion = new ArrayList<>();
        this.datosUsuario = new ArrayList<>();
    }

    public void guardarDatosPublicacion() {
        try {
            FileWriter fileWriter = new FileWriter("Publicaciones.csv");
            for (Publicacion publicacion : datosPublicacion) {
                fileWriter.write(publicacion.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leerDatosPublicacion() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Publicaciones.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] datosArreglo = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            Usuario usuario = buscarUsuario(Integer.parseInt(datosArreglo[1]));
            Publicacion publicacion = new Publicacion(usuario, datosArreglo[2]);
            publicacion.setId(Integer.parseInt(datosArreglo[0]));
            publicacion.setFecha(datosArreglo[3]);
            datosPublicacion.add(publicacion);
        }
    }

    public void guardarReaccion() {
        String csvFormat = "";
        try {
            FileWriter fileWriter = new FileWriter("Reacciones.csv");
            for (Publicacion publicacion : datosPublicacion) {
                HashMap<String, Integer> reaccionPubli = publicacion.getReacciones();
                for (Map.Entry<String, Integer> entry : reaccionPubli.entrySet()) {
                    if (entry.getValue() > 0) {
                        csvFormat = "\"" + publicacion.getId() + "\"" + "," + "\"" + entry.getKey() + "\"" + "\"" + publicacion.getUsuario().getId() + "\"";
                    }
                }
                fileWriter.write(csvFormat + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leerReacciones(){
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Reacciones.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {

        }
    }

    public void guardarDatosUsuario() {
        try {
            FileWriter fileWriter = new FileWriter("Usuarios.csv");
            for (Usuario Usuario : datosUsuario) {
                fileWriter.write(Usuario.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leerDatosUsuario() {
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
            Usuario usuario = new Usuario(datosArreglo[1]);
            usuario.setId(Integer.parseInt(datosArreglo[0]));
            datosUsuario.add(usuario);
        }
    }

    public Usuario buscarUsuario(int idUsuario) {
        for (Usuario usuario : datosUsuario) {
            if (usuario.getId() == idUsuario)
                return usuario;
        }
        return null;
    }
}
