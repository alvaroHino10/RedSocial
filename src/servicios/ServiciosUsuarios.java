package servicios;

import backend.Publicacion;
import backend.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServiciosUsuarios {
    private SortedMap<Integer, Usuario> datosUsuario;
    private int contIds;


    public ServiciosUsuarios() {
        this.datosUsuario = new TreeMap<>();
        leerDatosUsuario();
        this.contIds = datosUsuario.size();
    }

    public void agregarUsuario(String nombre) {
        Usuario usuario = new Usuario(contIds + 1, nombre);
        datosUsuario.put(contIds + 1, usuario);
        guardarDatosUsuario();
        contIds = datosUsuario.size();
    }

    public String buscarUsuario(int idUsuario) throws Exception {
        Usuario usuario = null;
        if (datosUsuario.containsKey(idUsuario)) {
            usuario = datosUsuario.get(idUsuario);
        }
        if (usuario != null)
            return usuario.getNombre();
        throw new Exception("El usuario no existe");
    }

    public List<Integer> getUsuarios() {
        return new ArrayList<>(datosUsuario.keySet());
    }

    private String dataCsv(Map.Entry entry) {
        return entry.getKey() + "," + entry.getValue() + "\n";
    }

    private void guardarDatosUsuario() {
        try {
            FileWriter fileWriter = new FileWriter("Usuarios.csv");
            for (Map.Entry entry : datosUsuario.entrySet()) {
                fileWriter.write(dataCsv(entry));
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

//    public void guardarDatosPublicacion() {
//        try {
//            FileWriter fileWriter = new FileWriter("Publicaciones.csv");
//            for (Publicacion publicacion : datosPublicacion) {
//                fileWriter.write(publicacion.toString());
//            }
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void leerDatosPublicacion() {
//        Scanner scanner;
//        try {
//            scanner = new Scanner(new File("Publicaciones.csv"));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        scanner.useDelimiter(",");
//        while (scanner.hasNextLine()) {
//            String data = scanner.nextLine();
//            String[] datosArreglo = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//            Usuario usuario = buscarUsuario(Integer.parseInt(datosArreglo[1]));
//            Publicacion publicacion = new Publicacion(usuario, datosArreglo[2]);
//            publicacion.setId(Integer.parseInt(datosArreglo[0]));
//            publicacion.setFecha(datosArreglo[3]);
//            datosPublicacion.add(publicacion);
//        }
//    }

//    public void guardarReaccion() {
//        String csvFormat = "";
//        try {
//            FileWriter fileWriter = new FileWriter("Reacciones.csv");
//            for (Publicacion publicacion : datosPublicacion) {
//                HashMap<String, Integer> reaccionPubli = publicacion.getReacciones();
//                for (Map.Entry<String, Integer> entry : reaccionPubli.entrySet()) {
//                    if (entry.getValue() > 0) {
//                        csvFormat = "\"" + publicacion.getId() + "\"" + "," + "\"" + entry.getKey() + "\"" + "\"" + publicacion.getUsuario().getId() + "\"";
//                    }
//                }
//                fileWriter.write(csvFormat + "\n");
//            }
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void leerReacciones(){
//        Scanner scanner;
//        try {
//            scanner = new Scanner(new File("Reacciones.csv"));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        scanner.useDelimiter(",");
//        while (scanner.hasNextLine()) {
//
//        }
//    }


}
