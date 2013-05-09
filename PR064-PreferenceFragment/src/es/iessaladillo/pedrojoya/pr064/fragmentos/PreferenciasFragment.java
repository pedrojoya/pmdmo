package es.iessaladillo.pedrojoya.pr064.fragmentos;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import es.iessaladillo.pedrojoya.pr064.R;

public class PreferenciasFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se construye el fragmento de preferencias a partir de la
        // especificación XML de preferencias.
        this.addPreferencesFromResource(R.xml.preferencias);
    }

}
