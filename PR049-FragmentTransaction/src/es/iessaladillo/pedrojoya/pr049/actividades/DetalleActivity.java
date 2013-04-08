package es.iessaladillo.pedrojoya.pr049.actividades;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.fragmentos.DetalleFragment;

public class DetalleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        // Cargo el fragmento de detalle en el FrameLayout.
        FragmentManager gestorFragmentos = this.getSupportFragmentManager();
        FragmentTransaction transaccion = gestorFragmentos.beginTransaction();
        DetalleFragment frgDetalle = new DetalleFragment();
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(MainActivity.EXTRA_ALBUM, getIntent()
                .getExtras().getParcelable(MainActivity.EXTRA_ALBUM));
        frgDetalle.setArguments(argumentos);
        transaccion.add(R.id.flContenedorDetalle, frgDetalle);
        transaccion.commit();
    }

}
