package es.iessaladillo.pedrojoya.pr066.modelos;

public class NavigationDrawerHeader implements NavigationDrawerItem {

    // Propiedades.
    private String text;

    // Constructor.
    public NavigationDrawerHeader(String text) {
        this.text = text;
    }

    // Retorna el tipo de elemento de la lista (cabecera).
    @Override
    public int getType() {
        return NavigationDrawerHeader.TYPE_HEADER;
    }

    // Getters y setters.
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
