package es.iessaladillo.pedrojoya.pr049.interfaces;

import es.iessaladillo.pedrojoya.pr049.modelos.Album;

// Interfaz para la comunicación entre fragmentos, cuando se selecciona un
// álbum.
public interface OnAlbumSelectedListener {

    // Cuando se selecciona un álbum.
    public void onAlbumSelected(Album album);

}
