package es.iessaladillo.pedrojoya.pr088client;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import es.iessaladillo.pedrojoya.pr088.R;

public class PreferenciasActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferencias);
    }

}
