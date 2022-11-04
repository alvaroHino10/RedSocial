package frontend;

public enum Reaccion {
    LIKE(1,"like","frontend/imagenes/like.png"),
    LOVE(2,"love","frontend/imagenes/love.png"),
    SAD(3,"sad","frontend/imagenes/sad.png"),
    HAPPY(4,"happy","frontend/imagenes/haha.png"),
    MAD(5,"mad","frontend/imagenes/mad.png"),
    SURPRISE(6,"surprise","frontend/imagenes/wow.png"),
    CARE(7,"care","frontend/imagenes/care.png"),
    DONT_CARE(8,"dontCare","frontend/imagenes/dont-care.png"),
    PLEASE_EXPLAIN(9,"pleaseExplain","frontend/imagenes/thinking.png");

    private int id;
    private String nombre;
    private String imgSrc;

    Reaccion(int id, String nombre, String imgSrc) {
        this.id = id;
        this.nombre = nombre;
        this.imgSrc = imgSrc;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
