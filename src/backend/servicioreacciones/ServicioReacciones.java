package backend.servicioreacciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioReacciones {
    private final SortedMap<Integer, List<List<String>>> datosReacciones;
    private int contIds;

    public ServicioReacciones() {
        this.datosReacciones = new TreeMap<>();
        leerReacciones();
        this.contIds = datosReacciones.size();
    }

    public void agregarReaccion(int idPublicacion, int idUsuario, Emocion reaccion) {
        List<List<String>> datosPubli = datosReacciones.get(idPublicacion);
        List<String> reaccionesListDatos = new ArrayList<>();
        reaccionesListDatos.add(String.valueOf(idPublicacion));
        reaccionesListDatos.add(reaccion.name());
        reaccionesListDatos.add(String.valueOf(idUsuario));
        if (datosPubli != null){
            datosPubli.add(reaccionesListDatos);
        }
        else {
            datosPubli = new ArrayList<>();
            datosPubli.add(reaccionesListDatos);
        }
        datosReacciones.put(idPublicacion, datosPubli);
        guardarReaccion();
    }

    public Map<Emocion, Integer> listarResumenReacciones(int idPublicacion) {
        Map<Emocion, Integer> reaccionesXPubli = new HashMap<>();
        cargarDatos(reaccionesXPubli);
        for (List<List<String>> datos : datosReacciones.values()) {
            for (List<String> reaccion : datos) {
                int idPublicacionDato = Integer.parseInt(reaccion.get(0));
                if (idPublicacionDato == idPublicacion) {
                    for (Emocion emocion : Emocion.values()) {
                        if (emocion.name().equals(reaccion.get(1))) {
                            reaccionesXPubli.put(emocion, reaccionesXPubli.get(emocion) + 1);
                            break;
                        }
                    }
                }
            }
        }
        return reaccionesXPubli;
    }

    public boolean tieneMasDeUnaReaccion(int idUsuario) {
        int contReacUsr = 0;
        for (int i = 0; i < datosReacciones.values().size(); i++) {
            List<String> datosCsv = datosReacciones.get(i).get(i + 1);
            if (datosCsv != null && Integer.parseInt(datosCsv.get(2)) == idUsuario) {
                contReacUsr++;
            }
        }
        return contReacUsr > 0;
    }

    private String toCsv(List<List<String>> reaccionesListDatos) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < reaccionesListDatos.size(); i++) {
            List<String> reaccion = reaccionesListDatos.get(i);
            res.append(reaccion.get(0)).append(",").append(reaccion.get(1)).append(",").append(reaccion.get(2)).append("\n");
        }
        return String.valueOf(res);
    }

    private void cargarDatos(Map<Emocion, Integer> listado) {
        for (Emocion reaccion : Emocion.values()) {
            listado.put(reaccion, 0);
        }
    }

    private void guardarReaccion() {
        try {
            FileWriter fileWriter = new FileWriter("Reacciones.csv");
            for (Map.Entry<Integer, List<List<String>>> entry : datosReacciones.entrySet()) {
                String formatCsv = toCsv(entry.getValue());
                fileWriter.write(formatCsv);
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void leerReacciones() {
        Scanner scanner;
        contIds = 0;
        try {
            scanner = new Scanner(new File("Reacciones.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] datosArreglo = data.split(",");
            List<List<String>> result = datosReacciones.get(Integer.valueOf(datosArreglo[0]));
            if (result == null)
                result = new ArrayList<>();
            result.add(List.of(datosArreglo));
            datosReacciones.put(Integer.valueOf(datosArreglo[0]), result);
            contIds++;
        }
    }
}
