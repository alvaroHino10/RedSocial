package backend.serviciointeresusuario;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioInteresUsuario {
    SortedMap<Integer, List<List<String>>> interesesUsuario;

    public ServicioInteresUsuario() {
        interesesUsuario = new TreeMap<>();
        leerDatos();
    }

    public void agregarInteresUsuario(int idInteres, int idUsr) {
        List<List<String>> interesesUsr = interesesUsuario.get(idUsr);
        if (interesesUsr == null) interesesUsr = new ArrayList<>();
        List<String> interesUsr = new ArrayList<>(2);
        interesUsr.add(String.valueOf(idInteres));
        interesUsr.add(String.valueOf(idUsr));
        interesesUsr.add(interesUsr);
        interesesUsuario.put(idUsr, interesesUsr);
        guardarDatos();
    }

    public List<Integer> listarInteresUsuario(int idUsr) {
        List<List<String>> interesesUsr = interesesUsuario.get(idUsr);
        List<Integer> idsIntereses = new ArrayList<>();
        if (interesesUsr != null) {
            for (List<String> interesUsr : interesesUsr) {
                int idInteres = Integer.parseInt(interesUsr.get(0));
                idsIntereses.add(idInteres);
            }
            return idsIntereses;
        }
        return idsIntereses;
    }

    private void guardarDatos() {
        try {
            FileWriter fileWriter = new FileWriter("InteresUsuario.csv");
            for (Map.Entry<Integer, List<List<String>>> entry : interesesUsuario.entrySet()) {
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
            scanner = new Scanner(new File("InteresUsuario.csv"));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] datosArreglo = data.split(",");
                List<List<String>> result = interesesUsuario.get(Integer.valueOf(datosArreglo[0]));
                if (result == null) result = new ArrayList<>();
                result.add(List.of(datosArreglo));
                interesesUsuario.put(Integer.valueOf(datosArreglo[0]), result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
