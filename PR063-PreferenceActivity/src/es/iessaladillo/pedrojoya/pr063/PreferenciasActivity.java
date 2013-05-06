package es.iessaladillo.pedrojoya.pr063;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenciasActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferencias);
    }
}
