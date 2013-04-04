package es.iessaladillo.pedrojoya.pr049.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.fragmentos.DetalleFragment;
import es.iessaladillo.pedrojoya.pr049.interfaces.OnAlbumSelectedListener;
import es.iessaladillo.pedrojoya.pr049.modelos.Album;

public class MainActivity extends FragmentActivity implements
        OnAlbumSelectedListener {

    // Constantes.
    public static final String EXTRA_ALBUM = "es.iessaladillo.pr047.ALBUM";

    // Variables miembro.
    private FragmentManager gestor;
    private DetalleFragment frgDetalle;
    private FrameLayout flDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtiene la referencia al FrameLayout.
        flDetalle = (FrameLayout) this.findViewById(R.id.flDetalle);
        gestor = getSupportFragmentManager();
    }

    // Cuando en el fragmento frgLista se selecciona un álbum.
    @Override
    public void onAlbumSelected(Album album) {
        // Si hay FrameLayout de detalle (puede que no haya porque por el tamaño
        // del dispositivo tengamos dos actividades distintas).
        if (flDetalle != null) {
            // Muestro el detalle del álbum.
            mostrarFragmentoDetalle(album);
        } else {
            // Hay dos actividades. Llamo a la otra actividad pasándole el álbum
            // que debe mostrar (cuya clase debe implementar Parcelable).
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(EXTRA_ALBUM, album);
            this.startActivity(i);
        }
    }

    public void mostrarFragmentoDetalle(Album album) {
        // Inicio la transacción.
        FragmentTransaction transaccion = gestor.beginTransaction();
        // Creo una nueva instancia del fragmento de detalle.
        frgDetalle = new DetalleFragment();
        // Le paso el album como argumento al fragmento.
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(MainActivity.EXTRA_ALBUM, album);
        frgDetalle.setArguments(argumentos);
        // Añado a la transacción el colocar el fragmento en el FrameLayout.
        transaccion.add(R.id.flDetalle, frgDetalle);
        // Finalizo la transacción.
        transaccion.commit();
    }
}
