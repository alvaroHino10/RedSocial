package backend.serviciointeres;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ServicioInteres{

    private Map<Integer, Interes> intereses;
    private int contsIds;

    public ServicioInteres() {
        this.intereses = new HashMap<>();
        leerDatosIntereses();
        this.contsIds = intereses.size();
    }

    public int agregarInteres(String nombre){
        int idActual = contsIds + 1;
        Interes interes = new Interes(idActual, nombre);
        int existeInteres = existeInteres(interes);
        if (existeInteres == -1){
            intereses.put(idActual, interes);
            guardarDatos();
            contsIds = intereses.size();
            return contsIds;
        }
        return existeInteres;

    }

    private int existeInteres(Interes interes) {
        for (int i = 0; i < intereses.keySet().size(); i++) {
            Interes interesEncontrado = intereses.get(i + 1);
            if (interesEncontrado.equals(interes)){
                return i + 1;
            }
        }
        return -1;
    }

    public List<Integer> listarIntereses(){
        return new ArrayList<>(intereses.keySet());
    }

    private void leerDatosIntereses() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Interes.csv"));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] datosArreglo = data.split(",");
                Interes interes = new Interes(Integer.parseInt(datosArreglo[0]), datosArreglo[1]);
                intereses.put(Integer.parseInt(datosArreglo[0]), interes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void guardarDatos() {
        try {
            FileWriter fileWriter = new FileWriter("Interes.csv");
            for (Map.Entry<Integer, Interes> entry : intereses.entrySet()) {
                fileWriter.write(entry.getValue().toString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Interes buscarInteres(int idInteres) {
        return intereses.get(idInteres);
    }
}
