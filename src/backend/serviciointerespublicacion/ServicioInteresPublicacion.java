package backend.serviciointerespublicacion;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioInteresPublicacion {
    SortedMap<Integer, List<List<String>>> interesesPublicacion;

    public ServicioInteresPublicacion() {
        interesesPublicacion = new TreeMap<>();
        leerDatos();
    }

    public void agregarInteresPublicacion(int idInteres, int idPublicacion) {
        List<List<String>> interesesUsr = interesesPublicacion.get(idPublicacion);
        if (interesesUsr == null) interesesUsr = new ArrayList<>();
        List<String> interesUsr = new ArrayList<>(2);
        interesUsr.add(String.valueOf(idInteres));
        interesUsr.add(String.valueOf(idPublicacion));
        interesesUsr.add(interesUsr);
        interesesPublicacion.put(idPublicacion, interesesUsr);
        guardarDatos();
    }

    public List<Integer> listarInteresPublicacion(int idPublicacion) {
        List<List<String>> interesesPub = interesesPublicacion.get(idPublicacion);
        List<Integer> idsIntereses = new ArrayList<>();
        if (interesesPub != null) {
            for (List<String> interesPub : interesesPub) {
                int idInteres = Integer.parseInt(interesPub.get(0));
                idsIntereses.add(idInteres);
            }
            return idsIntereses;
        }
        return idsIntereses;
    }

    private void guardarDatos() {
        try {
            FileWriter fileWriter = new FileWriter("InteresPublicacion.csv");
            for (Map.Entry<Integer, List<List<String>>> entry : interesesPublicacion.entrySet()) {
                String formatCsv = toCsv(entry.getValue());
                fileWriter.write(formatCsv);
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String toCsv(List<List<String>> dataCsv) {
        StringBuilder res = new StringBuilder();
        for (List<String> value : dataCsv) {
            res.append(value.get(0)).append(",").append(value.get(1)).append("\n");
        }
        return String.valueOf(res);
    }

    private void leerDatos() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("InteresPublicacion.csv"));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] datosArreglo = data.split(",");
                List<List<String>> result = interesesPublicacion.get(Integer.valueOf(datosArreglo[0]));
                if (result == null) result = new ArrayList<>();
                result.add(List.of(datosArreglo));
                interesesPublicacion.put(Integer.valueOf(datosArreglo[0]), result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
