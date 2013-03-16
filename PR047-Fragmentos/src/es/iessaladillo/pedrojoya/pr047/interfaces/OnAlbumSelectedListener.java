package es.iessaladillo.pedrojoya.pr047.interfaces;

import es.iessaladillo.pedrojoya.pr047.modelos.Album;

/**
 * Interfaz para la comunicación entre fragmentos, cuando se selecciona un
 * álbum.
 * 
 */
public interface OnAlbumSelectedListener {

    /**
     * Cuando se selecciona un álbum.
     * 
     * @param album
     */
    public void onAlbumSelected(Album album);

}
