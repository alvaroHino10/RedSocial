package backend.servicioreacciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioReacciones {
    private final SortedMap<Integer, List<List<String>>> datosReacciones;

    public ServicioReacciones() {
        this.datosReacciones = new TreeMap<>();
        leerReacciones();
    }

    public void agregarReaccion(int idPublicacion, int idUsuario, Emocion reaccion) {
        List<List<String>> datosPubli = datosReacciones.get(idPublicacion);
        List<String> reaccionesListDatos = new ArrayList<>(3);
        reaccionesListDatos.add(String.valueOf(idPublicacion));
        reaccionesListDatos.add(reaccion.name());
        reaccionesListDatos.add(String.valueOf(idUsuario));
        if (datosPubli == null) {
            datosPubli = new ArrayList<>();
        }
        datosPubli.add(reaccionesListDatos);

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

    public boolean tieneMasDeTresReacciones(int idPublicacion) {
        Map<Emocion, Integer> resumenReacciones = listarResumenReacciones(idPublicacion);
        int contadorReacciones = 0;
        for (Map.Entry<Emocion, Integer> reaccion: resumenReacciones.entrySet()){
            contadorReacciones += reaccion.getValue();
        }
        return contadorReacciones >= 3;
    }

    private String toCsv(List<List<String>> reaccionesListDatos) {
        StringBuilder res = new StringBuilder();
        for (List<String> reaccion : reaccionesListDatos) {
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
            e.printStackTrace();
        }
    }

    private void leerReacciones() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Reacciones.csv"));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] datosArreglo = data.split(",");
                List<List<String>> result = datosReacciones.get(Integer.valueOf(datosArreglo[0]));
                if (result == null)
                    result = new ArrayList<>();
                result.add(List.of(datosArreglo));
                datosReacciones.put(Integer.valueOf(datosArreglo[0]), result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
