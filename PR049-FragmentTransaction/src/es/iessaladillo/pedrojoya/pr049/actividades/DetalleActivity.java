package es.iessaladillo.pedrojoya.pr049.actividades;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.fragmentos.DetalleFragment;
import es.iessaladillo.pedrojoya.pr049.modelos.Obra;

public class DetalleActivity extends Activity implements
        DetalleFragment.OnDetalleShownListener {

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        // Cargo el fragmento de detalle en el FrameLayout.
        FragmentManager gestorFragmentos = this.getFragmentManager();
        FragmentTransaction transaccion = gestorFragmentos.beginTransaction();
        DetalleFragment frgDetalle = DetalleFragment.newInstance(
                (Obra) getIntent().getExtras().getParcelable(
                        DetalleFragment.EXTRA_OBRA), getIntent().getExtras()
                        .getInt(DetalleFragment.EXTRA_POSITION));
        transaccion.add(R.id.flDetalle, frgDetalle);
        transaccion.commit();
    }

    @Override
    public void onDetalleShown(int position) {
        // Es obligatorio que toda actividad que utilice el fragmento
        // DetalleActivity implemente la interfaz OnDetallaShownListner, aunque
        // en este caso no se hace nada.
    }

}
