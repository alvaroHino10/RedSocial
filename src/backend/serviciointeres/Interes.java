package backend.serviciointeres;

public class Interes {
    private final int idInteres;
    private final String nombreInteres;

    public Interes(int idInteres, String nombreInteres) {
        this.idInteres = idInteres;
        this.nombreInteres = nombreInteres;
    }

    public String getNombreInteres() {
        return nombreInteres;
    }

    public boolean equals(Object interes){
        if (interes instanceof Interes){
            return ((Interes) interes).nombreInteres.equals(this.nombreInteres);
        }
        return false;
    }

    public String toString(){
        return idInteres + "," + nombreInteres;
    }
}
