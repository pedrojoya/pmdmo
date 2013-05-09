package es.iessaladillo.pedrojoya.pr064.actividades;

import android.app.Activity;
import android.os.Bundle;
import es.iessaladillo.pedrojoya.pr064.fragmentos.PreferenciasFragment;

public class PreferenciasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se muestra el fragmento en la actividad.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferenciasFragment())
                .commit();
    }
}
