package es.iessaladillo.pedrojoya.pr066.modelos;

public interface NavigationDrawerItem {

    // Constantes.
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ALBUM = 1;
    public static final int NUM_TYPES = 2;

    // Retorna el tipo de elemento de la lista.
    int getType();

}
