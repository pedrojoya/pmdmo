package es.iessaladillo.pedrojoya.pr066.modelos;

public class NavigationDrawerHeader implements NavigationDrawerItem {

    // Propiedades.
    private String text;

    // Constructor.
    public NavigationDrawerHeader(String text) {
        this.text = text;
    }

    // Retorna si es ítem de cabecera de sección.
    @Override
    public boolean isHeader() {
        return true;
    }

    // Getters y setters.
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
