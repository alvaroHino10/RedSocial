package frontend;

public enum Reaccion {
    MeGusta ("Me gusta", "frontend/imagenes/like.png", ""),
    Like("Like","frontend/imagenes/like.png", "like"),
    Love("Love","frontend/imagenes/love.png", "love"),
    Sad("Sad","frontend/imagenes/sad.png", "sad"),
    Happy("Happy","frontend/imagenes/haha.png", "happy"),
    Mad("Mad","frontend/imagenes/mad.png", "mad"),
    Surprise("Surprise","frontend/imagenes/wow.png", "surprise"),
    Care("Care","frontend/imagenes/care.png", "care"),
    Indifferent("Indifferent","frontend/imagenes/dont-care.png", "dontCare"),
    Explain("Explain","frontend/imagenes/thinking.png", "explain");

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
