package backend.serviciorelacionador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicioRelacionador {
    private String pathCsv;
    private List<InteresRelacionado> interesRelacionados;

    public ServicioRelacionador(String pathCsv) {
        this.pathCsv = pathCsv + ".csv";
        this.interesRelacionados = new ArrayList<>();
        leerDatos();

    }

    public void agregarInteresRelacionado(int idInteres, int idRelacionado) {
        InteresRelacionado interesRelacionado = new InteresRelacionado(idInteres, idRelacionado);
        this.interesRelacionados.add(interesRelacionado);
        guardarDatos();
    }

    public List<Integer> listarInteresesRelacionados(int idRelacionado) {
        List<Integer> idsInteresesRelacionados = new ArrayList<>();
        for (InteresRelacionado interesRelacionado: interesRelacionados){
            if (interesRelacionado.getIdRelacionado() == idRelacionado){
                idsInteresesRelacionados.add(interesRelacionado.getIdInteres());
            }
        }
        return idsInteresesRelacionados;
    }

    private void leerDatos() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(this.pathCsv));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] datosArreglo = data.split(",");
                InteresRelacionado interesRelacionado = new InteresRelacionado(Integer.parseInt(datosArreglo[0]),
                        Integer.parseInt(datosArreglo[1]));
                interesRelacionados.add(interesRelacionado);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void guardarDatos() {
        try {
            FileWriter fileWriter = new FileWriter(this.pathCsv);
            for (InteresRelacionado interesRelacionado: interesRelacionados) {
                fileWriter.write(interesRelacionado.toString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
