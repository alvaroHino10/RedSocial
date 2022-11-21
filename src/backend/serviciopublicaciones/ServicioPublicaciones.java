package backend.serviciopublicaciones;


import backend.serviciousuarios.ServicioUsuarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServicioPublicaciones {

    private final SortedMap<Integer, Publicacion> datosPublicacion;
    private int contIds;
    private final List<Integer> idsUsuarios;
    private final ServicioUsuarios servicioUsuarios;

    public ServicioPublicaciones() {
        this.datosPublicacion = new TreeMap<>();
        this.servicioUsuarios = new ServicioUsuarios();
        this.idsUsuarios = this.servicioUsuarios.listarUsuarios();
        leerDatosPublicacion();
        this.contIds = datosPublicacion.size();
    }

    public int agregarPublicacion(int idUsuario, String contenido) {
        Publicacion publicacion = new Publicacion(contIds + 1, idUsuario, contenido, fecha());
        int idActual = contIds + 1;
        datosPublicacion.put(idActual, publicacion);
        guardarDatosPublicacion();
        contIds = datosPublicacion.size();
        return idActual;
    }

    public Publicacion buscarPublicacion(int idPublicacion) {
        return datosPublicacion.get(idPublicacion);
    }

    public List<Integer> listarPublicaciones() {
        return new ArrayList<>(datosPublicacion.keySet());
    }

    private String fecha() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    private void guardarDatosPublicacion() {
        try {
            FileWriter fileWriter = new FileWriter("Publicaciones.csv");
            for (Map.Entry<Integer, Publicacion> entry : datosPublicacion.entrySet()) {
                String formatCsv = (entry.getValue().toString() + "\n");
                fileWriter.write(formatCsv);
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void leerDatosPublicacion() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Publicaciones.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] datosArreglo = data.split(",");
            String contenido = datosArreglo[2].substring(1, datosArreglo[2].length() - 1);
            Publicacion publicacion = new Publicacion(Integer.parseInt(datosArreglo[0]),
                    Integer.parseInt(datosArreglo[1]), contenido, datosArreglo[3]);
            datosPublicacion.put(Integer.valueOf(datosArreglo[0]), publicacion);
        }
    }
}
