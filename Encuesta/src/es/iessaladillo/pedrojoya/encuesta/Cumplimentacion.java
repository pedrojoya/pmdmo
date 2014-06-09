package es.iessaladillo.pedrojoya.encuesta;

public class Cumplimentacion {

    // Propiedades.
    private long id;
    private String pregunta1;
    private String pregunta2;
    private String pregunta3;

    // Constructores.
    public Cumplimentacion(long id, String pregunta1, String pregunta2,
            String pregunta3) {
        super();
        this.id = id;
        this.pregunta1 = pregunta1;
        this.pregunta2 = pregunta2;
        this.pregunta3 = pregunta3;
    }

    public Cumplimentacion() {
    }

    // Getters and setters.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

}
