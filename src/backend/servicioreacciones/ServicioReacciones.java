package backend.servicioreacciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioReacciones {
    private final SortedMap<Integer, List<String>> datosReacciones;
    private final Map<Emocion, Integer> reaccionesXPubli;
    private int contIds;

    public ServicioReacciones() {
        this.datosReacciones = new TreeMap<>();
        this.reaccionesXPubli = new HashMap<>();
        leerReacciones();
        this.contIds = datosReacciones.size();
        cargarDatos();
    }

    public void agregarReaccion(int idPublicacion, int idUsuario, Emocion reaccion) {
        List<String> reaccionesListDatos = new ArrayList<>();
        reaccionesListDatos.add(String.valueOf(idPublicacion));
        reaccionesListDatos.add(String.valueOf(idUsuario));
        reaccionesListDatos.add(reaccion.name());
        datosReacciones.put(idPublicacion, reaccionesListDatos);
        guardarReaccion();
    }

    public Map<Emocion, Integer> listarResumenReacciones(int idPublicacion) {
        // devuelve la cantidad de reacciones por reaccion
        for(List<String> datos : datosReacciones.values()){
            int idPublicacionDato = Integer.parseInt(datos.get(0));
            if (idPublicacionDato == idPublicacion){
                for (Emocion emocion : Emocion.values()) {
                    if (emocion.name().equals(datos.get(1))){
                        reaccionesXPubli.put(emocion, reaccionesXPubli.get(emocion) + 1);
                        break;
                    }
                }
            }
        }
        return reaccionesXPubli;
    }

    private void cargarDatos(){
        for (Emocion reaccion: Emocion.values()) {
            reaccionesXPubli.put(reaccion, 0);
        }
    }

    private String toCsv(List<String> reaccionesListDatos) {
        return reaccionesListDatos.get(0) + "," + reaccionesListDatos.get(1) + "," + reaccionesListDatos.get(2) + "\n";
    }

    private void guardarReaccion() {
        try {
            FileWriter fileWriter = new FileWriter("Reacciones.csv");
            for (Map.Entry<Integer, List<String>> entry : datosReacciones.entrySet()) {
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
            datosReacciones.put(Integer.valueOf(datosArreglo[0]), new ArrayList<>(List.of(datosArreglo)));
            contIds++;
        }
    }
}
