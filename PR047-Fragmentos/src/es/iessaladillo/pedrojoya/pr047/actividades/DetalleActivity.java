package es.iessaladillo.pedrojoya.pr047.actividades;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import es.iessaladillo.pedrojoya.pr047.R;
import es.iessaladillo.pedrojoya.pr047.fragmentos.DetalleFragment;
import es.iessaladillo.pedrojoya.pr047.modelos.Album;

public class DetalleActivity extends FragmentActivity {

    private DetalleFragment frgDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        // Llama al método mostrarDetalle del fragmento detalle, pasándole el
        // álbum que debe mostrar (y que a su vez ha recibido como extra del
        // intent con el que ha sido llamada la actividad).
        frgDetalle = (DetalleFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.frgDetalle);
        frgDetalle.mostrarDetalle((Album) getIntent().getParcelableExtra(
                MainActivity.EXTRA_ALBUM));
    }
}
