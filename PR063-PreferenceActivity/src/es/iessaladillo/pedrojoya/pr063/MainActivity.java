package es.iessaladillo.pedrojoya.pr063;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView lblPreferencias;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtengo las preferencias.
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        // Obtengo las vistas.
        lblPreferencias = (TextView) this.findViewById(R.id.lblPreferencias);
    }

    @Override
    protected void onResume() {
        if (preferencias != null) {
            // Escribo las preferencias en el TextView.
            StringBuilder sb = new StringBuilder("");
            sb.append(getString(R.string.sincronizar)).append(": ")
                    .append(preferencias.getBoolean("prefSincronizar", false))
                    .append("\n");
            sb.append(getString(R.string.tipo_conexion))
                    .append(": ")
                    .append(preferencias.getString("prefTipoConexion",
                            getString(R.string.tipo_conexion_default)))
                    .append("\n");
            sb.append(getString(R.string.letras_grandes))
                    .append(": ")
                    .append(preferencias.getBoolean("prefLetrasGrandes", false))
                    .append("\n");
            sb.append(getString(R.string.lema)).append(": ")
                    .append(preferencias.getString("prefLema", ""))
                    .append("\n");
            sb.append(getString(R.string.tono_notificacion)).append(": ")
                    .append(preferencias.getString("prefTonoNotificacion", ""))
                    .append("\n");
            sb.append(getString(R.string.fecha_nacimiento)).append(": ")
                    .append(preferencias.getString("prefFecha", ""))
                    .append("\n");
            lblPreferencias.setText(sb.toString());
        }
        super.onResume();
    }

    // Al hacer click en btnPrefrencias.
    public void btnPreferenciasOnClick(View v) {
        // Lanzamos la actividad de preferencias.
        Intent i = new Intent(this, PreferenciasActivity.class);
        this.startActivity(i);
    }

}
