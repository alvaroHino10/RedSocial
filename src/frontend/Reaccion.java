package frontend;

public enum Reaccion {
    MeGusta ("Me gusta", "frontend/imagenes/like.png", ""),
    Like("like","frontend/imagenes/like.png", "like"),
    Love("love","frontend/imagenes/love.png", "love"),
    Sad("sad","frontend/imagenes/sad.png", "sad"),
    Happy("happy","frontend/imagenes/haha.png", "happy"),
    Mad("mad","frontend/imagenes/mad.png", "mad"),
    Surprise("surprise","frontend/imagenes/wow.png", "surprise"),
    Care("care","frontend/imagenes/care.png", "care"),
    Indifferent("indifferent","frontend/imagenes/dont-care.png", "dontCare"),
    Explain("explain","frontend/imagenes/thinking.png", "explain");

    private final String nombre;
    private final String imgSrc;
    private final String idImageView;

    Reaccion(String nombre, String imgSrc, String idImageView) {
        this.nombre = nombre;
        this.imgSrc = imgSrc;
        this.idImageView = idImageView;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getIdImageView() {
        return idImageView;
    }
}
